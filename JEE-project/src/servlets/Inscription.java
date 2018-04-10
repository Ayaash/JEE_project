package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import outilsdetest.TestSansBDD;
import pack.Jeux;
import pack.LienMySQL;
import pack.Utilisateur;

public class Inscription extends HttpServlet {
	
	LienMySQL lien = LienMySQL.getInstance();
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");
		TestSansBDD.init();//TODO pour tests sans BDD seulement
		LienMySQL BDD=LienMySQL.getInstance();
		
		if(request.getParameter("inscription") != null) {
			
			boolean echec=false;//retour a la page d'inscription si true
			String pseudo=request.getParameter("pseudo");
			
			//verification que le pseudo n'est pas deja dans la BDD
			Utilisateur foundUser=null;
			//TODO zone a remplacer par la recherche dans BDD
			Iterator<Utilisateur> iter=TestSansBDD.users.iterator();
			while(iter.hasNext() && foundUser==null) {
				Utilisateur u=iter.next();
				if(pseudo.equals(u.getPseudo())) {
					foundUser=u;
				}
			}
			//TODO }fin de la zone a modifier
			
			if(foundUser!=null) {
				request.setAttribute("msgpseudo", "Ce pseudo est deja utilis�");
				echec=true;
			}
			
			
			String motDePasse=request.getParameter("mdp");
			String motDePasse2=request.getParameter("mdp2");
			if(!motDePasse.equals(motDePasse2)) {
				request.setAttribute("msgmdp", "Les mots de passes doivent etres identiques");
				echec=true;
			}
			
			DateFormat format=new SimpleDateFormat("y-M-d");
			Date dateDeNaissance=new Date();
			try {
				dateDeNaissance = format.parse(request.getParameter("ddn"));

			} catch (ParseException e) {
				//Le format de la date est verifi� en amont, au cas ou, on redirige a la page d'inscription
				this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
			}

			
			String courriel=request.getParameter("mail");
			LinkedList<Jeux> jeux=new LinkedList<Jeux>();
			//Creation de la liste de jeux
			
			for(int i=0;i<Jeux.values().length;i++){
				String jeu=Jeux.values()[i].toString();
				if(request.getParameter(jeu) != null) {
					jeux.add(Jeux.valueOf(jeu));
				}
			}

	        
	        if(echec) {
		        request.getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward(request, response);
	        }else{
	        	//creation de l'utilisateur
				Utilisateur u = new Utilisateur(pseudo, motDePasse, jeux, dateDeNaissance, courriel);

				BDD.insererUtilisateur(u);//TODO peut etre modifier l'insertion
				
				//On ouvre la session de l'utilisateur
		        HttpSession session = request.getSession();
		        session.setAttribute("utilisateur", u);
	        	response.sendRedirect(this.getServletContext().getContextPath());//On renvoie a l'accueil
	        }

		}else if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
