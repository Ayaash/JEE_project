<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<%@ page import="pack.Jeux" %>
	
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Inscription</title>
</head>
<body>
	<form method="post" action=${pageContext.request.contextPath}/inscription>
	
		<p>Pseudo: <input type="text" name="pseudo" required>
		
		<err> <% if(request.getAttribute("msgpseudo")!=null){
			out.println("Ce pseudo est deja utilisé par un autre utilisateur");
		} %> </err>
		
		</p>
		
		<p>Mot de passe: <input type="password" name="mdp" required></p>
		
		<p>Confirmation du mot de passe: <input type="password" name="mdp2" required>
		
		<err> <% if(request.getAttribute("msgmdp")!=null){
			out.println("Les mots de passes doivent êtres identiques");
		} %> </err>
		
		</p>
		
		<p>Date de naissance: <input type="date" name="ddn" min="0001-01-01" max="9999-99-99" required></p>
		
		<p>Courriel: <input type="email" name="mail" required></p>
		
		<p>Jeux préférés:</p>
		<% for(int i=0;i<Jeux.values().length;i++){
			String jeu=Jeux.values()[i].toString();
			out.println("<p>    <input type='checkbox' name='"+jeu+"'> "+jeu+"</p>");
		}%>

		
		<input type="submit" value="s'inscrire" name="inscription">			
	</form>
</body>
</html>