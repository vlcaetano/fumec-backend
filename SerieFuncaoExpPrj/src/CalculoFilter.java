
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CalculoFilter implements Filter {
	
	private int limiteDeTermos = 10;

	public CalculoFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		if (session.getAttribute("precisao") == null) {
//			RequestDispatcher rd = request.getRequestDispatcher("funcaoExponencial.jsp");
//			rd.forward(request, response);
			
			((HttpServletResponse) response).sendRedirect("funcaoExponencial.jsp");
		}

		chain.doFilter(request, response);
		
		int termosUsados = (int) session.getAttribute("termosUsados");
		
		if (termosUsados > limiteDeTermos) {
			session.invalidate();
			((HttpServletResponse) response).sendRedirect("funcaoExponencial.jsp");
		} else {
			PrintWriter out = response.getWriter();
			out.println(
			  "<br><br> Termos restantes: " + (limiteDeTermos - termosUsados)
			+ "</body>"
			+ "</html>");
			out.close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		limiteDeTermos = Integer.parseInt(fConfig.getInitParameter("limiteDeTermos"));
	}

}
