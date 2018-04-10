package pack;

public class Jeu {
	
	private int id;
	private String nom;
	private boolean autorise;
	
	

	public Jeu(int id, String nom, boolean autorise) {
		super();
		this.id = id;
		this.nom = nom;
		this.autorise = autorise;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isAutorise() {
		return autorise;
	}

	public void setAutorise(boolean autorise) {
		this.autorise = autorise;
	}
}
