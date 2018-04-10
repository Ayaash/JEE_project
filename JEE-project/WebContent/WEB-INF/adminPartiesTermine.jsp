<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@ page import="pack.Partie" %>
	
	
	<%@ page import="java.util.List" %>
	<%@ page import="java.util.Iterator" %>
	
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	
	
	<meta http-equiv="refresh" content="2" />

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title> la liste des parties terminées</title>
</head>
<body>

<h1>Parties terminées</h1>

	<form method="post" action=${pageContext.request.contextPath}/partiestermine>

		<table>
			<tr>
				<td>Jeu</td>
				<td>Pseudo</td>
				<td>Debut</td>
				<td>Fin</td>
				
			
			</tr>
			<%List<Partie> parties=(List<Partie>) request.getAttribute("parties"); 
		
			Iterator<Partie> iter=parties.iterator();
			while(iter.hasNext()){
				Partie part=iter.next();
				
				out.println("<tr>");
				out.println("<td>"+part.getJeu().toString()+"</td>");
				out.println("<td>"+part.getJoueur().getPseudo()+"</td>");
				out.println("<td>"+part.getDebut().toString()+"</td>");
				out.println("<td>"+part.getFin().toString()+"</td>");

				out.println("</tr>");
			}
			%>
		
		</table>	
		<input type="submit" value="Accueil" name="accueil">
	</form>
</body>
</html>