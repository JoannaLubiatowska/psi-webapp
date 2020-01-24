<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Strona główna - PSI Webapp</title>
</head>
<body>
	<h2>
		Witaj,
		<c:out value='sessionScope["loggedUser"].getFullName()' />
	</h2>

	<c:set var="message" scope="session" value='${requestScope["message"]}' />
	<c:if test="message != null">
		<p>
			<c:out value="${message}" />
		</p>
	</c:if>

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
