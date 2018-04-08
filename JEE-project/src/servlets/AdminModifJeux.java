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
import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.Jeux;
import pack.Utilisateur;

public class AdminModifJeux extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
		if(ct==ConnectionType.NO || ct==ConnectionType.USER) {
			//Aucune session correspondant a un admin n'existe
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
        }else {
			//La session correspond a un admin
        	request.setAttribute("jeuxdispo", TestSansBDD.jeuxDispo);//TODO a remplacer par la liste venant de la BDD
        	this.getServletContext().getRequestDispatcher( "/WEB-INF/adminModifJeux.jsp" ).forward( request, response );
       }
		
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		
		if(request.getParameter("confirmer") != null) {
			
	
			//Creation de la liste de jeux
			LinkedList<Jeux> jeux=new LinkedList<Jeux>();

			for(int i=0;i<Jeux.values().length;i++){
				String jeu=Jeux.values()[i].toString();
				if(request.getParameter(jeu) != null) {
					jeux.add(Jeux.valueOf(jeu));
				}
			}
			TestSansBDD.jeuxDispo=jeux;//TODO remplacer par BDD

	        
	      

	        response.sendRedirect(this.getServletContext().getContextPath());//On renvoie a l'accueil


		}else if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
