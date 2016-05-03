package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuperUserMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUPER_USER_JSP = "/super_user/super_user.jsp";
	
    public SuperUserMenu() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(SUPER_USER_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(SUPER_USER_JSP).forward(request, response);
	}
}
