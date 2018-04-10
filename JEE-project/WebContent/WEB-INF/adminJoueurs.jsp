<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@ page import="pack.Utilisateur" %>
	<%@ page import="pack.Partie" %>
	
	<%@ page import="pack.FonctionsUtile" %>
	
	<%@ page import="java.util.List" %>
	<%@ page import="java.util.Iterator" %>
	
	<style><%@include file="/WEB-INF/css/style.css"%></style>
	
	
	<meta http-equiv="refresh" content="2" />

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Modifier la liste de jeux</title>
</head>
<body>

<h1>Parties en cours</h1>

	<form method="post" action=${pageContext.request.contextPath}/joueurs>

		<table>
			<tr>
				<td>Pseudo</td>
				<td>Date d'inscription</td>
				<td>nombre de parties jouées</td>		
				<td>Interdire/Autoriser</td>
				
			
			</tr>
			<%List<Utilisateur> users=(List<Utilisateur>) request.getAttribute("users"); 
		
			Iterator<Utilisateur> iter=users.iterator();
			while(iter.hasNext()){
				Utilisateur user=iter.next();
				
				out.println("<tr>");
				out.println("<td>"+user.getPseudo()+"</td>");
				out.println("<td>"+user.getDateInscription().toString()+"</td>");
				out.println("<td>"+user.getNbParties()+"</td>");
				boolean isInterdit=user.estInterdit();
				if(isInterdit){
					out.println("<td><input type='submit' value='autoriser' name='"+Integer.toString(user.hashCode())+"'></td>");
				}else{
					out.println("<td><input type='submit' value='interdire' name='"+Integer.toString(user.hashCode())+"'></td>");

				}
				out.println("</tr>");
			}
			%>
		
		</table>	
		<input type="submit" value="Accueil" name="accueil">
	</form>
</body>
</html>