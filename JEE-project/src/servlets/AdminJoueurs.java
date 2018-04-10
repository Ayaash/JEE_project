package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import outilsdetest.TestSansBDD;
import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.Jeux;
import pack.Partie;
import pack.Utilisateur;

public class AdminJoueurs extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
		if(ct==ConnectionType.NO || ct==ConnectionType.USER) {
			//Aucune session correspondant a un admin n'existe
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
        }else {
			//La session correspond a un admin
    		List<Utilisateur> users= TestSansBDD.users;//TODO a remplacer par la recuperation dans BDD 
    		request.setAttribute("users", users);
        	this.getServletContext().getRequestDispatcher( "/WEB-INF/adminJoueurs.jsp" ).forward( request, response );
       }
		
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		List<Utilisateur> users= TestSansBDD.users;//TODO a remplacer par la recuperation dans BDD 
		
		//On parcours la liste des joueurs pour voir si le bouton d'interdiction a ete clique
		Iterator<Utilisateur> iter=users.iterator();
		while(iter.hasNext()){
			Utilisateur user=iter.next();
			if(request.getParameter(Integer.toString(user.hashCode())) != null) {
				user.changeInterdiction();
				response.sendRedirect(this.getServletContext().getContextPath()+"/joueurs");

			}
		}
		//TODO modifier la BDD
	
		//Bouton accueil
		if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
