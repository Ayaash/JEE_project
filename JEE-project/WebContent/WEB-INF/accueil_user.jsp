<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<%@ page import="pack.Utilisateur" %>
	<%@ page import="pack.Jeux" %>


	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Accueil</title>
</head>
<body>
	
	<h1>Bienvenue, <%= ((Utilisateur)request.getSession().getAttribute("utilisateur")).getPseudo() %></h1>


	<form method="post" action=${pageContext.request.contextPath}/accueil>
		<input type="submit" value="se deconnecter" name="deconnexion">
		<input type="submit" value="modifier son compte" name="modif">
		
		<p>Jeux Disponibles:</p>
		<% 
		List<Jeux> list=(List<Jeux>) request.getSession().getAttribute("listejeux");
		Iterator<Jeux> iter=list.iterator();
		while(iter.hasNext()){
			Jeux jeu=iter.next();
			out.println("<p>  "+jeu.toString()+"  <input type='submit' name='"+jeu.toString()+"' value='jouer!' > </p>");
		}
		%>
			
	</form>


</body>
</html>