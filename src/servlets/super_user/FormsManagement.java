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
	private static final String ADD_QUESTIONNAIRE_JSP = "/super_user/questionnaire.jsp";

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
		request.setCharacterEncoding("UTF-8");
		String errorMessage = null;
		String action =  request.getParameter("action");
		String topicName = null;
		String questionnaireName = null;
		if(action != null){
			switch (action) {
			case "delete_topic":
				// System.out.println("Supprimer un sujet"); // Test
				topicName = request.getParameter("topic_name");
				try {
					this.topicsManagementDao.deleteTopic(topicName);
				} catch (DaoException e) {
					errorMessage = e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
				}
				break;
			case "add_questionnaire":
				// System.out.println("Ajouter un questionnaire"); // Test
				topicName = request.getParameter("topic_name");
				// System.out.println(topicName); // Test
				request.setAttribute("topic_name", topicName);
				request.setAttribute("paction", "add_questionnaire");
				this.getServletContext().getRequestDispatcher(ADD_QUESTIONNAIRE_JSP).forward(request, response);
				break;
			case "delete_questionnaire":
				// System.out.println("Supprimer un questionnaire"); // Test
				topicName = request.getParameter("topic_name");
				questionnaireName = request.getParameter("questionnaire_name");
				try {
					this.topicsManagementDao.deleteQuestionnaire(topicName, questionnaireName);
				} catch (DaoException e) {
					errorMessage = e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
				}
				break;
			case "activate_questionnaire":
				topicName = request.getParameter("topic_name");
				questionnaireName = request.getParameter("questionnaire_name");
				// System.out.println("Activer un questionnaire : " + topicName + " " + questionnaireName); // Test
				try {
					this.topicsManagementDao.activateQuestionnaire(topicName, questionnaireName);
				} catch (DaoException e) {
					errorMessage = e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
				}
				break;
			case "modify_questionnaire":
				// System.out.println("Modifier un questionnaire"); // Test
				topicName = request.getParameter("topic_name");
				questionnaireName = request.getParameter("questionnaire_name");
				// System.out.println(topicName); // Test
				// System.out.println(questionnaireName); // Test
				request.setAttribute("topic_name", topicName);
				request.setAttribute("questionnaireName", questionnaireName);
				request.setAttribute("paction", "modify_questionnaire");
				this.getServletContext().getRequestDispatcher(ADD_QUESTIONNAIRE_JSP).forward(request, response);
			break;
			default:
				break;
			}
		}
		if(action == null || ( !action.equals("add_questionnaire")) && !action.equals("modify_questionnaire") ){
			try {
				request.setAttribute("topics", this.topicsManagementDao.getTopics());
			} catch (DaoException e) {
				errorMessage = e.getMessage();
				request.setAttribute("errorMessage", errorMessage);
			}
			this.getServletContext().getRequestDispatcher(FORMS_MANAGEMENT_JSP).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String newTopicName = null;
		String topicName = null;
		String oldQuestionnaireName = null;
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
			case "modify_questionnaire":
				topicName = request.getParameter("topicName");
				oldQuestionnaireName = request.getParameter("oldQuestionnaireName");
				questionnaireName = request.getParameter("questionnaireName");
				// System.out.println("topicName: " + topicName);
				// System.out.println("oldQuestionnaireName: " + oldQuestionnaireName);
				// System.out.println("questionnaireName: " + questionnaireName);
				try {
					this.topicsManagementDao.updateQuestionnaire(topicName, oldQuestionnaireName, questionnaireName);
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