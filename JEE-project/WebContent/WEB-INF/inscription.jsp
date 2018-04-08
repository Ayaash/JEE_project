<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
	<%@ page import="pack.Jeux" %>
	

<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Inscription</title>
</head>
<body>
	<form method="post" action=${pageContext.request.contextPath}/inscription>
	
		<p>Pseudo: <input type="text" name="pseudo" required></p>
		
		<p class="erreur"> <% if(request.getAttribute("msgpseudo")!=null){
			out.println("Ce pseudo est deja utilis� par un autre utilisateur");
		} %> </p>
		
		<p>Mot de passe: <input type="password" name="mdp" required></p>
		
		<p>Confirmation du mot de passe: <input type="password" name="mdp2" required></p>
		
		<p class="erreur"> <% if(request.getAttribute("msgmdp")!=null){
			out.println("Les mots de passes doivent �tres identiques");
		} %> </p>
		
		<p>Date de naissance: <input type="date" name="ddn" min="0001-01-01" max="9999-99-99" required></p>
		
		<p>Courriel: <input type="email" name="mail" required></p>
		
		<p>Jeux pr�f�r�s:</p>
		<% for(int i=0;i<Jeux.values().length;i++){
			String jeu=Jeux.values()[i].toString();
			out.println("<p>    <input type='checkbox' name='"+jeu+"'> "+jeu+"</p>");
		}%>

		
		<input type="submit" value="s'inscrire" name="inscription">			
	</form>
</body>
</html>