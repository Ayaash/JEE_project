package pack;
import java.util.Date;
import java.util.List;

public class Utilisateur {
	
	private String pseudo;
	private String motDePasse;
	private List<Jeux> jeuxPreferes;
	private Date dateDeNaissance;
	private String courriel;
	private int nbParties;
		
	public Utilisateur(String pseudo, String motDePasse, List<Jeux> jeuxPreferes, Date dateDeNaissance, String courriel) {
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.jeuxPreferes = jeuxPreferes;
		this.dateDeNaissance = dateDeNaissance;
		this.courriel = courriel;
	}
	
	public boolean estAdmin() {
		return false;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public String getCourriel() {
		return courriel;
	}
	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}
	public List<Jeux> getJeuxPreferes() {
		return jeuxPreferes;
	}
}