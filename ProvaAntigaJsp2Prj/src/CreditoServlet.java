
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreditoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreditoServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String resposta = "";
		
		HttpSession session = request.getSession();
		
		try {
			int creditosComprados = Integer.parseInt(request.getParameter("creditosAComprar"));
			
			if (creditosComprados < 1) {
				resposta = "Valor inválido informado";
			} else {
				session.setAttribute("creditos", creditosComprados);
				response.sendRedirect("aposta");
			}
			
		} catch (NumberFormatException e) {
			resposta = "Valor inválido informado";
		}
		
		exibe(request, response, resposta);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Compra de Créditos");
		out.println("</title></head><body>");
		out.println("<h1> Compra de Créditos </h1>");
		out.println("<form action='credito' method='post'>");
		out.println("<br> Créditos a comprar: <input type='text' name='creditosAComprar'/>");
		out.println("<p><input type='submit' value='Ok' />");
		out.println("<form /> <br>");
		out.println("<br><br>" + resposta);
		out.close();
	}

}
