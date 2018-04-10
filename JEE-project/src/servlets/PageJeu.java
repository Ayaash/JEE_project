package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.Partie;
import pack.Jeu;
import pack.Utilisateur;


public class PageJeu extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
       		
	
        //On change la page d'accueil en fonction du type de connection
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
        if(ct==ConnectionType.NO) {
			//Aucune session correspondant a un utilisateur n'existe
			this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_invite.jsp" ).forward( request, response );
        }else if(ct==ConnectionType.USER) {
			//La session correspondant a un utilisateur

			this.getServletContext().getRequestDispatcher( "/WEB-INF/pageJeu.jsp" ).forward( request, response );
       }else if(ct==ConnectionType.ADMIN) {
    	   //La session correspondant a un administrateur
    	   
    	   this.getServletContext().getRequestDispatcher( "/WEB-INF/pageJeu.jsp" ).forward( request, response );
      }

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		/**En fonction du bouton clique, on redirige a la page correspondante*/
		if(request.getParameter("debut") != null) {
	        HttpSession session = request.getSession();
	        //On ne crée une partie qu'une seule fois
	    	if(session.getAttribute("partieencours")==null) {

		        //On ajoute la parie en cours
		        Partie part=new Partie((Utilisateur) session.getAttribute("utilisateur"),(Jeu) session.getAttribute("jeu"));
		        FonctionsUtile.partiesEnCours.add(part);
				
		        session.setAttribute("partieencours", part);
	    	}
	        this.getServletContext().getRequestDispatcher( "/WEB-INF/pageJeu.jsp" ).forward( request, response );


	    }else if(request.getParameter("fin") != null) {
	    	HttpSession session = request.getSession();
	    	//On ne peut detruire une partie que si elle existe
	    	if(session.getAttribute("partieencours")!=null) {
		    	//On recupere la partie our la detruire
		        Partie part=(Partie) session.getAttribute("partieencours");
		        part.finPartie();
		        FonctionsUtile.partiesEnCours.remove(part);
	
		        session.setAttribute("partieencours", null);
	    	}
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pageJeu.jsp" ).forward( request, response );

	        
	    }else if(request.getParameter("accueil") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");	    
	    }


	}

}
