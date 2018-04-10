<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@ page import="pack.Jeux" %>
	<%@ page import="java.util.List" %>


	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Modifier la liste de jeux</title>
</head>
<body>

<h1>Modification de la liste des jeux</h1>

	<form method="post" action=${pageContext.request.contextPath}/modifjeux>

	<p>Jeux Possibles:</p>
	<% 
	List<Jeux> listJeux=(List<Jeux>) request.getAttribute("jeuxdispo");
	for(int i=0;i<Jeux.values().length;i++){
		String jeu=Jeux.values()[i].toString();
		if(listJeux.contains(Jeux.values()[i])){
			out.println("<p>    <input type='checkbox' name='"+jeu+"' checked=checked > "+jeu+"</p>");

		}else{
			out.println("<p>    <input type='checkbox' name='"+jeu+"'> "+jeu+"</p>");
		}
	}
	%>

	
	<input type="submit" value="Confirmer" name="confirmer">	
	<input type="submit" value="Accueil" name="accueil">
</body>
</html>