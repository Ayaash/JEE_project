
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<%@ page import="pack.Utilisateur" %>
	<%@ page import="pack.Jeu" %>
	
	<%@page import="java.util.Iterator"%>
	<%@page import="java.util.List"%>


	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Accueil</title>
</head>
<body>
	
	<h1>Vous jouez a <%= ((Jeu) request.getSession().getAttribute("jeu")).getNom() %></h1>


	<form method="post" action=${pageContext.request.contextPath}/jeu>
		<div class="jeu">
			<input type="submit" value="début de jeu" name="debut">
			<input type="submit" value="fin de jeu" name="fin">
		</div>
		
		
		<% 
		if(request.getSession().getAttribute("partieencours")==null){

			out.println("<input type='submit' value='Accueil' name='accueil'>");

		}
		%>
	
		
		
	</form>


</body>
</html>