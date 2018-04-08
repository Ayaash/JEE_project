<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Connexion</title>
</head>
<body>
	<form method="post" action=${pageContext.request.contextPath}/connexion>
		<p>Pseudo: <input type="text" name="pseudo"></p>
		<p>Mot de passe: <input type="password" name=mdp>
		
		<err> <% if(request.getAttribute("message")!=null){
			out.println("Le mot de passe est incorrect");
		} %> </err>
		
		</p>
		
		<input type="submit" value="se connecter" name="connexion">			
	</form>
	
</body>
</html>