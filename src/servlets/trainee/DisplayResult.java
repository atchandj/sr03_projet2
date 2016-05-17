package servlets.trainee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.trainee.Trainee;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.trainee.DisplayResultDao;

public class DisplayResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ATT_SESSION_TRAINEE = "trainee";
	private static final String DISPLAY_RESULT_JSP = "/trainee/display_result.jsp";
	private DisplayResultDao displayResultDao;
    
    public DisplayResult() {
        super();
    }
    
    public void init() throws ServletException {
		DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.displayResultDao = daoFactory.getDisplayResultDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		} 
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Trainee trainee = (Trainee) session.getAttribute(ATT_SESSION_TRAINEE);
		
		try {
			 request.setAttribute("attempts", this.displayResultDao.getAttempts(trainee.getId()));
		} catch (DaoException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher(DISPLAY_RESULT_JSP).forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
