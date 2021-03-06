<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<%@ page import="pack.Jeu" %>
	<%@ page import="pack.Utilisateur" %>
	<%@ page import="java.util.Date" %>
	<%@ page import="java.text.SimpleDateFormat" %>
	
		<%@ page import="java.util.List" %>
	
	
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Modification</title>
</head>
<body>
	
	<form method="post" action=${pageContext.request.contextPath}/modification>
		<% Utilisateur user= (Utilisateur)request.getSession().getAttribute("utilisateur");%>
	
		<p>Pseudo: <input type="text" name="pseudo" value=<%= user.getPseudo() %> required>
		
<!-- 		message d'erreur -->
		<err> <% if(request.getAttribute("msgpseudo")!=null){
			out.println("Ce pseudo est deja utilis� par un autre utilisateur");
		} %> </err>
		
		</p>
		
		<p>Mot de passe: <input type="password" name="mdp" value=<%= user.getMotDePasse() %> required></p>
		
		<p>Confirmation du mot de passe: <input type="password" name="mdp2" value=<%= user.getMotDePasse() %> required>
		
<!-- 		message d'erreur -->
		<err> <% if(request.getAttribute("msgmdp")!=null){
			out.println("Les mots de passes doivent �tres identiques");
		} %> </err>
		
		</p>
		
		<%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sdate = sdf.format(user.getDateDeNaissance());
		 %>
		<p>Date de naissance: <input type="date" name="ddn" min="0001-01-01" max="9999-99-99" value=<%= sdate %> required></p>
		
		<p>Courriel: <input type="email" name="mail" value=<%= user.getCourriel() %> required></p>
		
		<p>Jeux pr�f�r�s:</p>
		<%
		List<Jeu> jeux=(List<Jeu>) request.getAttribute("jeux");
		for(int i=0;i<jeux.size();i++){
			String jeu=jeux.get(i).toString();
			out.println("<p>    <input type='checkbox' name='"+jeu+"'> "+jeu+"</p>");
		}%>

		
		<input type="submit" value="valider les modification" name="modification">
		<input type="submit" value="Accueil" name="accueil">			
					
	</form>
</body>
</html>