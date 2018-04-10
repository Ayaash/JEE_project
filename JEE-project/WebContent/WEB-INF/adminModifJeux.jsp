<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@ page import="pack.Jeu" %>
	<%@ page import="java.util.List" %>


	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Modifier la liste de jeux</title>
</head>
<body>

<h1>Modification de la liste des jeux</h1>

	<form method="post" action=${pageContext.request.contextPath}/modifjeux>

	<p>Jeux Possibles:</p>
	<% 
	List<Jeu> jeux=(List<Jeu>) request.getAttribute("jeux");
		for(int i=0;i<jeux.size();i++){
			Jeu jeu=jeux.get(i);
			String sjeu=jeux.get(i).toString();
			if(jeu.isAutorise()){
				out.println("<p>    <input type='checkbox' name='"+sjeu+"' checked=checked > "+sjeu+"</p>");
	
			}else{
				out.println("<p>    <input type='checkbox' name='"+sjeu+"'> "+sjeu+"</p>");
			}
		}%>

	
	<input type="submit" value="Confirmer" name="confirmer">	
	<input type="submit" value="Accueil" name="accueil">
	
	</form>
</body>
</html>