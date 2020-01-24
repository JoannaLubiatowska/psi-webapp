<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<c:choose>
	<c:when test="${sessionScope.loggedUser != null}">
		<c:import url="mainPage.jsp"></c:import>
	</c:when>
	
	<c:otherwise>
		<c:import url="login.jsp"></c:import>
	</c:otherwise>
</c:choose>