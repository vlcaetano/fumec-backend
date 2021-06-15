
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

public class ApostaFilter implements Filter {

	public ApostaFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();

		int creditosJogador = (int) session.getAttribute("creditos");
		
		if (creditosJogador < 1) {
			((HttpServletResponse) response).sendRedirect("credito");
		} else {
			int creditosMaquina = (int) session.getAttribute("creditosMaquina");
			if (creditosMaquina < 1) {
				session.setAttribute("creditosMaquina", 2000);
			}
			
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
