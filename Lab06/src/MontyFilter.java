
import java.io.IOException;
import java.io.PrintWriter;

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
		
		chain.doFilter(request, response);
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		int pontuacao = (Integer) session.getAttribute("pontuacao");
		
		ServletContext context = request.getServletContext();
		int pontuacaoMaxima = (Integer) context.getAttribute("pontuacaoMaxima");
		
		if (pontuacao > pontuacaoMaxima) {
			pontuacaoMaxima = pontuacao;
			context.setAttribute("pontuacaoMaxima", pontuacaoMaxima);
		}
		
		PrintWriter out = response.getWriter();
		
		out.println("<br><br>Pontuação Máxima: " + pontuacaoMaxima);
		out.println("</body></html>");

		out.close();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
