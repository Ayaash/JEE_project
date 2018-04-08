<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
	<%@ page import="pack.Jeux" %>
	<%@ page import="pack.Utilisateur" %>
	<%@ page import="java.util.Date" %>
	<%@ page import="java.text.SimpleDateFormat" %>
	

<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Modification</title>
</head>
<body>
	
	<form method="post" action=${pageContext.request.contextPath}/modification>
		<% Utilisateur user= (Utilisateur)request.getSession().getAttribute("utilisateur");%>
	
		<p>Pseudo: <input type="text" name="pseudo" value=<%= user.getPseudo() %> required></p>
		
<!-- 		message d'erreur -->
		<p class="erreur"> <% if(request.getAttribute("msgpseudo")!=null){
			out.println("Ce pseudo est deja utilisé par un autre utilisateur");
		} %> </p>
		
		<p>Mot de passe: <input type="password" name="mdp" value=<%= user.getMotDePasse() %> required></p>
		
		<p>Confirmation du mot de passe: <input type="password" name="mdp2" value=<%= user.getMotDePasse() %> required></p>
		
<!-- 		message d'erreur -->
		<p class="erreur"> <% if(request.getAttribute("msgmdp")!=null){
			out.println("Les mots de passes doivent êtres identiques");
		} %> </p>
		
		<%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sdate = sdf.format(user.getDateDeNaissance());
		 %>
		<p>Date de naissance: <input type="date" name="ddn" min="0001-01-01" max="9999-99-99" value=<%= sdate %> required></p>
		
		<p>Courriel: <input type="email" name="mail" value=<%= user.getCourriel() %> required></p>
		
		<p>Jeux préférés:</p>
		<% for(int i=0;i<Jeux.values().length;i++){
			String jeu=Jeux.values()[i].toString();
			if(user.getJeuxPreferes().contains(Jeux.values()[i])){
				out.println("<p>    <input type='checkbox' name='"+jeu+"' checked=checked > "+jeu+"</p>");

			}else{
				out.println("<p>    <input type='checkbox' name='"+jeu+"'> "+jeu+"</p>");
			}
		}%>

		
		<input type="submit" value="valider les modification" name="modification">			
	</form>
</body>
</html>