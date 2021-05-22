
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PotenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PotenciaServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resposta = "";

		try {
			double base = Double.parseDouble(request.getParameter("base"));
			double expoente = Double.parseDouble(request.getParameter("expoente"));

			double resultado = 0;

			if (expoente < 0) {
				resultado = 1 / calculoPotencia(base, -expoente);
			} else if (expoente % 2 == 0) {
				resultado = calculoPotencia(calculoPotencia(base, expoente / 2), 2);
			} else {
				resultado = calculoPotencia(base, (expoente - 1)) * base;
			}

			resposta = "Resultado: " + resultado;

		} catch (NumberFormatException e) {
			resposta = "Informe apenas números";
		}

		exibe(request, response, resposta);
	}

	private double calculoPotencia(double base, double expoente) {
		double resp = 1;
		while (expoente > 0) {
			resp *= base;
			expoente--;
		}
		return resp;
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Potência");
		out.println("</title></head><body>");
		out.println("<form action='/calculo/potencia' method='post'>");
		out.println("<h1> Cálculo Potência </h1>");
		out.println("<br> Base: <input type='text' name='base'/>");
		out.println("<br> Expoente: <input type='text' name='expoente'/>");
		out.println("<p><input type='submit'/>");
		out.println("<form /> <br>");
		out.println("<br><br>" + resposta);
		out.println("<br> <a href='/calculo/login'>Sair</a>");
		out.close();
	}
}
