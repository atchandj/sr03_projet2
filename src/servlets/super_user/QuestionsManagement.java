package servlets.super_user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.super_user.QuestionsManagementDao;

public class QuestionsManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String QUESTIONNAIRES_MANAGEMENT_JSP = "/super_user/questionnaires_management.jsp";   
	
    private QuestionsManagementDao questionnairesManagementDao;
	    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.questionnairesManagementDao = daoFactory.getQuestionsManagementDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }
	
    public QuestionsManagement() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String topicName = request.getParameter("topic_name");
		String questionnaireName = request.getParameter("questionnaire_name");
		// System.out.println(topicName); // Test
		// System.out.println(questionnaireName); // Test
		String errorMessage = null;
		try {
			request.setAttribute("questionnaire", this.questionnairesManagementDao.getQuestionnaire(topicName, questionnaireName));
		} catch (DaoException e) {
			errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		this.getServletContext().getRequestDispatcher(QUESTIONNAIRES_MANAGEMENT_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
