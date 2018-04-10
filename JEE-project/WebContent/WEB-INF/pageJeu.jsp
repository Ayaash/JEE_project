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
	
	<h1>Vous jouez a <%= request.getSession().getAttribute("jeu") %></h1>


	<form method="post" action=${pageContext.request.contextPath}/jeu>
		<div class="jeu">
			<input type="submit" value="début de jeu" name="debut">
			<input type="submit" value="fin de jeu" name="fin">
		</div>
		
		
<!-- 		<p>Jeux Disponibles:</p> -->
		<% 
		if(request.getSession().getAttribute("partieencours")==null){
			/*List<Jeux> list=(List<Jeux>) request.getSession().getAttribute("listejeux");
			Iterator<Jeux> iter=list.iterator();
			while(iter.hasNext()){
				Jeux jeu=iter.next();
				out.println("<p>  "+jeu.toString()+"  <input type='submit' name='jouer'  id='"+jeu.toString()+"' value='jouer!' > </p>");
			}*/
			out.println("<input type='submit' value='Accueil' name='accueil'>");

		}
		%>
	
		
		
	</form>


</body>
</html>