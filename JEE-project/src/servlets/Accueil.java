package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import outilsdetest.TestSansBDD;
import pack.Utilisateur;


public class Accueil extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
       
		TestSansBDD.init();//TODO Pour tests uniquement, a supprimer
		
		HttpSession session = request.getSession();
        if (session == null) {
            //Aucune session n'existe
    		this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_invite.jsp" ).forward( request, response );
        } else {
        	if(session.getAttribute("utilisateur")==null) {
        		//La session ne correspond pas a un utilisateur
        		this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_invite.jsp" ).forward( request, response );
        	}else {
	            // Un session existe, on verifie si c'est en administrateur
	        	if(((Utilisateur)session.getAttribute("utilisateur")).estAdmin()) {//Si est administrateur
	        		this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_admin.jsp" ).forward( request, response );
	        	}else {
	        		this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil_user.jsp" ).forward( request, response );
	        	}
        	}
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
	    }


	}

}
