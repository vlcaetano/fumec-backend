<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Cálculo Série</title>
	</head>
	<body>
		<h1>Cálculo Função Exponencial</h1>
		
		<form action="funcaoExponencial.jsp" method="post">
			<p/>Precisão desejada: <input type="text" name="precisao">
			
			<input type="submit" value="Calcular">
		</form>
		<%
			if("POST".equals(request.getMethod())) {
				try {
					double precisao = Double.parseDouble(request.getParameter("precisao"));
					if (precisao > 0 && precisao < 1) {
						session.setAttribute("precisao", precisao);
						response.sendRedirect("calculo");
					}
					
					out.print("<br> Informar um número entre 0 e 1");
				} catch (NumberFormatException e) {
					out.print("<br> Informar apenas números");
				}
			}
		%>
	</body>
</html>