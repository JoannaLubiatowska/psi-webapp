<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Logowanie - PSI Webapp</title>
</head>
<body>
	<h2>Zaloguj się</h2>

	<p>
	<% if ( request.getAttribute("message") != null ) { %>
		<%=request.getAttribute( "message" )%>
	<% } %>
	<p>

	<form action="login" method="post">
		<table>
			<tr>
				<th><label for="login-field">Nazwa użytkownika:</label></th>
				<td><input id="login-field" type="text" name="login"></td>
			</tr>
			<tr>
				<th><label for="password-field">Hasło:</label></th>
				<td><input id="password-field" type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">Zaloguj</button></td>
			</tr>
		</table>
	</form>
	<p>
		Jeśli nie posiadasz konta, to <a href="register.jsp">zarejestruj
			się</a>.
	</p>
</body>
</html>
