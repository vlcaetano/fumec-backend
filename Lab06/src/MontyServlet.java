
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MontyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MontyServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		reiniciar(session);

		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		int rodada = (Integer) session.getAttribute("rodada");
		int portaCerta = (Integer) session.getAttribute("portaCerta");
		String resposta = "";

		try {
			int escolhaUsuario = Integer.parseInt(request.getParameter("escolha"));
			
			// Consistência: O usuário só pode escolher portas de 1 até 3
			if (escolhaUsuario < 1 || escolhaUsuario > 3) {
				resposta = "Informe apenas números entre 1 e 3";
			} else {
				if (rodada == 1) {
					// Primeira rodada: O usuário escolheu uma porta e outra é aberta
					for (int i = 1; i <= 3; i++) {
						if (i != escolhaUsuario && i != portaCerta) {
							session.setAttribute("portaAberta", i);
							break;
						}
					}

					session.setAttribute("rodada", 2);
				} else {
					// Segunda rodada: O usuário escolheu entre as duas portas restantes
					int pontuacao = (Integer) session.getAttribute("pontuacao");
					int portaAberta = (Integer) session.getAttribute("portaAberta");
					
					// Consistência: Não se pode escolher a porta já aberta
					if (escolhaUsuario == portaAberta) {
						resposta = "A porta escolhida já foi aberta";
					} else if (escolhaUsuario == portaCerta) {
						resposta = "Parabéns, você acertou!";
						session.setAttribute("pontuacao", (pontuacao + 10));
						reiniciar(session);
					} else {
						resposta = "Você errou!";
						session.setAttribute("pontuacao", (pontuacao / 2));
						reiniciar(session);
					}
				}
			}

			
		} catch (NumberFormatException e) {
			resposta = "Informe apenas números";
		}

		exibe(request, response, resposta);
	}

	private void reiniciar(HttpSession session) {
		Random r = new Random();
		int portaCerta = r.nextInt(3) + 1;
		//System.out.println(portaCerta);
		
		session.setAttribute("portaCerta", portaCerta);
		session.setAttribute("portaAberta", 0);
		session.setAttribute("rodada", 1);
	}
	
	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		HttpSession session = request.getSession();
		int portaAberta = (Integer) session.getAttribute("portaAberta");
		int pontuacao = (Integer) session.getAttribute("pontuacao");
		
		ServletContext context = request.getServletContext();
		int pontuacaoMaxima = (Integer) context.getAttribute("pontuacaoMaxima");
		
		String portas = "";
		for (int i = 1; i <= 3; i++) {
			if (i == portaAberta) {
				portas += " *";
			} else {
				portas += " " + i;
			}
		}

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Série");
		out.println("</title></head><body>");
		out.println("<form action='/lab06/monty' method='post'>");
		out.println("<h1> Jogo de Monty Hall </h1>");
		out.println("<br> Portas:" + portas);
		out.println("<br> Escolha: <input type='text' name='escolha'/>");
		out.println("<p><input type='submit'/>");
		out.println("<form /> <br>");
		out.println("<br><br>" + resposta);
		out.println("<br><br>Pontuação: " + pontuacao);
		out.println("<br> <a href='/lab06/monty'>Reiniciar</a>");
		out.println("<br><br>Pontuação Máxima: " + pontuacaoMaxima);
		out.println("</body></html>");

		out.close();
	}
}
