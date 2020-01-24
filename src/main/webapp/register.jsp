<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Rejestracja - PSI Webapp</title>
</head>
<body>
	<h2>Zarejestruj się</h2>

	<c:set var="message" scope="session" value='${requestScope["message"]}' />
	<c:if test="message != null">
		<p>
			<c:out value="${message}" />
		</p>
	</c:if>

	<form action="register" method="post">
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
				<th><label for="repeat-password-field">Powtórz hasło:</label></th>
				<td><input id="repeat-password-field" type="password" name="rp-password"></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">Zarejestruj</button></td>
			</tr>
		</table>
	</form>
	<p>
		Jeśli posiadasz konto, to <a href="index.jsp">zaloguj
			się</a>.
	</p>
</body>
</html>