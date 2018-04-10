package servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import outilsdetest.TestSansBDD;
import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.Jeu;
import pack.LienMySQL;
import pack.Partie;
import pack.Utilisateur;


public class Accueil extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
       
		LienMySQL BDD=LienMySQL.getInstance();
	
        //On change la page d'accueil en fonction du type de connection
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
        if(ct==ConnectionType.NO) {
			//Aucune session correspondant a un utilisateur n'existe
			this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_invite.jsp" ).forward( request, response );
        }else if(ct==ConnectionType.USER) {
			//La session correspondant a un utilisateur
			HttpSession session = request.getSession();
		    session.setAttribute("listejeux", BDD.findJeuxautorise());
			this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_user.jsp" ).forward( request, response );
       }else if(ct==ConnectionType.ADMIN) {
    	   //La session correspondant a un administrateur
    	   HttpSession session = request.getSession();
		   session.setAttribute("listejeux", BDD.findJeuxautorise());

    	   
    	  this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_admin.jsp" ).forward( request, response );
      }

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

        HttpSession session = request.getSession();

      //Recuperation du jeu selectionné si necessaire
  		if(session.getAttribute("listejeux")!=null) {
  			List<Jeu> list=(List<Jeu>) request.getSession().getAttribute("listejeux");
  			Iterator<Jeu> iter=list.iterator();
  			while(iter.hasNext()){
  				Jeu jeu=iter.next();
  				if(request.getParameter(jeu.getNom())!=null) {
  					//On a selectionne un jeu, on est redirige vers la page de jeu
  			        session.setAttribute("jeu", jeu);
  					response.sendRedirect(this.getServletContext().getContextPath()+"/jeu");
  				}
  			}
  		}
		
		/**En fonction du bouton clique, on redirige a la page correspondante*/
		if(request.getParameter("connexion") != null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/connexion");
	    }else if(request.getParameter("inscription") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/inscription");
	    }else if(request.getParameter("deconnexion") != null) {
	    	//Si une partie etait en cours, on y met fin
	    	if(session.getAttribute("partieencours")!=null) {
	    		Partie part=(Partie) session.getAttribute("partieencours");
		        part.finPartie();
		        FonctionsUtile.partiesEnCours.remove(part);
		        session.setAttribute("partieencours", null);
	    	}
	        session.invalidate();
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
	    }else if(request.getParameter("modif") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/modification");
	    	
	    }else if(request.getParameter("modifjeux") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/modifjeux");
	    }else if(request.getParameter("partcour") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/partiesencours");
	    }else if(request.getParameter("partterm") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/partiestermine");
	    }else if(request.getParameter("joueur") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/joueurs");
	    }
		
		
		



	}

}
