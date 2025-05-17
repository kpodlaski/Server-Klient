BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS Stanowisko (
	"id"	INTEGER,
	"nazwa"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS Pracownik (
	"id"	INTEGER,
	"imie"	TEXT,
	"nazwisko"	TEXT,
	"stanowisko"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("stanowisko") REFERENCES Stanowisko("id")
);
CREATE TABLE IF NOT EXISTS Jednostka (
	"id"	INTEGER,
	"nazwa"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS PracJednLnk (
	"id_prac"	INTEGER,
	"id_jedn"	INTEGER,
	FOREIGN KEY("id_prac") REFERENCES Pracownik("id"),
	FOREIGN KEY("id_jedn") REFERENCES Jednostka("id")
);

INSERT INTO Stanowisko VALUES (1,'kierownik');
INSERT INTO Stanowisko VALUES (2,'sekretarz');
INSERT INTO Stanowisko VALUES (3,'dyrektor');
INSERT INTO Pracownik VALUES (1,'Tomasz','Tomaszewski',3);
INSERT INTO Pracownik VALUES (2,'Agata','Agatkowska',1);
INSERT INTO Pracownik VALUES (3,'Juliusz','Juliański',2);
INSERT INTO Pracownik VALUES (4,'Alicja','Alicewska',2);
INSERT INTO Pracownik VALUES (5,'Stefan','Stefanowski',1);
INSERT INTO Jednostka VALUES (1,'Produkcja');
INSERT INTO Jednostka VALUES (2,'Księgowość');
INSERT INTO PracJednLnk VALUES (1,2);
INSERT INTO PracJednLnk VALUES (2,1);
INSERT INTO PracJednLnk VALUES (3,1);
INSERT INTO PracJednLnk VALUES (3,2);

COMMIT;
