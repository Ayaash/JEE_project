<%@page import="pack.LienMySQL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Accueil</title>
</head>
<body>

	<h1>Bienvenue sur notre site de jeu en ligne</h1>

	<form method="post" action=${pageContext.request.contextPath}/accueil>
		<input type="submit" value="se connecter" name="connexion">
		<input type="submit" value="s'inscrire" name="inscription">
			
	</form>


</body>
</html>