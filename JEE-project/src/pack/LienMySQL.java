package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class LienMySQL {
	/**
	 * Necessite un serveur MySQL actif avec le mot de passe root = 'root'. Et une database nommée projet_jee.
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

	
	private void executerRequete(String requete) {
		String retour = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(requete);
			
			retour = resultSet.toString();
			System.out.println(retour);		// retour est un objet, le toString est degueulasse. Mais au moins on a une preuve que quelque chose est revenu.


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void executerUpdate(String requete) {
		String retour = null;
		try {
			statement = connection.createStatement();
			resultInt = statement.executeUpdate(requete);
			
			retour = resultSet.toString();
			System.out.println(retour);		// retour est un objet, le toString est degueulasse. Mais au moins on a une preuve que quelque chose est revenu.


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Utilisateur authentificationUtilisateur(String pseudo, String motDePasse) {
		getConnection();
		int id;
		Date daten;
		String email;
		List<Jeu> jeux;
		Date datei;
		Boolean interdit;
		int nbParties;
		Utilisateur utilisateur = null;
		executerRequete("SELECT * FROM utilisateur WHERE pseudo=" + pseudo + " AND mdp=" + motDePasse + ";");
		try {
			if(resultSet.next()) {
				id = resultSet.getInt(1);
				jeux = new ArrayList<Jeu>();
				Jeu jeu;
				daten = resultSet.getDate("date_naissance");
				email = resultSet.getString("email");
				interdit=resultSet.getBoolean("interdit");
				nbParties=resultSet.getInt("nbparties");
				datei=resultSet.getDate("date-inscription");
				utilisateur = new Utilisateur(id, pseudo, motDePasse, null, daten, email, interdit, datei, nbParties);
				executerRequete("SELECT jeu.id AS id, jeu.nom AS nom, jeu.autorise AS autorise FROM jeu, jeuxFavoris WHERE jeuxFavoris.joueur=" + id);
				while(this.resultSet.next()) {
					jeu = new Jeu(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getBoolean("autorise"));
					jeux.add(jeu);
				}
				utilisateur = new Utilisateur(id, pseudo, motDePasse, jeux, daten, email,interdit,datei,nbParties);
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		fermerConnections();
		return utilisateur;
	}
	
	public int insererUtilisateur(Utilisateur utilisateur) {
		getConnection();
		int id = -1;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(utilisateur.getDateDeNaissance());
		
		String requete = "INSERT INTO utilisateur VALUE (" + utilisateur.getPseudo() + ", " + utilisateur.getMotDePasse() + ", " + date + ", " + utilisateur.getCourriel() + ", false);";
		this.executerUpdate(requete);
		requete = "SELECT id FROM utilisateur WHERE  pseudo=" + utilisateur.getPseudo() + " AND email= "  + utilisateur.getCourriel() + ";" ;
		this.executerRequete(requete);
		try {
			resultSet.next();
			id = resultSet.getInt(1);
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
		
		String requete = "UPDATE utilisateur SET pseudo=" + utilisateur.getPseudo() + ", mdp=" + utilisateur.getMotDePasse() + ", date_naissance=" + date + ", email=" + utilisateur.getCourriel() +" WHERE id=" + utilisateur.getId() + ";";
		this.executerRequete(requete);
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
		
		String requete="Select * FROM utilisateur where pseudo="+pseud+";";
		this.executerRequete(requete);
		
		try {
			if(resultSet.next()) {
				result=true;
			}
			
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
		
		List<Jeu> listJeu = new ArrayList<Jeu>();
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM jeu");
			while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String nom = resultSet.getString("nom");
					Boolean autorise = resultSet.getBoolean("autorise");
					listJeu.add(new Jeu(id, nom, autorise));
			}
			
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
		
		stat = connection.prepareStatement("UPDATE Jeu set status=? where id=?;");
     	stat.setBoolean(1, status);;
     	stat.setInt(2, id);
     	rowsUpdated = stat.executeUpdate();
     	
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
		
		List<Partie> listpart = new ArrayList<Partie>();
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM partie");
			while (resultSet.next()) {
					int idu = resultSet.getInt("jouer");
					int idj = resultSet.getInt("jeu");					
					Date debut=resultSet.getDate("date_debut");
					Date fin=resultSet.getDate("date_fin");
					Partie p = new Partie(null, null, debut,fin);
					
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

}
