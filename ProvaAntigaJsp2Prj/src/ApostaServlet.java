
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApostaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int creditosMaquina;
	
	public ApostaServlet() {
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		creditosMaquina = Integer.parseInt(config.getInitParameter("valor"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute("creditosMaquina", creditosMaquina);
		
		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int creditosJogador = (int) session.getAttribute("creditos");
		int creditosMaquina = (int) session.getAttribute("creditosMaquina");
		
		int primeiroDado = (int) (Math.ceil(Math.random() * 6));
		int segundoDado = (int) (Math.ceil(Math.random() * 6));
		int terceiroDado = (int) (Math.ceil(Math.random() * 6));
		
		int resultadoFinal = primeiroDado * 100 + segundoDado * 10 + terceiroDado;
		
		if (resultadoFinal == 666) {
			creditosJogador += creditosMaquina;
			creditosMaquina = 0;
		} else if (resultadoFinal == 555 || resultadoFinal == 444) {
			creditosJogador += creditosMaquina/2;
			creditosMaquina = creditosMaquina/2;
		} else if (resultadoFinal == 333 || resultadoFinal == 222 || resultadoFinal == 111) {
			creditosMaquina += creditosJogador;
			creditosJogador = 0;
		} else if (primeiroDado != segundoDado && primeiroDado != terceiroDado && segundoDado != terceiroDado) {
			creditosJogador += 5;
			creditosMaquina -= 5;
		} else {
			creditosJogador -= 2;
			creditosMaquina += 2;
		}
		
		session.setAttribute("creditos", creditosJogador);
		session.setAttribute("creditosMaquina", creditosMaquina);
		
		exibe(request, response, ""+resultadoFinal);
	}

	
	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		HttpSession session = request.getSession();
		int creditosJogador = (int) session.getAttribute("creditos");
		int creditosMaquina = (int) session.getAttribute("creditosMaquina");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Máquina caça-níquel");
		out.println("</title></head><body>");
		out.println("<h1> Máquina caça-níquel </h1>");
		out.println("<form action='aposta' method='post'>");
		out.println("<br> Jogador: " + creditosJogador);
		out.println("<br> Máquina: " + creditosMaquina);
		out.println("<p><input type='submit' value='Apostar' />");
		out.println("<form /> <br>");
		out.println("<br><br>Lance: " + resposta);
		out.close();
	}

}
