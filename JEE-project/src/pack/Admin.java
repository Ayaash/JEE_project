package pack;

import java.util.Date;
import java.util.List;

public class Admin extends Utilisateur {

	public Admin(String pseudo, String motDePasse, List<Jeux> jeuxPreferes, Date dateDeNaissance, String couriel) {
		super(pseudo, motDePasse, jeuxPreferes, dateDeNaissance, couriel);
	}

	public boolean estAdmin(){
		return true;
	}
}
