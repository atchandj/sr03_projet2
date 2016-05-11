package servlets.super_user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TemporaryUser;
import beans.super_user.SuperUser;
import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.super_user.UserDataDao;
import dao.super_user.UsersManagementDao;

public class UserData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USER_DATA_JSP = "/super_user/user_data.jsp";
    
    private UserDataDao userDataDao;
	
    public UserData() {
        super();
    }
    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.userDataDao = daoFactory.getUserDataDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String userEmail = request.getParameter("email");
		String action = request.getParameter("action");
		String type = request.getParameter("type");
		if(type.equals("super_user")){
			SuperUser superUser;
			try {
				superUser = this.userDataDao.getSuperUser(userEmail);
				request.setAttribute("user", superUser);
			} catch (DaoException e) {				
				request.setAttribute("errorMessage", "Utilisateur inconnu.");
			}			
			request.setAttribute("type", "Administrateur");
		}else if(type.equals("trainee")){
			Trainee trainee;
			try {
				trainee = this.userDataDao.getTrainee(userEmail);
				request.setAttribute("user", trainee);
			} catch (DaoException e) {
				request.setAttribute("errorMessage", "Utilisateur inconnu.");
			}			
			request.setAttribute("type", "Stagiaire");
		}else{
			request.setAttribute("errorMessage", "Bien essayé ;-)");
		}		
		this.getServletContext().getRequestDispatcher(USER_DATA_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(USER_DATA_JSP).forward(request, response);
	}
}