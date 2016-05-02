package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import beans.SuperUser;
import beans.TemporaryUser;
import beans.Trainee;

public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getServletContext().getRequestDispatcher("/WEB-INF/connection.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TemporaryUser tempUser = new TemporaryUser();
		tempUser.setEmail(request.getParameter("email"));
		tempUser.setPassword(request.getParameter("password"));
		System.out.println(tempUser.getEmail());
		String errorMessage;
		try {
			Trainee trainee = this.userDao.getTrainee(tempUser);
			request.setAttribute("surname", trainee.getSurname());
		} catch (DaoException e) {
			if(e.getMessage().equals("Not a trainee.")){
				SuperUser superUser;
				try {
					superUser = this.userDao.getSuperUser(tempUser);
					request.setAttribute("surname", superUser.getSurname());
				} catch (DaoException e1) {
					errorMessage = e1.getMessage();
					if(errorMessage.equals("Not a super user.")){
						request.setAttribute("errorMessage", "Utilisateur inconnu.");
					}else{
						request.setAttribute("errorMessage", errorMessage);
					}
				}				
			}			
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/connection.jsp").forward(request, response);
	}

}
