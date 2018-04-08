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

public class Modification extends HttpServlet {
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        ConnectionType ct=FonctionsUtile.typeDeConnection(request);
		if(ct==ConnectionType.NO) {
			//Aucune session correspondant a un utilisateur n'existe
			response.sendRedirect(this.getServletContext().getContextPath()+"/Accueil");
        }else {
			//La session correspondant a un utilisateur ou  un admin
        	this.getServletContext().getRequestDispatcher( "/WEB-INF/modification.jsp" ).forward( request, response );
       }
		
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		//response.sendRedirect("/inscription");
		TestSansBDD.init();//TODO pour tests sans BDD seulement
		
		
		
		
		if(request.getParameter("modification") != null) {
			
			HttpSession session = request.getSession();//On recupere la session courante pour verifier
			
			
			
			
			Utilisateur currentUser=((Utilisateur)session.getAttribute("utilisateur"));
			
			boolean echec=false;//retour a la page d'inscription si true
			String pseudo=request.getParameter("pseudo");
			//verification que le pseudo n'est pas deja dans la BDD
			Utilisateur foundUser=null;
			//TODO zone a remplacer par la recherche dans BDD
			Iterator<Utilisateur> iter=TestSansBDD.users.iterator();
			while(iter.hasNext() && foundUser==null) {
				Utilisateur u=iter.next();
				System.out.println(u.getDateDeNaissance());
				if(pseudo.equals(u.getPseudo()) 
					&& !(pseudo.equals(currentUser.getPseudo()))) {//On autorise la conservation de son pseudo
					
					foundUser=u;
				}
			}
			//TODO }fin de la zone a modifier
			
			if(foundUser!=null) {
				request.setAttribute("msgpseudo", "Ce pseudo est deja utilisé");
				echec=true;
			}
			
			
			String motDePasse=request.getParameter("mdp");
			String motDePasse2=request.getParameter("mdp2");
			if(!motDePasse.equals(motDePasse2)) {
				request.setAttribute("msgmdp", "Les mots de passes doivent etres identiques");
				echec=true;
			}
			
			DateFormat format=new SimpleDateFormat("y-M-d");
			Date dateDeNaissance=new Date();
			try {
				dateDeNaissance = format.parse(request.getParameter("ddn"));
			} catch (ParseException e) {
				//Le format de la date est verifié en amont, au cas ou, on redirige a la page d'inscription
				this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );
			}
			
			String courriel=request.getParameter("mail");
			LinkedList<Jeux> jeux=new LinkedList<Jeux>();
			//Creation de la liste de jeux
			
			for(int i=0;i<Jeux.values().length;i++){
				String jeu=Jeux.values()[i].toString();
				if(request.getParameter(jeu) != null) {
					jeux.add(Jeux.valueOf(jeu));
				}
			}

	        
	        if(echec) {
		        request.getRequestDispatcher( "/WEB-INF/modification.jsp" ).forward(request, response);
	        }else{
	        	//creation de l'utilisateur

				//TODO modification de l'utilisateur dans la base de donnée{
	        	int i=TestSansBDD.users.indexOf(currentUser);
	        	TestSansBDD.users.get(i).modifiy(pseudo, motDePasse, jeux, dateDeNaissance, courriel);;
	        	//TODO }

	        	response.sendRedirect(this.getServletContext().getContextPath());//On renvoie a l'accueil
	        }

		}
	}
}
