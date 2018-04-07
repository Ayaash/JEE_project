package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pack.Jeux;
import pack.Utilisateur;

public class Inscription extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");
		if(request.getParameter("inscription") != null) {
			boolean echec=false;//si true, on recommence l'inscription en indiquant les problemes
			String pseudo=request.getParameter("pseudo");
			String motDePasse=request.getParameter("mdp");
			
			DateFormat format=new SimpleDateFormat("y-M-d");
			Date dateDeNaissance=new Date();
			try {
				dateDeNaissance = format.parse(request.getParameter("ddn"));
			} catch (ParseException e) {
				//Si mauvais format de date, on l'indique

			}
			
			String courriel=request.getParameter("mail");
			LinkedList<Jeux> jeux=new LinkedList<Jeux>();
			//Creation de la liste de jeux
			if(request.getParameter("seawar") != null) {
				jeux.add(Jeux.SeaWar);
			}
			if(request.getParameter("fightship") != null) {
				jeux.add(Jeux.FightShip);
			}

			if(echec==true) {//On indique les causes d'echec de l'inscription
				this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
			}else {//ajout de l'utilisateur et retour a l'accueil, mais connecte
				Utilisateur u = new Utilisateur(pseudo, motDePasse, jeux, dateDeNaissance, courriel);
				//TODO ajout de l'utilisateur dans la base de donnée
		        HttpSession session = request.getSession();
		        session.setAttribute("utilisateur", u);
		        
		        System.out.println("nom="+u.getPseudo());
		        
				response.sendRedirect(this.getServletContext().getContextPath());

			}
			
		}
	}
}
