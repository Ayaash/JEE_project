package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LienMySQL {
	/**
	 * TODO: Necessite un serveur MySQL actif avec le mot de passe root = 'root'. Et une database nommée BDD.
	 */

	private String serveur = "jdbc:mysql://localhost:3306/BDD";
	
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
	public boolean bddInitiale() {
		return true;
	}
	
	public String ExecuterRequete(String requete) {
		getConnection();
		String retour = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(requete);
			
			retour = resultSet.toString();
			System.out.println(retour);		// retour est un objet, le toString est degueulasse. Mais au moins on a une preuve que quelque chose est revenu.


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fermerConnections();
		}
		
		return retour;
	}
	
	


}
