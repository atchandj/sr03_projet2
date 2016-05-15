package servlets.super_user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.super_user.TopicsManagementDao;

public class FormsManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FORMS_MANAGEMENT_JSP = "/super_user/forms_management.jsp";
	private static final String ADD_QUESTIONNAIRE_JSP = "/super_user/add_questionnaire.jsp";

    private TopicsManagementDao topicsManagementDao;
	
    public FormsManagement() {
        super();
    }
    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.topicsManagementDao = daoFactory.getTopicsManagementDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errorMessage = null;
		String action =  request.getParameter("action");
		String topicName = null;
		if(action != null){
			switch (action) {
			case "delete_topic":	
				System.out.println("Supprimer un sujet"); // Test
				break;
			case "add_questionnaire":
				System.out.println("Ajouter un questionnaire"); // Test
				topicName = request.getParameter("topic_name");
				// System.out.println(topicName); // Test
				request.setAttribute("topic_name", topicName);
				this.getServletContext().getRequestDispatcher(ADD_QUESTIONNAIRE_JSP).forward(request, response);
				break;
			case "delete_questionnaire":
				System.out.println("Supprimer un questionnaire"); // Test
				break;
			case "activate_questionnaire":
				System.out.println("Activer un questionnaire"); // Test
				break;
			default:
				break;
			}
		}
		try {
			request.setAttribute("topics", this.topicsManagementDao.getTopics());
		} catch (DaoException e) {
			errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		if(action == null || !action.equals("add_questionnaire")){
			this.getServletContext().getRequestDispatcher(FORMS_MANAGEMENT_JSP).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newTopicName = null;
		String topicName = null;
		String questionnaireName = null;
		String errorMessage = null;
		String action =  request.getParameter("paction");
		if(action != null){
			switch (action) {
			case "add_topic":
				newTopicName = request.getParameter("newTopicName");
				try {
					this.topicsManagementDao.addTopic(newTopicName);
				} catch (DaoException e) {
					errorMessage = e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
				}
				break;
			case "add_questionnaire":
				topicName = request.getParameter("topicName");
				questionnaireName = request.getParameter("questionnaireName");
				try {
					this.topicsManagementDao.addQuestionnaire(topicName, questionnaireName);
				} catch (DaoException e) {
					errorMessage = e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
				}
				break;
			default:
				break;
			}
		}
		doGet(request, response);
	}
}