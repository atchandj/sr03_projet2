package servlets.trainee;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.trainee.Answer;
import beans.trainee.Attempt;
import beans.trainee.GoodAnswer;
import beans.trainee.Question;
import beans.trainee.Topic;
import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.trainee.QuestionnairesListDao;


public class SurveyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SURVEY_LIST_JSP = "/trainee/survey_list.jsp";
	private static final String ANSWER_SURVEY_JSP = "/trainee/answer_survey.jsp";
	private static final String ATT_SESSION_QUESTIONS = "questions";
	private static final String ATT_SESSION_ATTEMPT = "attempt";
	private static final String ATT_SESSION_TRAINEE = "trainee";
	private QuestionnairesListDao questionnairesListDao;
       
    public SurveyList() {
        super();
    }

    public void init() throws ServletException {
		DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.questionnairesListDao = daoFactory.getTopicsListDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		} 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action =  request.getParameter("action");
		HttpSession session = request.getSession();
		
		if(action != null){
			
			try {
				if(request.getParameter("questionnaire") == null)
					throw new Exception("Questionnaire introuvable");
				int idQuestionnaire = Integer.parseInt(request.getParameter("questionnaire"));
				Attempt attempt = new Attempt();
				attempt.setQuestionnaireId(idQuestionnaire);
				List<Question> questions = new ArrayList<Question>();
				try {
					questions = this.questionnairesListDao.getQuestions(idQuestionnaire);	
				} catch (DaoException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
				if(questions.isEmpty()){
					if(request.getAttribute("errorMessage") != null)
						throw new Exception((String) request.getAttribute("errorMessage"));
					else
						throw new Exception("Aucune question dans ce questionnaire");
				}					
				
				// Initiation of the session engine
				session.setAttribute(ATT_SESSION_QUESTIONS, questions); // Creation of a session variable for the questions
				session.setAttribute(ATT_SESSION_ATTEMPT, attempt); // Creation of a session variable for an attempt
							
				int index =  0;
				request.setAttribute("question", questions.get(index));
				request.setAttribute("index", index);
				request.setAttribute("end", false);	
				this.getServletContext().getRequestDispatcher(ANSWER_SURVEY_JSP).forward(request, response);
			} catch (Exception e) {
				request.setAttribute("errorMessage", e.getMessage());
				try {
					List<Topic> topics = this.questionnairesListDao.getActivatedQuestionnaire();
					request.setAttribute("topics", topics);
				} catch (DaoException e1) {
					request.setAttribute("errorMessage", e1.getMessage());
				}
				this.getServletContext().getRequestDispatcher(SURVEY_LIST_JSP).forward(request, response);
			}						
		}
		else{
			try {
				List<Topic> topics = this.questionnairesListDao.getActivatedQuestionnaire();
				request.setAttribute("topics", topics);
			} catch (DaoException e) {
				request.setAttribute("errorMessage", e.getMessage());
			}
			this.getServletContext().getRequestDispatcher(SURVEY_LIST_JSP).forward(request, response);		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Attempt attempt = (Attempt) session.getAttribute(ATT_SESSION_ATTEMPT); //We recover the session variable for an attempt
		@SuppressWarnings("unchecked")
		List<Question> questions = (List<Question>) session.getAttribute(ATT_SESSION_QUESTIONS);
		
		if(request.getParameter("answerId") != null && attempt != null && questions != null){
			int index = Integer.parseInt(request.getParameter("index")),
			    answerId = Integer.parseInt(request.getParameter("answerId"));
				
			for(Answer a : questions.get(index).getAnswers()){
				if(answerId == a.getId()){
					attempt.getAttemptedAnswers().add(a); //Put the answer in the attempt
					if(a.getClass().equals(GoodAnswer.class) ) 
						attempt.increaseScore(); 					
					session.setAttribute(ATT_SESSION_ATTEMPT, attempt);
				}
				else{
					if(attempt.getAttemptedAnswers().contains(a)){
						attempt.getAttemptedAnswers().remove(a);
						if(a.getClass().equals(GoodAnswer.class) )
							attempt.decreaseScore();
					}
				}
			}
			
			if(index + 1 < questions.size() ){
				request.setAttribute("end", false);	
				request.setAttribute("question", questions.get(index + 1));
				request.setAttribute("index", index + 1);	
				this.getServletContext().getRequestDispatcher(ANSWER_SURVEY_JSP).forward(request, response);
			}
			else{
				Trainee trainee = (Trainee) session.getAttribute(ATT_SESSION_TRAINEE);
				request.setAttribute("end", true);
				attempt.setEnd(new Timestamp(new Date().getTime()));
				attempt.setDurationInSeconds();
				request.setAttribute("questions", questions);
				request.setAttribute("attempt", attempt);				
				attempt.setAnswersUnique();
				System.out.println(attempt.getAttemptedAnswers());
				try {
					this.questionnairesListDao.addAttempt(trainee, attempt);
					session.removeAttribute(ATT_SESSION_ATTEMPT);
					session.removeAttribute(ATT_SESSION_QUESTIONS);
				} catch (DaoException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
				this.getServletContext().getRequestDispatcher(ANSWER_SURVEY_JSP).forward(request, response);;
			}
			
		}
		else{
			request.setAttribute("errorMessage", "Parcours déjà terminé !");
			doGet(request, response);
		}			
	}

}
