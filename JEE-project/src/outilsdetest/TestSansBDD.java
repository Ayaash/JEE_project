package outilsdetest;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import pack.Admin;
import pack.Jeux;
import pack.Utilisateur;

public class TestSansBDD {
	public static List<Utilisateur> users=new LinkedList<Utilisateur>();
	public static List<Jeux> jeuxDispo=new LinkedList<Jeux>();

	public static boolean initialized=false;
	
	public static void init() {
		if(initialized==false) {
			
			jeuxDispo.add(Jeux.SeaWar);
			jeuxDispo.add(Jeux.FightShip);

			Date d= new Date(12222);
			users.add(new Admin("admin", "admin", new LinkedList<Jeux>(), d, "admin@test.com"));
			users.add(new Utilisateur("user", "user", new LinkedList<Jeux>(), d, "user@test.com"));
		initialized=true;
		}
	}
}
