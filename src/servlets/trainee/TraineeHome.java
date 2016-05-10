package servlets.trainee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TraineeHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TRAINEE_JSP = "/trainee/trainee.jsp";

    public TraineeHome() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(TRAINEE_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(TRAINEE_JSP).forward(request, response);
	}
}