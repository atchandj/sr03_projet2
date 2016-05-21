package servlets.super_user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.super_user.SuperUser;
import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.super_user.UsersManagementDao;

public class UsersManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USERS_MANAGEMENT_JSP = "/super_user/users_management.jsp";
	private static final String ADD_USER_JSP = "/super_user/add_user.jsp";
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
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); // Initiation of the session engine					
		SuperUser superUser = (SuperUser) session.getAttribute(ATT_SESSION_SUPERUSER); // We get a session variable
		String errorMessage = null;
		String userType = null;
		String userEmail = null;
		String activate = null;
		Boolean activateBool = null;
		
		String action =  request.getParameter("action");
		if(action != null){
			switch (action) {
			case "delete":	
				// System.out.println("Supprimer"); // Test
				userType =  request.getParameter("user_type");
				userEmail =  request.getParameter("email");	
				// System.out.println(userType); // Test
				// System.out.println(userEmail); // Test
				if(userType.equals("super_user")){
					try {
						// System.out.println("Supprimer admin"); // Test
						this.usersManagementDao.dropSuperSuper(userEmail);
					} catch (DaoException e) {
						errorMessage = e.getMessage();
						request.setAttribute("errorMessage", errorMessage);
					}
				}else if(userType.equals("trainee")){				
					try {
						// System.out.println("Supprimer stagiaire"); // Test
						this.usersManagementDao.dropTrainee(userEmail);
					} catch (DaoException e) {
						errorMessage = e.getMessage();
						request.setAttribute("errorMessage", errorMessage);
					}
				}
				break;
			case "modify_status":
				// System.out.println("Modification de status"); // Test
				userType = request.getParameter("user_type");
				userEmail = request.getParameter("email");	
				activate = request.getParameter("activer");
				activateBool = Boolean.valueOf(activate);
				
				// System.out.println(userType); // Test
				// System.out.println(userEmail); // Test
				// System.out.println(activate); // Test
				// System.out.println(activateBool); // Test
				if(userType.equals("super_user")){
					try {
						// System.out.println("Modifier statut admin"); // Test
						this.usersManagementDao.modifyStatusSuperUser(userEmail, activateBool);
					} catch (DaoException e) {
						errorMessage = e.getMessage();
						request.setAttribute("errorMessage", errorMessage);
					}
				}else if(userType.equals("trainee")){				
					try {
						// System.out.println("Modifier statut  stagiaire"); // Test
						this.usersManagementDao.modifyStatusTrainee(userEmail, activateBool);
					} catch (DaoException e) {
						errorMessage = e.getMessage();
						request.setAttribute("errorMessage", errorMessage);
					}
				}
				break;
			case "add":
				this.getServletContext().getRequestDispatcher(ADD_USER_JSP).forward(request, response);
			default:
				break;
			}
		}
		if(action == null || ( !(action.equals("add") ) ) ){
			try {
				request.setAttribute("superUsers", this.usersManagementDao.getSuperUsers(superUser));
				request.setAttribute("trainees", this.usersManagementDao.getTrainees());
			} catch (DaoException e) {
				errorMessage = e.getMessage();
				request.setAttribute("errorMessage", errorMessage);
			}
			this.getServletContext().getRequestDispatcher(USERS_MANAGEMENT_JSP).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); // Initiation of the session engine					
		SuperUser superUser = (SuperUser) session.getAttribute(ATT_SESSION_SUPERUSER); // We get a session variable
		
		String email =  request.getParameter("email");
		String password =  request.getParameter("password");
		String surname =  request.getParameter("surname");
		String name =  request.getParameter("name");
		String phone =  request.getParameter("phone");
		String company =  request.getParameter("company");
		String type =  request.getParameter("type");
		String accountStatus =  request.getParameter("status");
		String errorMessage = null;

		if(type.equals("super_user")){
			SuperUser newSuperUser = new SuperUser();
			newSuperUser.setEmail(email);
			newSuperUser.setPassword(password);
			newSuperUser.setSurname(surname);
			newSuperUser.setName(name);
			newSuperUser.setPhone(phone);
			newSuperUser.setCompany(company);
			newSuperUser.setAccountStatus(accountStatus.equals("active")?true:false);
			try {
				// System.out.println("Ajouter administrateur"); // Test
				this.usersManagementDao.addSuperUser(newSuperUser);
			} catch (DaoException e) {
				errorMessage = e.getMessage();
				request.setAttribute("errorMessage", errorMessage);
			}
		}else if(type.equals("trainee")){
			Trainee newTrainee = new Trainee();
			newTrainee.setEmail(email);
			newTrainee.setPassword(password);
			newTrainee.setSurname(surname);
			newTrainee.setName(name);
			newTrainee.setPhone(phone);
			newTrainee.setCompany(company);
			newTrainee.setAccountStatus(accountStatus.equals("active")?true:false);
			try {
				// System.out.println("Ajouter stagiaire"); // Test
				this.usersManagementDao.addTrainee(newTrainee);
			} catch (DaoException e) {
				errorMessage = e.getMessage();
				request.setAttribute("errorMessage", errorMessage);
			}
			
		}else{
			request.setAttribute("errorMessage", "Veuillez sélectionner un type d'utilisateur.");
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
}
