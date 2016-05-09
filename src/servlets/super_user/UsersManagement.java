package servlets.super_user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.super_user.SuperUser;
import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.UserDao;
import dao.super_user.UsersManagementDao;

public class UsersManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USERS_MANAGEMENT_JSP = "/super_user/users_management.jsp";
    private static final String ATT_SESSION_SUPERUSER = "superUser";

    private UsersManagementDao usersManagementDao;
    
    public UsersManagement() {
        super();
    }
    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.usersManagementDao = daoFactory.getUsersManagamentDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); // Initiation of the session engine					
		SuperUser superUser = (SuperUser) session.getAttribute(ATT_SESSION_SUPERUSER); // Creation of a session variable
		String errorMessage;
		
		String action =  request.getParameter("action");
		if(action != null){
			switch (action) {
			case "delete":	
				System.out.println("Supprimer");
				String userType =  request.getParameter("user_type");
				String userEmail =  request.getParameter("email");	
				System.out.println(userType);
				System.out.println(userEmail);
				if(userType.equals("super_user")){
					try {
						System.out.println("Supprimer admin");
						this.usersManagementDao.dropSuperSuper(userEmail);
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						errorMessage = e.getMessage();
						request.setAttribute("errorMessage", errorMessage);
					}
				}else if(userType.equals("trainee")){				
					try {
						System.out.println("Supprimer stagiaire");
						this.usersManagementDao.dropTrainee(userEmail);
					} catch (DaoException e) {
						errorMessage = e.getMessage();
						request.setAttribute("errorMessage", errorMessage);
					}
				}
				break;
			case "add":			
				break;
			default:
				break;
			}
		}

		try {
			request.setAttribute("superUsers", this.usersManagementDao.getSuperUsers(superUser));
			request.setAttribute("trainees", this.usersManagementDao.getTrainees());
		} catch (DaoException e) {
			errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		this.getServletContext().getRequestDispatcher(USERS_MANAGEMENT_JSP).forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
