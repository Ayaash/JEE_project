package servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.LienMySQL;
import pack.Utilisateur;

public class AdminJoueurs extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
		if(ct==ConnectionType.NO || ct==ConnectionType.USER) {
			//Aucune session correspondant a un admin n'existe
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
        }else {
			//La session correspond a un admin
    		LienMySQL BDD=LienMySQL.getInstance();
    		List<Utilisateur> users= BDD.listusers();
    		request.setAttribute("users", users);
        	this.getServletContext().getRequestDispatcher( "/WEB-INF/adminJoueurs.jsp" ).forward( request, response );
       }
		
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		LienMySQL BDD=LienMySQL.getInstance();
		List<Utilisateur> users= BDD.listusers();
		
		//On parcours la liste des joueurs pour voir si le bouton d'interdiction a ete clique
		Iterator<Utilisateur> iter=users.iterator();
		while(iter.hasNext()){
			Utilisateur user=iter.next();
			if(request.getParameter(Integer.toString(user.hashCode())) != null) {
				user.changeInterdiction();
				response.sendRedirect(this.getServletContext().getContextPath()+"/joueurs");

			}
		}
		BDD.modiflisteutilisateur(users);
	
		//Bouton accueil
		if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
