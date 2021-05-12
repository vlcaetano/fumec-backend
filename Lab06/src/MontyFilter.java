
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MontyFilter implements Filter {

	public MontyFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		int pontuacao = (Integer) session.getAttribute("pontuacao");
		
		ServletContext context = request.getServletContext();
		int pontuacaoMaxima = (Integer) context.getAttribute("pontuacaoMaxima");
		
		if (pontuacao > pontuacaoMaxima) {
			context.setAttribute("pontuacaoMaxima", pontuacao);
		}
		
		chain.doFilter(request, response);

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
