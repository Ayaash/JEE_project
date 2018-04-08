<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Connexion</title>
</head>
<body>
	<form method="post" action=${pageContext.request.contextPath}/connexion>
		<p>Pseudo: <input type="text" name="pseudo"></p>
		<p>Mot de passe: <input type="password" name=mdp></p>
		
		<p class="erreur"> <% if(request.getAttribute("message")!=null){
			out.println("Le mot de passe est incorrect");
		} %> </p>
		
		<input type="submit" value="se connecter" name="connexion">			
	</form>
	
</body>
</html>