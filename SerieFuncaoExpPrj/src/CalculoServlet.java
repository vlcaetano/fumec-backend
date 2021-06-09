
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CalculoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CalculoServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		double precisao = (double) session.getAttribute("precisao");

		String resposta;

		try {
			double x = Double.parseDouble(request.getParameter("x"));

			resposta = calcularFuncaoExponencial(x, precisao, session);

		} catch (NumberFormatException e) {
			resposta = "Informe apenas números";
		}

		exibe(request, response, resposta);
	}

	private String calcularFuncaoExponencial(double x, double precisao, HttpSession session) throws IOException {
		
		int termosUsados = (int) session.getAttribute("termosUsados");

		double resultado = 1;

		double termo = 1;
		int cont = 1;
		double numerador;
		double denominador = 1;

		while (termo >= precisao) {
			numerador = Math.pow(x, cont);
			denominador *= cont;

			termo = numerador / denominador;
			resultado += termo;

			cont++;
		}
		
		termosUsados += cont;
		session.setAttribute("termosUsados", termosUsados);

		return "Foram calculados " + cont + " termo(s) e o resultado foi: " + resultado;
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html>" 
				+ "	<head>" 
				+ "		<meta charset='ISO-8859-1'>"
				+ "		<title>Cálculo Série</title>" + "	</head>" 
				+ "	<body>"
				+ "		<h1>Cálculo Função Exponencial</h1>" 
				+ "		<form action='calculo' method='post'>"
				+ "			<p/>Valor de x: <input type='text' name='x'>"
				+ "			<input type='submit' value='Calcular'>" 
				+ "		</form>"
				+ "<br><a href=\"funcaoExponencial.jsp\">Voltar</a>" 
				+ "<br><br>" + resposta);
//				+ "</body>"
//				+ "</html>");
//		out.close();
	}
}
