package pack;
import java.util.Date;
import java.util.List;

public class Utilisateur {
	
	private String pseudo;
	private String motDePasse;
	private List<Jeux> jeuxPreferes;
	private Date dateDeNaissance;
	private String couriel;
	
	public Utilisateur(String pseudo, String motDePasse, List<Jeux> jeuxPreferes, Date dateDeNaissance, String couriel) {
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.jeuxPreferes = jeuxPreferes;
		this.dateDeNaissance = dateDeNaissance;
		this.couriel = couriel;
	}
	
	public boolean estAdmin() {
		return false;
	}

}
