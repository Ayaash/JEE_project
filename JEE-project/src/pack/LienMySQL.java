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
	java.sql.Statement stat = null;
	ResultSet rs = null;
	
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
			connection =  DriverManager.getConnection(serveur, "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fermerConnections() {
		try {
			if(connection != null) connection.close();
			if(stat != null) stat.close();
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean bddInitiale() {
		
		return true;
	}
	
	public void main() {
		getConnection();
		try {
			stat = connection.createStatement();
			rs = stat.executeQuery("SELECT * FROM test;");
			System.out.println(rs.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fermerConnections();
		}
		
		
	}

	
}
