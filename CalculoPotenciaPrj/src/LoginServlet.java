
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.invalidate();

		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String codigo = request.getParameter("codigo");
		String senha = request.getParameter("senha");
		
		if ("teste".equals(codigo) && "teste".equals(senha)) {
			request.getSession();
			response.sendRedirect("potencia");
		} else {
			exibe(request, response, "Código ou senha incorreta, digite novamente");
		}
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Login");
		out.println("</title></head><body>");
		out.println("<form action='/calculo/login' method='post'>");
		out.println("<h1> Login </h1>");
		out.println("<br> Código: <input type='text' name='codigo'/>");
		out.println("<br> Senha: <input type='text' name='senha'/>");
		out.println("<p><input type='submit'/>");
		out.println("<form /> <br>");
		out.println("<br><br>" + resposta);
		out.close();
	}
}
