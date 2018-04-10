package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;



public class LienMySQL {
	/**
	 * Necessite un serveur MySQL actif avec le mot de passe root = 'root'. Et une database nommée projet_jee.
	 */

	private String serveur = "jdbc:mysql://localhost:3306/projet_jee";
	
	Connection connection = null;
	java.sql.Statement statement = null;
	ResultSet resultSet = null;
	
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
	
	//tentative avec executeUpdate.
	private void executerUpdate(String requete) {
		String retour = null;
		try {
			statement = connection.createStatement();
			int entier = statement.executeUpdate(requete);
			
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
		Date date;
		String email;
		Utilisateur utilisateur = null;
		executerRequete("SELECT * FROM utilisateur WHERE pseudo=" + pseudo + " AND mdp=" + motDePasse + ";");
		try {
			if(resultSet.next()) {
				id = resultSet.getInt(1);
				date = resultSet.getDate("date_naissance");
				email = resultSet.getString(5);
				utilisateur = new Utilisateur(id, pseudo, motDePasse, null, date, email);
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
		try {
			resultSet.next();
			id = resultSet.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fermerConnections();
		return id;		
	}
	
	public void modifierUtilisateur(Utilisateur utilisateur) {
		getConnection();
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(utilisateur.getDateDeNaissance());
		
		String requete = "UPDATE utilisateur SET pseudo=" + utilisateur.getPseudo() + ", mdp=" + utilisateur.getMotDePasse() + ", date_naissance=" + date + ", email=" + utilisateur.getCourriel() +" WHERE id=" + utilisateur.getId() + ";";
		this.executerUpdate(requete);
		fermerConnections();
	}
	
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

}
