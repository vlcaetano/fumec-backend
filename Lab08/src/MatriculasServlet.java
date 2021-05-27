

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MatriculasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public MatriculasServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String resposta = "";
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			int numero = 0;
			String titulo = "";
			int nota = 0;
			
			if (request.getParameter("pesquisar") == null) {
				numero = Integer.parseInt(request.getParameter("numero"));
				titulo = request.getParameter("titulo");
				nota = Integer.parseInt(request.getParameter("nota"));
			}
			
			connection = DB.getConnection();

			if (request.getParameter("adicionar") != null) {
				ps = connection.prepareStatement("insert into matricula (numero, titulo, nota) values (?,?,?)");

				ps.setInt(1, numero);
				ps.setString(2, titulo);
				ps.setInt(3, nota);

				int linhasAfetadas = ps.executeUpdate();
				if (linhasAfetadas > 0) {
					resposta = "Matéria salva com sucesso";
				}
				
			} else if (request.getParameter("deletar") != null) {
				ps = connection.prepareStatement("delete from matricula where numero = ? and titulo = ? and nota = ?");

				ps.setInt(1, numero);
				ps.setString(2, titulo);
				ps.setInt(3, nota);

				int linhasAfetadas = ps.executeUpdate();
				if (linhasAfetadas > 0) {
					resposta = "Matéria deletada com sucesso";
				}
				
			} else if (request.getParameter("editar") != null) {
				ps = connection.prepareStatement("update matricula set nota = ? where numero = ? and titulo = ?");

				ps.setInt(1, nota);
				ps.setInt(2, numero);
				ps.setString(3, titulo);

				int linhasAfetadas = ps.executeUpdate();
				if (linhasAfetadas > 0) {
					resposta = "Matéria editada com sucesso";
				}
			} else { //pesquisar
				ps = connection.prepareStatement("select * from matricula");
				ResultSet rs = ps.executeQuery();
				while(rs.next())
					resposta += (rs.getString(1) + " / " + rs.getString(2) + " / " + rs.getString(3) + "<br>");
			}
			

		} catch (SQLException e) {
			resposta = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			resposta = "Erro na obtenção do contexto inicial: " + e.getMessage();
		} catch (MinhaException e) {
			resposta = e.getMessage();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				resposta = e.getMessage();
			}
		}

		exibe(request, response, resposta);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Matrículas");
		out.println("</title></head><body>");
		out.println("<form action='matriculas' method='post'>");
		out.println("<h1> Matrículas </h1>");
		out.println("<br> Número: <input type='text' name='numero'/>");
		out.println("<br> Título: <input type='text' name='titulo'/>");
		out.println("<br> Nota: <input type='text' name='nota'/>");
		out.println("<p><input type='submit' value='Adicionar' name='adicionar'/>");
		out.println("<p><input type='submit' value='Deletar' name='deletar'/>");
		out.println("<p><input type='submit' value='Editar' name='editar'/>");
		out.println("<p><input type='submit' value='Pesquisar' name='pesquisar'/>");
		out.println("<form /> <br>");
		out.println("<br><br>" + resposta);
		out.close();
	}
}
