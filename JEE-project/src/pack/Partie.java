package pack;

import java.util.Date;
import java.util.List;

import pack.Jeu;

public class Partie {
	
	private pack.Jeu jeu;
	private Utilisateur joueur;
	private boolean enCours;
	private Date debut;
	private Date fin;

	
	public Partie(Utilisateur joueur,Jeu jeu2 ) {
		this.joueur=joueur;
		this.jeu=jeu2;
		enCours=true;
		debut=new Date();
	}
	
	public Partie(Utilisateur joueur,Jeu jeu, Date debut, Date fin) {
		this.joueur=joueur;
		this.jeu=jeu;
		enCours=false;
		this.debut=debut;
		this.fin=fin;
	}
	
	public void finPartie() {
		enCours=false;
		fin=new Date();
		LienMySQL BDD =LienMySQL.getInstance();
		BDD.insererPartie(this);
	}
	
	public boolean enCours() {
		return enCours;
	}
	
	public Jeu getJeu() {
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
