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
import pack.Partie;

public class AdminPartiesEnCours extends HttpServlet {
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
        	this.getServletContext().getRequestDispatcher( "/WEB-INF/adminPartiesEnCours.jsp" ).forward( request, response );
       }
		
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		List<Partie> parties=FonctionsUtile.partiesEnCours; 
		
		//On parcours la liste des parties en cours pour voir si le bouton de fin de partie a ete clique, si c'est le cas, on detruit la partie
		Iterator<Partie> iter=FonctionsUtile.partiesEnCours.iterator();
		while(iter.hasNext()){
			Partie part=iter.next();
			if(request.getParameter(Integer.toString(part.hashCode())) != null) {
				part.finPartie();
				parties.remove(part);
				response.sendRedirect(this.getServletContext().getContextPath()+"/partiesencours");

			}
		}
			
	
		//Bouton accueil
		if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
