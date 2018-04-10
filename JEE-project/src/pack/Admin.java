package pack;

import java.util.Date;
import java.util.List;

public class Admin extends Utilisateur {

	public Admin(String pseudo, String motDePasse, List<Jeu> jeuxPreferes, Date dateDeNaissance, String courriel) {
		super(pseudo, motDePasse, jeuxPreferes, dateDeNaissance, courriel);
		}

	public Admin(int id, String pseudo, String motDePasse, List<Jeu> jeuxPreferes, Date dateDeNaissance,
			String courriel, boolean interdit, Date dateInscription, int nbParties) {
		super(id, pseudo, motDePasse, jeuxPreferes, dateDeNaissance, courriel, interdit, dateInscription, nbParties);
	}

	public boolean estAdmin() {
		return true;
	}
	
}
