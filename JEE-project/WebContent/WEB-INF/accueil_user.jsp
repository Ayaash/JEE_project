<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<%@ page import="pack.Utilisateur" %>
	<%@ page import="pack.Jeu" %>
	<%@ page import="pack.Partie" %>
	<%@ page import="pack.FonctionsUtile" %>
	


	<style><%@include file="/WEB-INF/css/style.css"%></style>
	<title>Accueil</title>
</head>
<body>
	
	<h1>Bienvenue, <%= ((Utilisateur)request.getSession().getAttribute("utilisateur")).getPseudo() %></h1>


	<form method="post" action=${pageContext.request.contextPath}/accueil>
		<input type="submit" value="se deconnecter" name="deconnexion">
		<input type="submit" value="modifier son compte" name="modif">
		
		<% 
		Partie isInPart=null;
			
		//On verifie que l'utilisateur n'a pas une autre partie en cours dans une autre session{
		Iterator<Partie> iterP0=FonctionsUtile.partiesEnCours.iterator();
		while(iterP0.hasNext()){
			Partie part=iterP0.next();
			//Si le joueur est dans une autre partie
			if(part.getJoueur()==((Utilisateur)request.getSession().getAttribute("utilisateur"))){
				isInPart=part;
			}
		}
			
		if(isInPart==null){
			request.getSession().setAttribute("partieencours",null);

			out.println("<p>Jeux Disponibles:</p>");
			//On parcour l'ensemble des jeux
			List<Jeu> list=(List<Jeu>) request.getSession().getAttribute("listejeux");
			Iterator<Jeu> iterJ=list.iterator();
			while(iterJ.hasNext()){
				Jeu jeu=iterJ.next();
				//On cherche le nombre de parties en cours pour ce jeu
				int nbPart=0;
				Iterator<Partie> iterP=FonctionsUtile.partiesEnCours.iterator();
				while(iterP.hasNext()){
					Partie part=iterP.next();
					if(part.getJeu()==jeu){
						nbPart+=1;
					}
				}
				out.println("<p>  "+jeu.getNom()+" ("+nbPart+" parties en cours) <input type='submit' name='"+jeu.getNom()+"' value='jouer!' > </p>");
			}
		}else{
			request.getSession().setAttribute("partieencours",isInPart);
			out.println("<p>Vous devez terminer votre partie actuelle avant de changer de jeu</p>");
		}
		
		%>
		
	</form>		



</body>
</html>