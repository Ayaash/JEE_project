<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<%@ page import="pack.Utilisateur" %>

	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Accueil</title>
</head>
<body>

<h1>Bienvenue, administrateur <%= ((Utilisateur)request.getSession().getAttribute("utilisateur")).getPseudo() %></h1>

	<form method="post" action=${pageContext.request.contextPath}/accueil>
		<input type="submit" value="se deconnecter" name="deconnexion">
		<input type="submit" value="modifier son compte" name="modif">
			
	</form>


</body>
</html>