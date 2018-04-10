CREATE database projet_jee;
GRANT all privileges on projet_jee to 'root'@'localhost';
flush privileges;

USE projet_jee;

CREATE TABLE utilisateur (
	id INT NOT NULL AUTO_INCREMENT,
	pseudo VARCHAR(30) NOT NULL,
	mdp VARCHAR(30) NOT NULL,
	date_naissance DATE NOT NULL,
	date_inscription DATE NOT NULL,
	nbparties INT NOT NULL,
	email VARCHAR(50) NOT NULL,
	isAdmin BOOLEAN NOT NULL,
	interdit BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

INSERT INTO utilisateur (
	id,
	pseudo,
	mdp,
	date_naissance,
	email,
	isAdmin)
	VALUES(
	DEFAULT,
	"chef",
	"boss",
	1990-01-01,
	2018-04-01,
	"boss@chef.com",
	false,
	true
);

CREATE TABLE jeu (
	id INT NOT NULL AUTO_INCREMENT,
	nom VARCHAR(100) NOT NULL,
	autorise BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE partie (
	id INT NOT NULL AUTO_INCREMENT,
	joueur INT NOT NULL,
	jeu INT NOT NULL,
	date_debut DATETIME NOT NULL,
	date_fin DATETIME NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (jeu) 
		REFERENCES jeu(id),
	FOREIGN KEY (joueur)
		REFERENCES utilisateur(id)
);

CREATE TABLE jeuxFavoris (
	joueur INT NOT NULL,
	jeu INT NOT NULL,
	PRIMARY KEY(joueur, jeu),
	FOREIGN KEY (joueur)
		REFERENCES utilisateur(id),
	FOREIGN KEY (jeu)
		REFERENCES jeu(id)
);
