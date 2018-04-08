package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
	
	public int authentificationUtilisateur(String pseudo, String motDePasse) {
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
				utilisateur = new Utilisateur();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fermerConnections();
		return id;
	}
	
	public int insererUtilisateur(Utilisateur utilisateur) {
		getConnection();
		int id = -1;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(utilisateur.getDateDeNaissance());
		
		String requete = "INSERT INTO utilisateur VALUE (" + utilisateur.getPseudo() + ", " + utilisateur.getMotDePasse() + ", " + date + ", " + utilisateur.getCourriel() + ", false);";
		this.executerRequete(requete);
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
		this.executerRequete(requete);
		fermerConnections();
	}

}
