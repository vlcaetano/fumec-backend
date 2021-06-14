<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Cálculo de datas</title>
	</head>
	<body>
		<h1>Cálculo de datas</h1>
	
		<p/>Informe a data de conclusão:
		
		<form action="data" method="post">
			Dia: <input type="text" name="dia"> <br>
			Mês: <input type="text" name="mes"> <br>
			Ano: <input type="text" name="ano"> <br>
			
			<input type="submit" value="Calcular"> <br>
		</form>
		
		<%
			if("POST".equals(request.getMethod())) {
				out.print(request.getAttribute("resposta"));
			}
		%>
	</body>
</html>