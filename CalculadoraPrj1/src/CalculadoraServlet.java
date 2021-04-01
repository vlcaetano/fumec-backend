
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculadoraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CalculadoraServlet() {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String resposta = fazerConta(req);
		exibe(resp, resposta);
	}

	private String fazerConta(HttpServletRequest req) {
		double op1 = 0, op2 = 0;
		char operador = req.getParameter("operador").charAt(0);

		try {
			op1 = Double.parseDouble(req.getParameter("operando1"));
			op2 = Double.parseDouble(req.getParameter("operando2"));
		} catch (NumberFormatException e) {
			return "Informe dois números válidos";
		}

		switch (operador) {
		case '+':
			return "Resultado = " + (op1 + op2);
		case '-':
			return "Resultado = " + (op1 - op2);
		case '/':
			return "Resultado = " + (op1 / op2);
		case '*':
			return "Resultado = " + (op1 * op2);
		default:
			return "Erro";
		}
	}

	private void exibe(HttpServletResponse resp, String resposta) throws IOException {
		PrintWriter out = resp.getWriter();

		resp.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Resposta");
		out.println("</title></head><body>");
		out.println("<h1>Resposta</h1>");
		out.println("<p>" + resposta + "</p>");
		out.println("<a href=\"calculadora.html\">Voltar</a>");
		out.println("</body></html>");

		out.close();
	}

}
