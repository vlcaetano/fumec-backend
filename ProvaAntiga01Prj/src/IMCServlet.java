
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IMCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IMCServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		exibe(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		exibe(request, response);
	}

	private void exibe(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();

		resp.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Primeira Avaliação");
		out.println("</title></head><body>");
		out.println("<h1>Cálculo de indicadores de saúde</h1>");
		out.println("<form action='imc' method='post'>");
		out.println("Altura: <input type='text' name='altura'> metros<br>");
		out.println("Peso: <input type='text' name='peso'> kg <br>");
		out.println("<label for='sexo'> Sexo maculino? </label>");
		out.println("<input type='checkbox' name='sexo' value='masc'> <br><br>");
		out.println("<input type='submit' value='Enviar'>");
		out.println("</form>");
		
		if ("POST".equals(req.getMethod())) {
			if (req.getParameter("peso").isBlank()) {
				out.println("<p>" + calcPesoIdeal(req) + "</p>");
			} else {
				out.println("<p>" + calcIMC(req) + "</p>");
			}
		}
		
		out.println("</body></html>");

		out.close();
	}

	private String calcIMC(HttpServletRequest req) {
		DecimalFormat df = new DecimalFormat("0.0");

		double altura = 0, peso = 0;
		double e, k;
		double imc;

		try {
			altura = Double.parseDouble(req.getParameter("altura"));
			peso = Double.parseDouble(req.getParameter("peso"));
		} catch (NumberFormatException err) {
			return "Valor da altura ou peso inválido";
		}

		if (altura <= 0 || peso <= 0)
			return "A altura e o peso devem ser positivos";

		if ("tradicional".equals(getInitParameter("tipoCalculo"))) {
			k = 1.0;
			e = 2.0;
		} else {
			// cálculo moderno
			k = 1.3;
			e = 2.5;
		}
		
		imc = (k * peso) / Math.pow(altura, e);
		String resposta = df.format(imc);
		
		if (imc < 18.5)
			return resposta + " - Abaixo do peso";
		else if (imc < 24.9)
			return resposta + " - Saudável";
		else if (imc < 29.9)
			return resposta + " - Sobrepeso";
		else if (imc < 34.5)
			return resposta + " - Obesidade grau I";
		else if (imc < 39.9)
			return resposta + " - Obesidade grau II (severa)";
		else
			return resposta + " - Obesidade grau III (mórbida)";
	}

	private String calcPesoIdeal(HttpServletRequest req) {
		double s;
		double altura = 0;
		double pesoIdeal = 0;

		if (req.getParameter("sexo") == null)
			s = 2.0;
		else
			s = 4.0;

		try {
			altura = Double.parseDouble(req.getParameter("altura"));
		} catch (NumberFormatException e) {
			return "Valor da altura inválido";
		}

		if (altura <= 0)
			return "A altura deve ser positiva";

		pesoIdeal = (altura * 100 - 100) - (altura * 100 - 150) / s;

		return "Peso ideal = " + pesoIdeal + "kg";
	}
}
