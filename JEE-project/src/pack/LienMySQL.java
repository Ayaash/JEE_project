package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;




public class LienMySQL {
	/**
	 * Necessite un serveur MySQL actif avec le mot de passe root = ''. Et une database nommée projet_jee.
	 */

	private String serveur = "jdbc:mysql://localhost:3306/projet_jee";
	
	Connection connection = null;
	java.sql.Statement statement = null;
	ResultSet resultSet = null;
	int resultInt = 0;
	
	//Singleton pour n'avoir qu'une seule source d'interaction avec la BDD
	private static LienMySQL instance;
	private LienMySQL() {
		
	}
	public static LienMySQL getInstance() {
		if(instance == null) {
			instance = new LienMySQL();
		}
		return instance;
	}
	
	public void getConnection() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} 

		try {
			connection =  DriverManager.getConnection(serveur, "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fermerConnections() {
		try {
			if(connection != null) connection.close();
			if(statement != null) statement.close();
			if(resultSet != null) resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	private ResultSet executerRequete(String requete) {;
		ResultSet rs = null;
		java.sql.Statement stat = null;
		try {
			stat = connection.createStatement();
			rs = stat.executeQuery(requete);
			return rs;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wow");
			e.printStackTrace();
		}
		return null;
	}
	private int executerUpdate(String requete) {
		java.sql.Statement stat = null;
		int retour = 0;
		try {
			stat = connection.createStatement();
			retour = stat.executeUpdate(requete);
			stat.close();
			return retour;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public Utilisateur authentificationUtilisateur(String pseudo, String motDePasse) {
		System.out.println(motDePasse);
		getConnection();
		int id;
		Date daten;
		String email;
		List<Jeu> jeux;
		Date datei;
		Boolean interdit;
		Boolean admin;
		int nbParties;
		Utilisateur utilisateur = null;
		ResultSet rs = executerRequete("SELECT * FROM utilisateur WHERE pseudo=\"" + pseudo + "\" AND mdp=\"" + motDePasse + "\";");
		try {
			if(rs.next()) {
				System.out.println(rs.getString("mdp"));
				id = rs.getInt(1);
				jeux = new ArrayList<Jeu>();
				Jeu jeu;
				daten = rs.getDate("date_naissance");
				email = rs.getString("email");
				interdit=rs.getBoolean("interdit");
				admin = rs.getBoolean("isAdmin");
				nbParties=rs.getInt("nbparties");
				datei=rs.getDate("date_inscription");
				executerRequete("SELECT jeu.id AS id, jeu.nom AS nom, jeu.autorise AS autorise FROM jeu, jeuxFavoris WHERE jeuxFavoris.joueur=" + id + ";");
				while(rs.next()) {
					jeu = new Jeu(rs.getInt("id"), rs.getString("nom"), rs.getBoolean("autorise"));
					jeux.add(jeu);
				}
				System.out.println("wow");
				if(admin) {
					utilisateur = new Admin(id, pseudo, motDePasse, jeux, daten, email,interdit,datei,nbParties);
				}else {
					utilisateur = new Utilisateur(id, pseudo, motDePasse, jeux, daten, email,interdit,datei,nbParties);
				}
				
				
				
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		;
		fermerConnections();
		return utilisateur;
	}
	
	public List<Utilisateur> listusers(){
		getConnection();
		
		List<Utilisateur> listu = new ArrayList<Utilisateur>();
		
		int id;
		Date daten;
		String email;
		List<Jeu> jeux;
		Date datei;
		Boolean interdit;
		Boolean admin;
		int nbParties;
		Utilisateur utilisateur = null;
		String pseudo;
		String motDePasse;
		ResultSet rs1 = executerRequete("SELECT * FROM utilisateur;");
		ResultSet rs2=null;
		try {
			if(rs1.next()) {
				id = rs1.getInt(1);
				pseudo=rs1.getString("pseudo");
				motDePasse=rs1.getString("mdp");
				jeux = new ArrayList<Jeu>();
				Jeu jeu;
				daten = rs1.getDate("date_naissance");
				email = rs1.getString("email");
				interdit=rs1.getBoolean("interdit");
				admin = rs1.getBoolean("isAdmin");
				nbParties=rs1.getInt("nbparties");
				datei=rs1.getDate("date_inscription");
				rs2 = executerRequete("SELECT jeu.id AS id, jeu.nom AS nom, jeu.autorise AS autorise FROM jeu, jeuxFavoris WHERE jeuxFavoris.joueur=" + id + ";");
				while(rs2.next()) {
					jeu = new Jeu(rs2.getInt("id"), rs2.getString("nom"), rs2.getBoolean("autorise"));
					jeux.add(jeu);
				}
				if(admin) {
					utilisateur = new Admin(id, pseudo, motDePasse, jeux, daten, email,interdit,datei,nbParties);
				}else {
					utilisateur = new Utilisateur(id, pseudo, motDePasse, jeux, daten, email,interdit,datei,nbParties);
				}
				
				listu.add(utilisateur);
			}
			rs1.close();
			rs2.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return listu;
	}
	
	public int insererUtilisateur(Utilisateur utilisateur) {
		System.out.println("WOW");
		getConnection();
		int id = -1;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(utilisateur.getDateDeNaissance());
		String dateIns = sdf.format(utilisateur.getDateInscription());
		
		String requete = "INSERT INTO utilisateur VALUE (DEFAULT, \"" + utilisateur.getPseudo() + "\", \"" + utilisateur.getMotDePasse() + "\",\"" + date + "\", \"" + dateIns + "\", "+ 0 + ", \"" + utilisateur.getCourriel() + "\", false, false);";
		this.executerUpdate(requete);
		requete = "SELECT id FROM utilisateur WHERE  pseudo=\"" + utilisateur.getPseudo() + "\" AND email= \""  + utilisateur.getCourriel() + "\";" ;
		ResultSet rs = this.executerRequete(requete);
		try {
			rs.next();
			id = rs.getInt("id");
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Jeu jeu;
		for(int i = 0; i < utilisateur.getJeuxPreferes().size(); i++) {
			jeu = utilisateur.getJeuxPreferes().get(i);
			this.executerUpdate("INSERT INTO jeuxFavoris VALUE (" + id + ", " + jeu.getId() + ");");
		}

		fermerConnections();
		
		
		return id;		
	}
	
	
	
	public void modifierUtilisateur(Utilisateur utilisateur) {
		getConnection();
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(utilisateur.getDateDeNaissance());
		
		String requete = "UPDATE utilisateur SET pseudo=\"" + utilisateur.getPseudo() + "\", mdp=\"" + utilisateur.getMotDePasse() + "\", date_naissance=\"" + date + "\", email=\"" + utilisateur.getCourriel() +"\" WHERE id=" + utilisateur.getId() + ";";
		this.executerUpdate(requete);
		fermerConnections();
	}
	
	
	public void modiflisteutilisateur(List<Utilisateur> l) {
		Utilisateur u;
		int taille=l.size();
		
		for(int i=0; i<taille;i++) {
			u=l.get(i);
			modifierUtilisateur(u);
		}
	}
	
	//retourne true si le pseudonyme passéen parametre est pris
	public boolean pseudopris(String pseud) {
		boolean result=false;
		
		getConnection();
		
		String requete="Select * FROM utilisateur where pseudo=\""+pseud+"\";";
		ResultSet rs = this.executerRequete(requete);
		
		try {
			if(rs.next()) {
				result=true;
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fermerConnections();
		return result;
	}
	
	//fonctions liees au jeux
	
	public void insererJeu(int id, String nom) throws SQLException {
		getConnection();
		java.sql.PreparedStatement stat;
		int rowsUpdated;
		
		stat = connection.prepareStatement("insert into Jeu (id, nom) Values (?,?)");
     	stat.setInt(1, id);
     	stat.setString(2, nom);
     	rowsUpdated = stat.executeUpdate();
     	
     	fermerConnections();
	}
	
	public List<Jeu> findJeux() {
		getConnection();
		java.sql.Statement stat = null;
		ResultSet rs = null;
		List<Jeu> listJeu = new ArrayList<Jeu>();
		
		try {
			stat = connection.createStatement();
			rs = stat.executeQuery("SELECT * FROM jeu;");
			while (rs.next()) {
					int id = rs.getInt("id");
					String nom = rs.getString("nom");
					Boolean autorise = rs.getBoolean("autorise");
					listJeu.add(new Jeu(id, nom, autorise));
			}
			rs.close();
			stat.close();
			
		} catch (Exception e) {
			// sert à afficher les potentielles erreurs
			e.printStackTrace();

		} finally {
			fermerConnections();
		}
		return listJeu;

	}
	
	public List<Jeu> findJeuxautorise() {
		getConnection();
		java.sql.Statement stat = null;
		ResultSet rs = null;
		List<Jeu> listJeu = new ArrayList<Jeu>();
		
		try {
			stat = connection.createStatement();
			rs = stat.executeQuery("SELECT * FROM jeu where autorise=true;");
			while (rs.next()) {
					int id = rs.getInt("id");
					String nom = rs.getString("nom");
					Boolean autorise = rs.getBoolean("autorise");
					listJeu.add(new Jeu(id, nom, autorise));
			}
			rs.close();
			stat.close();
		} catch (Exception e) {
			// sert à afficher les potentielles erreurs
			e.printStackTrace();

		} finally {
			
			fermerConnections();
		}
		return listJeu;

	}
	
	public void majjeu(Jeu j, boolean status) throws SQLException {
		getConnection();
		java.sql.PreparedStatement stat;
		int rowsUpdated;
		int id= j.getId();
		
		stat = connection.prepareStatement("UPDATE Jeu set autorise=? where id=?;");
     	stat.setBoolean(1, status);;
     	stat.setInt(2, id);
     	rowsUpdated = stat.executeUpdate();
     	stat.close();
     	fermerConnections();
	}
	
	public void majjeuliste(List<Jeu> l) throws SQLException {
		Jeu j;
		int taille = l.size();
		
		for(int i=0; i<taille;i++) {
			j=l.get(i);
			majjeu(j,j.isAutorise());
		}
	}
	
	
	
	//fonction des parties
	public List<Partie> findParties() {
		getConnection();
		java.sql.Statement stat = null;
		ResultSet rs = null;
		List<Partie> listpart = new ArrayList<Partie>();
		
		try {
			stat = connection.createStatement();
			rs = stat.executeQuery("SELECT * FROM partie");
			while (rs.next()) {
					int idu = rs.getInt("jouer");
					int idj = rs.getInt("jeu");					
					Date debut=rs.getDate("date_debut");
					Date fin=rs.getDate("date_fin");
					rs.close();
					rs = executerRequete("SELECT * From utilisateur WHERE id=" + idu+";");
					Utilisateur u = authentificationUtilisateur(rs.getString("pseudo"), rs.getString("mdp"));
					rs.close();
					rs = executerRequete("SELECT * From jeu WHERE id=" + idj+";");
					
					int id = rs.getInt("id");
					String nom = rs.getString("nom");
					Boolean autorise = rs.getBoolean("autorise");
					Jeu j=(new Jeu(id, nom, autorise));
					
					Partie p = new Partie(u, j, debut,fin);
					
					listpart.add(p);
			}
			
		} catch (Exception e) {
			// sert à afficher les potentielles erreurs
			e.printStackTrace();

		} finally {
			fermerConnections();
		}
		return listpart;

	}
	
	public int insererPartie(Partie p) {
		getConnection();
		int id = -1;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String debut = sdf.format(p.getDebut());
		String fin = sdf.format(p.getFin());
		
		String requete = "INSERT INTO partie VALUE (DEFAULT, \"" + p.getJoueur().getId() + "\", \"" + p.getJeu().getId() + "\",\"" + debut + "\", \"" + fin + "\");";
		this.executerUpdate(requete);
		

		fermerConnections();
		
		
		return id;		
	}

}
