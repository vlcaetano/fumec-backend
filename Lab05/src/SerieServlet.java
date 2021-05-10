
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SerieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SerieServlet() {
	}

	public void init(ServletConfig config) throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = request.getServletContext();
		int acertosTotais = (Integer) context.getAttribute("acertosTotais");
		int errosTotais = (Integer) context.getAttribute("errosTotais");
		int pontuacaoMaxima = (Integer) context.getAttribute("pontuacaoMaxima");

		exibe(request, response, "", 0, 0, acertosTotais, errosTotais, pontuacaoMaxima);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
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

		exibe(request, response, resposta, acertos, erros, acertosTotais, errosTotais, pontuacaoMaxima);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta, int acertos,
			int erros, int acertosTotais, int errosTotais, int pontuacaoMaxima) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Série");
		out.println("</title></head><body>");
		out.println("<form action='/Lab05/serie' method='post'>");
		out.println("Digite o valor de s(" + (acertos + 1) + ") <br> <input type='text' name='resposta'/>");
		out.println("<p><input type='submit'/>");
		out.println("<form /> <br>");
		out.println(resposta);
		out.println("<br><br>Acertos: " + acertos + " <br>Erros: " + erros);
		out.println("<br><br>Acertos Totais: " + acertosTotais + "<br>Erros Totais: " + errosTotais);
		out.println("<br><br>Pontuação Máxima: " + pontuacaoMaxima);
		out.println("</body></html>");

		out.close();
	}
}
