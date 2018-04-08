package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import outilsdetest.TestSansBDD;
import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.Utilisateur;


public class Accueil extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
       
		TestSansBDD.init();//TODO Pour tests uniquement, a supprimer
		
	
        //On change la page d'accueil en fonction du type de connection
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
        if(ct==ConnectionType.NO) {
			//Aucune session correspondant a un utilisateur n'existe
			this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_invite.jsp" ).forward( request, response );
        }else if(ct==ConnectionType.USER) {
			//La session correspondant a un utilisateur
			this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_user.jsp" ).forward( request, response );
       }else if(ct==ConnectionType.ADMIN) {
    	   //La session correspondant a un administrateur
    	   this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_admin.jsp" ).forward( request, response );
      }

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		/**En fonction du bouton clique, on redirige a la page correspondante*/
		if(request.getParameter("connexion") != null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/connexion");
	    }else if(request.getParameter("inscription") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/inscription");
	    }else if(request.getParameter("deconnexion") != null) {
	        HttpSession session = request.getSession();
	        session.invalidate();
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
	    }else if(request.getParameter("modif") != null) {
	    	response.sendRedirect(this.getServletContext().getContextPath()+"/modification");
	    }


	}

}
