package pack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import outilsdetest.TestSansBDD;

public class FonctionsUtile {
	
	
	
	/**retourne NO si pas connecté, USER si utilisateur et ADMIN si administrateur*/
	public static ConnectionType typeDeConnection( HttpServletRequest request) {		
		HttpSession session = request.getSession();
        if (session == null) {
            //Aucune session n'existe
        	return ConnectionType.NO;
        } else {
        	if(session.getAttribute("utilisateur")==null) {
        		//La session ne correspond pas a un utilisateur
            	return ConnectionType.NO;
        	}else {
	            // Un session existe, on verifie si c'est en administrateur
	        	if(((Utilisateur)session.getAttribute("utilisateur")).estAdmin()) {//Si est administrateur
	            	return ConnectionType.ADMIN;
	        	}else {
	            	return ConnectionType.USER;
	        	}
        	}
        }
	}
}
