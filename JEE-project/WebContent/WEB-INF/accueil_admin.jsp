<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ page import="pack.Utilisateur" %>


	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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