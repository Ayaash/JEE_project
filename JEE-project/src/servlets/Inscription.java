package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pack.Jeu;
import pack.LienMySQL;
import pack.Utilisateur;

public class Inscription extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LienMySQL lien = LienMySQL.getInstance();
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		LienMySQL BDD=LienMySQL.getInstance();
		List<Jeu> jeux=BDD.findJeux();
		request.setAttribute("jeux", jeux);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");
		
		
		if(request.getParameter("inscription") != null) {
			LienMySQL BDD=LienMySQL.getInstance();
			
			boolean echec=false;//retour a la page d'inscription si true
			String pseudo=request.getParameter("pseudo");
			
			//verification que le pseudo n'est pas deja dans la BDD

			if(BDD.pseudopris(pseudo)) {
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
			List<Jeu> jeux=BDD.findJeux();

	        
	        if(echec) {
		        request.getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward(request, response);
	        }else{
	        	//creation de l'utilisateur
				Utilisateur u = new Utilisateur(pseudo, motDePasse, jeux, dateDeNaissance, courriel);

				int id=BDD.insererUtilisateur(u);
				u.setId(id);//On doit connaitre l'id de l'utilisateur (au cas ou)
				
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
