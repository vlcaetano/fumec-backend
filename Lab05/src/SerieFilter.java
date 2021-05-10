
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
		
		if("POST".equals(((HttpServletRequest) request).getMethod())) {
			testarResposta(request, response);
		}

		chain.doFilter(request, response);
	}

	private void testarResposta(ServletRequest request, ServletResponse response) {
		HttpSession session = ((HttpServletRequest) request).getSession();
		int acertos = (Integer) session.getAttribute("acertos");
		int erros = (Integer) session.getAttribute("erros");

		ServletContext context = request.getServletContext();
		int acertosTotais = (Integer) context.getAttribute("acertosTotais");
		int errosTotais = (Integer) context.getAttribute("errosTotais");
		int pontuacaoMaxima = (Integer) context.getAttribute("pontuacaoMaxima");

		String resposta = "";

		try {
			int numResp = Integer.parseInt(request.getParameter("resposta"));
			
			int[] s = (int[]) session.getAttribute("termos");

			int proximoTermo;
			if (acertos < 3) {
				proximoTermo = s[acertos];
			} else {
				proximoTermo = s[2] + 2 * s[0];
			}

			if (numResp == proximoTermo) {
				if (acertos >= 3) {
					s[0] = s[1];
					s[1] = s[2];
					s[2] = proximoTermo;

					session.setAttribute("termos", s);
				}

				session.setAttribute("acertos", ++acertos);
				context.setAttribute("acertosTotais", ++acertosTotais);

				resposta = "Acertou!";
			} else {
				session.setAttribute("erros", ++erros);
				context.setAttribute("errosTotais", ++errosTotais);

				resposta = "Errou!";
			}

		} catch (NumberFormatException e) {
			resposta = "informe apenas números";
		}

		session.setAttribute("resposta", resposta);
		
		if (erros == 3) {
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
