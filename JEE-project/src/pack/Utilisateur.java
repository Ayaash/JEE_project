package pack;
import java.util.Date;
import java.util.List;

public class Utilisateur {
	
	private int id;
	private String pseudo;
	private String motDePasse;
	private List<Jeu> jeuxPreferes;
	private Date dateDeNaissance;
	private String courriel;
	private Date dateInscription;
	private int nbParties;
	private boolean interdit;	
	
	
	public Utilisateur(String pseudo, String motDePasse, List<Jeu> jeuxPreferes, Date dateDeNaissance, String courriel) {
		this.id = -1;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.jeuxPreferes = jeuxPreferes;
		this.dateDeNaissance = dateDeNaissance;
		this.courriel = courriel;
		this.interdit=false;
		this.dateInscription=new Date();
	}
	
	public Utilisateur(int id, String pseudo, String motDePasse, List<Jeu> jeuxPreferes, Date dateDeNaissance, String courriel) {
		this.id = id;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.jeuxPreferes = jeuxPreferes;
		this.dateDeNaissance = dateDeNaissance;
		this.courriel = courriel;
		this.interdit=false;
		this.dateInscription=new Date();
		this.nbParties=0;
	}
	
	public Utilisateur(int id, String pseudo, String motDePasse, List<Jeu> jeuxPreferes, Date dateDeNaissance, String courriel,boolean interdit, Date dateInscription, int nbParties) {
		this.id = id;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.jeuxPreferes = jeuxPreferes;
		this.dateDeNaissance = dateDeNaissance;
		this.courriel = courriel;
		this.interdit=interdit;
		this.dateInscription=dateInscription;
		this.nbParties=nbParties;


	}
	
	public void modifiy(String pseudo, String motDePasse, List<Jeu> jeuxPreferes, Date dateDeNaissance, String courriel) {
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
	public List<Jeu> getJeuxPreferes() {
		return jeuxPreferes;
	}
	
	public Date getDateInscription() {
		return dateInscription;
	}

	public int getNbParties() {
		return nbParties;
	}
	
	public void jouerPartie() {
		nbParties+=1;
	}
	
	public boolean estInterdit() {
		return interdit;
	}
	
	public void changeInterdiction() {
		interdit=!interdit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}