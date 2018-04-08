<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
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