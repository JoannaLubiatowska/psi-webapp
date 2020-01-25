<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Zmiana hasła - PSI Webapp</title>
</head>
<body>
	<h2>Zmień hasło</h2>

	<c:if test="${requestScope.message != null}">
		<p><c:out value="${requestScope.message}" escapeXml="false" /></p>
	</c:if>

	<form action="changePass" method="post">
		<table>
			<tr>
				<th><label for="old-password-field">Obecne hasło:</label></th>
				<td><input id="old-password-field" type="password" name="old-password"></td>
			</tr>
			<tr>
				<th><label for="new-password-field">Nowe hasło:</label></th>
				<td><input id="new-password-field" type="password" name="new-password"></td>
			</tr>
			<tr>
				<th><label for="repeat-password-field">Powtórz nowe hasło:</label></th>
				<td><input id="repeat-password-field" type="password" name="rp-new-password"></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">Zmień hasło</button></td>
			</tr>
		</table>
	</form>
</body>
</html>