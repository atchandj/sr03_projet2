package servlets.trainee;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.trainee.Question;
import beans.trainee.Topic;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.trainee.TopicsListDao;


public class SurveyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SURVEY_LIST_JSP = "/trainee/survey_list.jsp";
	private static final String ANSWER_SURVEY_JSP = "/trainee/answer_survey.jsp";
	private TopicsListDao topicsListDao;
       
    public SurveyList() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
		DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.topicsListDao = daoFactory.getTopicsListDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		} 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action =  request.getParameter("action");
		int index;
		
		if(action != null){
			int idQuestionnaire = Integer.parseInt(request.getParameter("questionnaire"));
			try {
				List<Question> questions = this.topicsListDao.getQuestions(idQuestionnaire);
				request.setAttribute("questions", questions);
				
				index = (request.getParameter("index") != null) ? Integer.parseInt(request.getParameter("index")) : 0 ;
				request.setAttribute("answers", this.topicsListDao.getAnswer(questions.get(index).getId()));
				request.setAttribute("index", index);
			} catch (DaoException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
			}
			this.getServletContext().getRequestDispatcher(ANSWER_SURVEY_JSP).forward(request, response);
		}
		else{
			try {
				List<Topic> topics = this.topicsListDao.getActivatedTopics();
				request.setAttribute("topics", topics);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
			}
			this.getServletContext().getRequestDispatcher(SURVEY_LIST_JSP).forward(request, response);		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
