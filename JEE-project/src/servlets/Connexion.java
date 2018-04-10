package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pack.LienMySQL;
import pack.Utilisateur;

public class Connexion extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");
		if(request.getParameter("connexion") != null) {
			LienMySQL BDD=LienMySQL.getInstance();

			String pseudo=request.getParameter("pseudo");
			String motDePasse=request.getParameter("mdp");
		
			
			//la recherche doit etre fait dans la BDD
			Utilisateur foundUser=null;
			
			foundUser=BDD.authentificationUtilisateur(pseudo, motDePasse);
			
			
			if(foundUser!=null) {//connexion reussie
				//Si l'utilisateur n'est pas interdit, on cree la session, sinon, message d'erreur
				//On cree une session
				if(!foundUser.estInterdit()) {
					HttpSession session = request.getSession();
				    session.setAttribute("utilisateur", foundUser);
					response.sendRedirect(this.getServletContext().getContextPath());//On renvoie a l'accueil
				}else {
					request.setAttribute("message", "Vous n'avez pas l'autorisation de vous connecter");
			        request.getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward(request, response);
				}
			}else {
				request.setAttribute("message", "Le mot de passe est invalide");
		        request.getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward(request, response);
		    }

		}else if(request.getParameter("accueil")!=null) {
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
		}
	}
}
