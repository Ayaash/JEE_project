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
import pack.Utilisateur;

public class Connexion extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");
		TestSansBDD.init();//TODO pour tests sans BDD seulement
		if(request.getParameter("connexion") != null) {

			String pseudo=request.getParameter("pseudo");
			String motDePasse=request.getParameter("mdp");
		
			
			//la recherche doit etre fait dans la BDD{
			Utilisateur foundUser=null;
			//TODO zone a remplacer
			Iterator<Utilisateur> iter=TestSansBDD.users.iterator();
			while(iter.hasNext() && foundUser==null) {
				Utilisateur u=iter.next();
				if(pseudo.equals(u.getPseudo()) && motDePasse.equals(u.getMotDePasse())) {
					foundUser=u;
				}
			}
			
			//TODO }fin de la zone a remplacer par la recherche en BDD
			
			if(foundUser!=null) {//connexion reussie
				//On cree une session
				HttpSession session = request.getSession();
			    session.setAttribute("utilisateur", foundUser);
				response.sendRedirect(this.getServletContext().getContextPath());//On renvoie a l'accueil
			}else {
				request.setAttribute("message", "Le mot de passe est invalide");
		        request.getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward(request, response);
		    }

		}else if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
