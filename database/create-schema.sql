CREATE DATABASE IF NOT EXISTS betweenus;
USE betweenus;

CREATE TABLE Usuari (
	ID_User INT,
	Name VARCHAR(255),
	Email VARCHAR(255),
	Password VARCHAR(255),
	
	PRIMARY KEY (ID_User)
);

CREATE TABLE GameConfiguration(
    name VARCHAR(255),
    NumberCrewmates INTEGER,
    NumberImpostors INTEGER,
    PlayerColour VARCHAR(255),
    selectedMap VARCHAR(255),
    ID_Creador INTEGER,

    PRIMARY KEY(name,ID_Creador),
    FOREIGN KEY (ID_Creador) REFERENCES Usuari(ID_User)


);

CREATE TABLE Partida (
	ID_Partida INT,
	ID_Creador INT,
    configurationName VARCHAR(255),
	
	PRIMARY KEY (ID_Partida),
	FOREIGN KEY (ID_Creador) REFERENCES Usuari(ID_User),
    FOREIGN KEY (configurationName) REFERENCES GameConfiguration(name)
);

CREATE TABLE Historial (
	ID_Partida INT,
	ID_Jugador INT,
	win SMALLINT,
	dataFinalitzacio DATETIME,
	winPercentage FLOAT,

	PRIMARY KEY (ID_Partida, ID_Jugador),
	FOREIGN KEY (ID_Jugador) REFERENCES Usuari(ID_User),
	FOREIGN KEY (ID_Partida) REFERENCES Partida(ID_Partida)
);

CREATE TABLE Player (
	ID_Player INT AUTO_INCREMENT,
	Colour VARCHAR(255),
	Rol VARCHAR(255), 
	dead SMALLINT,
	Impostor SMALLINT,
	Xposition FLOAT,
	Yposition FLOAT,
	playable SMALLINT,

	PRIMARY KEY (ID_Player)
);

CREATE TABLE Historial_Movements (
	ID_Player INT NOT NULL,
	Sala VARCHAR(255),
	instant INTEGER,
	rol VARCHAR(255),
	PRIMARY KEY (ID_Player, Sala, instant),
	FOREIGN KEY (ID_Player) REFERENCES Player(ID_Player)
);

CREATE TABLE Player_In_Game (
	ID_Player INT NOT NULL,
	ID_Game INT NOT NULL,
	Colour VARCHAR(255),
	PRIMARY KEY (ID_Player, ID_Game),
	FOREIGN KEY (ID_Player) REFERENCES Player(ID_Player),
	FOREIGN KEY (ID_Game) REFERENCES Partida(ID_Partida)
);
