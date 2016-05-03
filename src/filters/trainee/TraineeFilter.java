package filters.trainee;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.trainee.Trainee;

public class TraineeFilter implements Filter {
	private static final String CONNECTION_PAGE = "/connection";
    private static final String ATT_SESSION_TRAINEE = "trainee";
    
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req; // Cast
        HttpServletResponse response = (HttpServletResponse) res; // Cast

        // Initiation of the session engine
        HttpSession session = request.getSession();
        Trainee trainee = (Trainee) session.getAttribute(ATT_SESSION_TRAINEE);

		if(trainee != null){
            /* We display the hidden page. */
            chain.doFilter( request, response );
		}else{
            /* Redirection to the connection page */
			response.sendRedirect( request.getContextPath() + CONNECTION_PAGE );
		}
    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
