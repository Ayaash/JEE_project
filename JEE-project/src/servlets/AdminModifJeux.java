package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pack.ConnectionType;
import pack.FonctionsUtile;
import pack.Jeu;
import pack.LienMySQL;

public class AdminModifJeux extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LienMySQL lien = LienMySQL.getInstance();
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
		if(ct==ConnectionType.NO || ct==ConnectionType.USER) {
			//Aucune session correspondant a un admin n'existe
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
        }else {
			//La session correspond a un admin
        	LienMySQL BDD=LienMySQL.getInstance();
    		List<Jeu> jeux=BDD.findJeux();
        	request.setAttribute("jeuxdispo", jeux);
        	this.getServletContext().getRequestDispatcher( "/WEB-INF/adminModifJeux.jsp" ).forward( request, response );
       }
		
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");

		
		if(request.getParameter("confirmer") != null) {
			LienMySQL BDD=LienMySQL.getInstance();
    		List<Jeu> jeux=BDD.findJeux();
        	request.setAttribute("jeuxdispo", jeux);
	

			for(int i=0;i<jeux.size();i++){
				String jeu=jeux.get(i).toString();
				if(request.getParameter(jeu) != null) {
					jeux.add(jeux.get(i));
				}
			}
			try {
				BDD.majjeuliste(jeux);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	      

	        response.sendRedirect(this.getServletContext().getContextPath());//On renvoie a l'accueil


		}else if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
