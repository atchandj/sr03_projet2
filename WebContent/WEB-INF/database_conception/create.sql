-- ======================================== Modèle Logique de Données ========================================

CREATE TABLE Trainee(
	id INT,
	email VARCHAR(30) NOT NULL,
	surname VARCHAR(40) NOT NULL,
	name VARCHAR(30) NOT NULL,
	password VARCHAR(50) NOT NULL,
	phone INT(10) NOT NULL,
	company VARCHAR(30) NOT NULL,
	accountCreation DATE NOT NULL,
	accountStatus BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(email)
);

/* Ajout d'une clé artificielle pour les performances */

CREATE TABLE SuperUser(
	id INT,
	email VARCHAR(30) NOT NULL,
	surname VARCHAR(40) NOT NULL,
	name VARCHAR(30) NOT NULL,
	password VARCHAR(50) NOT NULL,
	phone INT(10) NOT NULL,
	company VARCHAR(30) NOT NULL,
	accountCreation DATE NOT NULL,
	accountStatus BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(email)
);

/* Ajout d'une clé artificielle pour les performances */

-----------------------------------------------------------------------------------------------

CREATE TABLE Topic(
	name VARCHAR(50),
	PRIMARY KEY(name)
);

-----------------------------------------------------------------------------------------------

CREATE TABLE Questionnaire(
	id INT,
	topic VARCHAR(50) NOT NULL,
	name VARCHAR(50) NOT NULL,
	active BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(topic, name),
	FOREIGN KEY(topic) REFERENCES Topic(name)
);

/* Ajout d'une clé artificielle pour les performances */

-----------------------------------------------------------------------------------------------

CREATE TABLE Attempt(
	id INT,
	trainee INT NOT NULL,
	questionnaire INT NOT NULL,
	score FLOAT NOT NULL,
	beginning DATE NOT NULL,
	end DATE NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(trainee, questionnaire),
	FOREIGN KEY(trainee) REFERENCES Trainee(id),
	FOREIGN KEY(questionnaire) REFERENCES Questionnaire(id)
);

/* Ajout d'une clé artificielle pour les performances */

-----------------------------------------------------------------------------------------------

CREATE TABLE Question(
	id INT,
	questionnaire INT NOT NULL,
	orderNumber INT NOT NULL,
	value VARCHAR(1000) NOT NULL,
	active BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(questionnaire, orderNumber),
	FOREIGN KEY(questionnaire) REFERENCES Questionnaire(id)
);

/* Ajout d'une clé artificielle pour les performances */

-----------------------------------------------------------------------------------------------

CREATE TABLE Answer(
	id INT,
	question INT NOT NULL,
	orderNumber INT NOT NULL,
	value VARCHAR(1000) NOT NULL, 
	active BOOLEAN NOT NULL,
	t VARCHAR(10) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(question, orderNumber),
	FOREIGN KEY(question) REFERENCES Question(id),
	CHECK(t) IN ('GoodAnswer', 'BadAnswer')
);

/* Ajout d'une clé artificielle pour les performances */


CREATE VIEW vGoodAnswer
AS
SELECT A.question, A.orderNumber, A.value, A.active
FROM Answer A
WHERE A.t='GoodAnswer';

CREATE VIEW vBadAnswer
AS
SELECT A.question, A.orderNumber, A.value, A.active
FROM Answer A
WHERE A.t='BadAnswer';

-----------------------------------------------------------------------------------------------

CREATE TABLE AttemptAnswer(
	PRIMARY KEY(attempt, answer),
	FOREIGN KEY(attempt) REFERENCES Attempt(id),
	FOREIGN KEY(answer) REFERENCES Answer(id)
);