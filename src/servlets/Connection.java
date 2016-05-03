package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import beans.SuperUser;
import beans.TemporaryUser;
import beans.Trainee;

public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String TRAINEE_PAGE = "/trainee";
    public static final String SUPER_USER_PAGE = "/super_user";
    private UserDao userDao;
    
    public Connection() {
        super();
    }
    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.userDao = daoFactory.getUserDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String traineeSurname = (String) session.getAttribute("traineeSurname");
		String traineeName = (String) session.getAttribute("traineeName");
		
		String superUserSurname = (String) session.getAttribute("superUserSurname");
		String superUserName = (String) session.getAttribute("superUserName");
		
		if((traineeSurname != null && traineeName != null) || (superUserSurname != null && superUserName != null)){
			if(traineeSurname != null && traineeName != null){
	            request.getRequestDispatcher( TRAINEE_PAGE ).forward( request, response );
			}else{
	            request.getRequestDispatcher( SUPER_USER_PAGE ).forward( request, response );
			}			
		}else{
			this.getServletContext().getRequestDispatcher("/connection.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TemporaryUser tempUser = new TemporaryUser();
		tempUser.setEmail(request.getParameter("email"));
		tempUser.setPassword(request.getParameter("password"));
		String errorMessage;
		try {
			Trainee trainee = this.userDao.getTrainee(tempUser);
			
			HttpSession session = request.getSession(); // Initiation of the session engine					
			session.setAttribute("traineeSurname", trainee.getSurname()); // Creation of a session variable
			session.setAttribute("traineeName", trainee.getName()); // Creation of a session variable
			
			response.sendRedirect( request.getContextPath() + TRAINEE_PAGE ); // Redirection
		} catch (DaoException e) {
			if(e.getMessage().equals("Not a trainee.")){
				SuperUser superUser;
				try {
					superUser = this.userDao.getSuperUser(tempUser);
					
					HttpSession session = request.getSession(); // Initiation of the session engine					
					session.setAttribute("superUserSurname", superUser.getSurname()); // Creation of a session variable
					session.setAttribute("superUserName", superUser.getName()); // Creation of a session variable
					
					response.sendRedirect( request.getContextPath() + SUPER_USER_PAGE ); // Redirection
				} catch (DaoException e1) {
					errorMessage = e1.getMessage();
					if(errorMessage.equals("Not a super user.")){
						request.setAttribute("errorMessage", "Utilisateur inconnu.");
					}else{
						request.setAttribute("errorMessage", errorMessage);
					}
					this.getServletContext().getRequestDispatcher("/connection.jsp").forward(request, response);
				}				
			}			
		}		
	}
}
