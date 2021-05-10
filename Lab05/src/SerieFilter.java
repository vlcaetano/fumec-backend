
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

public class SerieFilter implements Filter {

	public SerieFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		chain.doFilter(request, response);

		int erros = (Integer) session.getAttribute("erros");

		if (erros == 3) {
			// Verificação se a pontuação máxima foi ultrapassada
			int acertos = (Integer) session.getAttribute("acertos");

			ServletContext context = request.getServletContext();
			int pontuacaoMaxima = (Integer) context.getAttribute("pontuacaoMaxima");

			if (acertos > pontuacaoMaxima) {
				context.setAttribute("pontuacaoMaxima", acertos);
			}

			// Resetar o programa
			session.invalidate();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
