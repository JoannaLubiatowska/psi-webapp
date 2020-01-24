<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Strona główna - PSI Webapp</title>
</head>
<body>
	<h2>
		Witaj,
		<c:out value='${sessionScope["loggedUser"].getLogin()}' />
	</h2>

	<p>
	<% if ( request.getAttribute("message") != null ) { %>
		<%=request.getAttribute( "message" )%>
	<% } %>
	<p>

	<table>
		<tr>
			<th><a href="changePass">Zmień hasło</a></th>
		</tr>
		<tr>
			<th><a href="logout">Wyloguj</a></th>
		</tr>
	</table>
</body>
</html>
