-- ======================================== Modèle Physique de Données ========================================

CREATE TABLE Trainee(
	id INT AUTO_INCREMENT,
	email VARCHAR(30) NOT NULL,
	surname VARCHAR(40) NOT NULL,
	name VARCHAR(30) NOT NULL,
	password VARCHAR(50) NOT NULL,
	phone CHAR(10) NOT NULL,
	company VARCHAR(30) NOT NULL,
	accountCreation DATETIME NOT NULL,
	accountStatus BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(email)
);

-- /* Ajout d'une clé artificielle pour les performances */

CREATE TABLE SuperUser(
	id INT AUTO_INCREMENT,
	email VARCHAR(30) NOT NULL,
	surname VARCHAR(40) NOT NULL,
	name VARCHAR(30) NOT NULL,
	password VARCHAR(50) NOT NULL,
	phone CHAR(10) NOT NULL,
	company VARCHAR(30) NOT NULL,
	accountCreation DATETIME NOT NULL,
	accountStatus BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(email)
);

-- /* Ajout d'une clé artificielle pour les performances */

-- -----------------------------------------------------------------------------------------------

CREATE TABLE Topic(
	name VARCHAR(50),
	PRIMARY KEY(name)
);

-- -----------------------------------------------------------------------------------------------

CREATE TABLE Questionnaire(
	id INT AUTO_INCREMENT,
	topic VARCHAR(50) NOT NULL,
	name VARCHAR(50) NOT NULL,
	active BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(topic, name),
	FOREIGN KEY(topic) REFERENCES Topic(name) ON DELETE CASCADE
);

-- /* Ajout d'une clé artificielle pour les performances */

-- -----------------------------------------------------------------------------------------------

CREATE TABLE Attempt(
	id INT AUTO_INCREMENT,
	trainee INT NOT NULL,
	questionnaire INT NOT NULL,
	score FLOAT NOT NULL,
	beginning DATETIME NOT NULL,
	end DATETIME NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(trainee, questionnaire),
	FOREIGN KEY(trainee) REFERENCES Trainee(id),
	FOREIGN KEY(questionnaire) REFERENCES Questionnaire(id)
);

-- /* Ajout d'une clé artificielle pour les performances */

-- -----------------------------------------------------------------------------------------------

CREATE TABLE Question(
	id INT AUTO_INCREMENT,
	questionnaire INT NOT NULL,
	orderNumber INT NOT NULL,
	value VARCHAR(1000) NOT NULL,
	active BOOLEAN NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(questionnaire, orderNumber),
	FOREIGN KEY(questionnaire) REFERENCES Questionnaire(id) ON DELETE CASCADE
);

-- /* Ajout d'une clé artificielle pour les performances */

-- -----------------------------------------------------------------------------------------------

CREATE TABLE Answer(
	id INT AUTO_INCREMENT,
	question INT NOT NULL,
	orderNumber INT NOT NULL,
	value VARCHAR(1000) NOT NULL, 
	active BOOLEAN NOT NULL,
	t VARCHAR(10) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(question, orderNumber),
	FOREIGN KEY(question) REFERENCES Question(id) ON DELETE CASCADE,
	CHECK(t LIKE 'GoodAnswer' OR t LIKE 'BadAnswer')
);

-- /* Ajout d'une clé artificielle pour les performances */


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

-- -----------------------------------------------------------------------------------------------

CREATE TABLE AttemptAnswer(
	attempt INT,
	answer INT,
	PRIMARY KEY(attempt, answer),
	FOREIGN KEY(attempt) REFERENCES Attempt(id),
	FOREIGN KEY(answer) REFERENCES Answer(id)
);

-- -----------------------------------------------------------------------------------------------

CREATE VIEW NotActivableQuestionnaire 
AS  
SELECT * 
FROM Questionnaire Q1
WHERE EXISTS (
	SELECT *
	FROM Question Q2
	WHERE Q2.questionnaire = Q1.id AND Q2.active = 0
) OR NOT EXISTS(
	SELECT *
	FROM Question Q2
	WHERE Q2.questionnaire = Q1.id
) OR Q1.active = 1;

CREATE VIEW ActivableQuestionnaire 
AS  
SELECT * 
FROM Questionnaire Q1
WHERE NOT EXISTS (
	SELECT *
	FROM Question Q2
	WHERE Q2.questionnaire = Q1.id AND Q2.active = 0
) AND EXISTS(
	SELECT *
	FROM Question Q2
	WHERE Q2.questionnaire = Q1.id
) AND Q1.active = 0;

CREATE VIEW NotDeletableQuestionnaire 
AS  
SELECT * 
FROM Questionnaire Q1
WHERE EXISTS (
	SELECT *
	FROM Question Q2
	WHERE Q2.questionnaire = Q1.id AND Q2.active = 1
) OR Q1.active = 1;

CREATE VIEW DeletableQuestionnaire 
AS  
SELECT * 
FROM Questionnaire Q1
WHERE NOT EXISTS (
	SELECT *
	FROM Question Q2
	WHERE Q2.questionnaire = Q1.id AND Q2.active = 1
) AND Q1.active = 0;

-- -----------------------------------------------------------------------------------------------