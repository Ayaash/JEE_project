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


INSERT INTO utilisateur
	VALUES(
	DEFAULT,
	"chef",
	"boss",
	"1990-01-01",
	"2018-04-01",
	0,
	"boss@chef.com",
	false,
	true
);

INSERT INTO jeu
	VALUES(
	DEFAULT,
	"Le jeu",
	false
);

INSERT INTO jeu
	VALUES(
	DEFAULT,
	"SeaWar",
	true
);

INSERT INTO jeu
	VALUES(
	DEFAULT,
	"TES II: Daggerfall",
	true
);

INSERT INTO jeu
	VALUES(
	DEFAULT,
	"Stellaris",
	true
);

INSERT INTO jeu
	VALUES(
	DEFAULT,
	"Dames",
	true
);

INSERT INTO jeu
	VALUES(
	DEFAULT,
	"Paf le Chien",
	true
);

INSERT INTO utilisateur
	VALUES(
	DEFAULT,
	"admin",
	"admin",
	"1970-01-01",
	"1970-01-02",
	0,
	"trash@ayaash.me",
	true,
	false
);

