
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AlertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AlertaServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		if (context.getAttribute("numConexoes") == null) {
			context.setAttribute("numConexoes", 0);
		}
		
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
			session.setAttribute("requisicoes", 0);
			
			int numConexoes = (Integer) context.getAttribute("numConexoes");
			context.setAttribute("numConexoes", ++numConexoes);
		}
		
		String resposta = "";
		
		if ((Integer) context.getAttribute("numConexoes") >= 3) {
			resposta = "<h1>Número máximo de sessões atingido</h1>";
		} else {
			int req = (Integer) session.getAttribute("requisicoes");
			
			switch (req) {
			case 0:
				resposta = "<h1 style=\"color:green;\">Alerta verde</h1>";
				req++;
				break;
			case 1:
				resposta = "<h1 style=\"color:yellow;\">Alerta amarelo</h1>";
				req++;
				break;
			case 2:
				resposta = "<h1 style=\"color:red;\">Alerta vermelho</h1>";
				req = 0;
				break;
			}

			session.setAttribute("requisicoes", req);
		}
		
		exibe(request, response, resposta);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Resposta");
		out.println("</title></head><body>");
		out.println(resposta);
		out.println("<p/><a href='logout'>finalizar sessão</a>");
		out.println("</body></html>");

		out.close();
	}
}
