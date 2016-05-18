package servlets.trainee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.trainee.Attempt;
import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.trainee.DisplayResultDao;
import dao.trainee.QuestionnairesListDao;

public class DisplayResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ATT_SESSION_TRAINEE = "trainee";
	private static final String DISPLAY_RESULT_JSP = "/trainee/display_result.jsp";
	private static final String DETAIL_ATTEMPT_JSP = "/trainee/detail_attempt.jsp";
	private DisplayResultDao displayResultDao;
	private QuestionnairesListDao questionnairesListDao;
    
    public DisplayResult() {
        super();
    }
    
    public void init() throws ServletException {
		DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.displayResultDao = daoFactory.getDisplayResultDao();
			this.questionnairesListDao = daoFactory.getTopicsListDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		} 
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String stringAttemptId =  request.getParameter("attemptId");
		Trainee trainee = (Trainee) session.getAttribute(ATT_SESSION_TRAINEE);
		
		if(stringAttemptId != null){
			try {
				if(stringAttemptId.matches("[-+]?\\d+(\\.\\d+)?") != true)
					throw new Exception("L'identifiant du parcours n'est pas un nombre valide.");
				int attemptId = Integer.parseInt(stringAttemptId);
				Attempt attempt = this.displayResultDao.getAttempt(trainee.getId(), attemptId);
				attempt.setAttemptedAnswers(this.displayResultDao.getAttemptedAnswers(attemptId));
				request.setAttribute("attempt", attempt);
				request.setAttribute("questions", this.questionnairesListDao.getQuestions(attempt.getQuestionnaireId()));
				this.getServletContext().getRequestDispatcher(DETAIL_ATTEMPT_JSP).forward(request, response);
			} catch (Exception e1) {
				request.setAttribute("errorMessage", e1.getMessage());
				try {
					 request.setAttribute("attempts", this.displayResultDao.getAttemptsList(trainee.getId()));
				} catch (DaoException e2) {
					request.setAttribute("errorMessage", e2.getMessage());
				}
				this.getServletContext().getRequestDispatcher(DISPLAY_RESULT_JSP).forward(request, response);
			}
		}
		else{
			try {
				 request.setAttribute("attempts", this.displayResultDao.getAttemptsList(trainee.getId()));
			} catch (DaoException e) {
				request.setAttribute("errorMessage", e.getMessage());
			}
			this.getServletContext().getRequestDispatcher(DISPLAY_RESULT_JSP).forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
