package pack;

import java.util.Date;
import java.util.List;

public class Partie {
	
	private Jeux jeu;
	private Utilisateur joueur;
	private boolean enCours;
	private Date debut;
	private Date fin;

	
	public Partie(Utilisateur joueur,Jeux jeu ) {
		this.joueur=joueur;
		this.jeu=jeu;
		enCours=true;
		debut=new Date();
	}
	
	public void finPartie() {
		enCours=false;
		fin=new Date();
		//TODO ajouter la sauvegarde dans la BDD
	}
	
	public boolean enCours() {
		return enCours;
	}
	
	public Jeux getJeu() {
		return jeu;
	}
	
	public Utilisateur getJoueur() {
		return joueur;
	}
	
	public Date getDebut() {
		return debut;
	}
	public Date getFin() {
		return fin;
	}
}
