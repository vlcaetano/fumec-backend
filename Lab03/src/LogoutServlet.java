

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogoutServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		ServletContext context = getServletContext();
		
		int numConexoes = (Integer) context.getAttribute("numConexoes");
		context.setAttribute("numConexoes", --numConexoes);
		
		PrintWriter out = response.getWriter();
		out.println("<h1>Sessão finalizada</h1>");
		out.close();
	}

}
