

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

public class MateriasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MateriasServlet() {
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
			int periodo = 0;
			String titulo = "";
			
			if (request.getParameter("pesquisar") == null) {
				periodo = Integer.parseInt(request.getParameter("periodo"));
				titulo = request.getParameter("titulo");
			}
			
			connection = DB.getConnection();

			if (request.getParameter("adicionar") != null) {
				ps = connection.prepareStatement("insert into materia (titulo, periodo) values (?,?)");

				ps.setString(1, titulo);
				ps.setInt(2, periodo);

				int linhasAfetadas = ps.executeUpdate();
				if (linhasAfetadas > 0) {
					resposta = "Matéria salva com sucesso";
				}
				
			} else if (request.getParameter("deletar") != null) {
				ps = connection.prepareStatement("delete from materia where titulo = ? and periodo = ?");

				ps.setString(1, titulo);
				ps.setInt(2, periodo);

				int linhasAfetadas = ps.executeUpdate();
				if (linhasAfetadas > 0) {
					resposta = "Matéria deletada com sucesso";
				}
				
			} else if (request.getParameter("editar") != null) {
				ps = connection.prepareStatement("update materia set periodo= ? where titulo = ?");

				ps.setInt(1, periodo);
				ps.setString(2, titulo);

				int linhasAfetadas = ps.executeUpdate();
				if (linhasAfetadas > 0) {
					resposta = "Matéria editada com sucesso";
				}
			} else { //pesquisar
				ps = connection.prepareStatement("select * from materia");
				ResultSet rs = ps.executeQuery();
				while(rs.next())
					resposta += (rs.getString(1) + "/" + rs.getString(2) + "<br>");
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
		out.println("Materias");
		out.println("</title></head><body>");
		out.println("<form action='materias' method='post'>");
		out.println("<h1> Materias </h1>");
		out.println("<br> Título: <input type='text' name='titulo'/>");
		out.println("<br> Período: <input type='text' name='periodo'/>");
		out.println("<p><input type='submit' value='Adicionar' name='adicionar'/>");
		out.println("<p><input type='submit' value='Deletar' name='deletar'/>");
		out.println("<p><input type='submit' value='Editar' name='editar'/>");
		out.println("<p><input type='submit' value='Pesquisar' name='pesquisar'/>");
		out.println("<form /> <br>");
		out.println("<br><br>" + resposta);
		out.close();
	}
}
