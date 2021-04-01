
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculadoraServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CalculadoraServlet2() {
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
		out.println("Resposta");
		out.println("</title></head><body>");
		out.println("<h1>Calculadora</h1>");
		out.println("<form action=\"calc\" method=\"post\">");
		out.println("<input type=\"text\" name=\"operando1\">");
		out.println("<select name=\"operador\">");
		out.println("<option>+</option><option>-</option><option>*</option><option>/</option>");
		out.println("</select>");
		out.println("<input type=\"text\" name=\"operando2\">");
		out.println("<input type=\"submit\">");
		out.println("</form>");
		if ("POST".equals(req.getMethod())) {
			out.println("<p>" + fazerConta(req) + "</p>");
		}
		out.println("</body></html>");

		out.close();
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
}
