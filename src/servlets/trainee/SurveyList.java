package servlets.trainee;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Survey;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.trainee.SurveyListDao;


public class SurveyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SURVEY_LIST_JSP = "/trainee/survey_list.jsp";
	private SurveyListDao surveyListDao;
       
    public SurveyList() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
		DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.surveyListDao = daoFactory.getSurveyListDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		} 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("col");
		// TODO Auto-generated method stub
		try {
			List<Survey> surveyList = this.surveyListDao.getSurveyList();
			request.setAttribute("surveyList", surveyList);
			System.out.println(surveyList);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher(SURVEY_LIST_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
