<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Inscription</title>
</head>
<body>
	<form method="post" action=${pageContext.request.contextPath}/inscription>
		<p>Pseudo: <input type="text" name="pseudo" required></p>
		<p>Mot de passe: <input type="password" name="mdp" required></p>
		<p>Date de naissance: <input type="date" name="ddn" min="0001-01-01" max="9999-99-99" required></p>
		<p>Courriel: <input type="email" name="mail" required></p>
		<p>Jeux préférés:</p>
		<p>    <input type="checkbox" name="seawar"> SeaWar:</p>
		<p>    <input type="checkbox" name="fightShip"> FightShip:</p>
			
		
		
		<input type="submit" value="s'inscrire" name="inscription">			
	</form>
</body>
</html>