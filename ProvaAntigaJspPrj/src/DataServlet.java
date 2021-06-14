

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibe(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int dia = Integer.parseInt(request.getParameter("dia"));
			int mes = Integer.parseInt(request.getParameter("mes"));
			int ano = Integer.parseInt(request.getParameter("ano"));
			
			if (CalculoDatas.valida(dia, mes, ano)) {
				GregorianCalendar dataAtual = new GregorianCalendar();
				GregorianCalendar dataFormatura = new GregorianCalendar(ano, (mes-1), dia);
				
				int diferenca = CalculoDatas.diferenca(dataFormatura, dataAtual);
				request.setAttribute("resposta", ("Diferença: " + diferenca));
			} else {
				request.setAttribute("resposta", "Data inválida");
			}
		} catch (NumberFormatException e) {
			request.setAttribute("resposta", "Data inválida");
		}
		
		exibe(request, response);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/formularioData.jsp");
		rd.forward(request, response);
	}
}
