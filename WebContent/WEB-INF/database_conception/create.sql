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
	FOREIGN KEY(topic) REFERENCES Topic(name) ON DELETE CASCADE ON UPDATE CASCADE
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

CREATE VIEW NotActivableQuestion
AS  
SELECT * 
FROM Question Q
WHERE EXISTS (
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.active = 0
) OR NOT EXISTS(
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.t = 'GoodAnswer'
) OR Q.active = 1;

CREATE VIEW ActivableQuestion 
AS  
SELECT * 
FROM Question Q
WHERE NOT EXISTS (
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.active = 0
) AND EXISTS(
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.t = 'GoodAnswer'
) AND Q.active = 0;

CREATE VIEW NotDeletableQuestion 
AS  
SELECT * 
FROM Question Q
WHERE EXISTS (
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.active = 1
) OR Q.active = 1;

CREATE VIEW DeletableQuestion 
AS  
SELECT * 
FROM Question Q
WHERE NOT EXISTS (
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.active = 1
) AND Q.active = 0;

CREATE VIEW NotChangeableTrueAnswerQuestion 
AS  
SELECT * 
FROM Question Q
WHERE EXISTS (
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.active = 1 AND A.t = 'GoodAnswer'
);

CREATE VIEW ChangeableTrueAnswerQuestion
AS  
SELECT * 
FROM Question Q
WHERE NOT EXISTS (
	SELECT *
	FROM Answer A
	WHERE A.question = Q.id AND A.active = 1 AND A.t = 'GoodAnswer'
);

-- -----------------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE deleteAnswer(IN questionID INT, IN answerOrderNumber INT)
BEGIN
	START TRANSACTION;
    DELETE FROM Answer
	WHERE question = questionID AND orderNumber = answerOrderNumber;
    UPDATE Answer
    SET orderNumber = orderNumber - 1
    WHERE orderNumber > answerOrderNumber AND question = questionID;
    COMMIT;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE addAnswer(IN questionId INT, IN answerValue VARCHAR(255))
BEGIN
 	DECLARE numberOfAnswers INT;
	DECLARE answerOrderNumber INT;
	DECLARE numberOfActiveQuestions INT;
	START TRANSACTION;
	SELECT COUNT(*) INTO numberOfActiveQuestions
	FROM Question Q
	WHERE Q.id = questionId AND Q.active = TRUE;
	IF numberOfActiveQuestions = 0 THEN
		SELECT COUNT(*) INTO numberOfAnswers
		FROM Answer
		WHERE question = questionId;
		SET answerOrderNumber = numberOfAnswers + 1;
		IF numberOfAnswers = 0 THEN
			INSERT INTO Answer(question, orderNumber, value, active, t)
			VALUE(questionId, answerOrderNumber, answerValue, FALSE, 'GoodAnswer');
		ELSE
			INSERT INTO Answer(question, orderNumber, value, active, t)
			VALUE(questionId, answerOrderNumber, answerValue, FALSE, 'BadAnswer');
		END IF;
	END IF;
    COMMIT;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE addQuesion(IN questionnaireId INT, IN questionValue VARCHAR(255))
BEGIN
 	DECLARE numberOfQuestions INT;
	DECLARE questionOrderNumber INT;
	DECLARE numberOfActiveQuestionnaires INT;
	START TRANSACTION;
	SELECT COUNT(*) INTO numberOfActiveQuestionnaires
	FROM Questionnaire Q
	WHERE Q.id = questionnaireId AND Q.active = TRUE;
	IF numberOfActiveQuestionnaires = 0 THEN	
		SELECT COUNT(*) INTO numberOfQuestions
		FROM Question
		WHERE questionnaire = questionnaireId;
		SET questionOrderNumber = numberOfQuestions + 1;
		INSERT INTO Question(questionnaire, orderNumber, value, active)
		VALUE(questionnaireId, questionOrderNumber, questionValue, FALSE);
	END IF;
    COMMIT;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE changeTrueAnswerOfAQuestion(IN questionId INT, IN answerOrderNumber INT)
BEGIN
	DECLARE numberOfChangeableTrueAnswerQuestions INT;
	DECLARE numberOfInterestingActiveQuestions INT;
	START TRANSACTION;
	SELECT COUNT(*) INTO numberOfChangeableTrueAnswerQuestions
	FROM ChangeableTrueAnswerQuestion CTAQ
	WHERE CTAQ.id = questionID;
	SELECT COUNT(*) INTO numberOfInterestingActiveQuestions
	FROM Answer A
	WHERE A.question = questionId AND A.orderNumber = answerOrderNumber AND A.active = TRUE;
	IF (numberOfChangeableTrueAnswerQuestions = 1 AND numberOfInterestingActiveQuestions = 0) THEN
		UPDATE Answer
		SET t = 'BadAnswer'
		WHERE question = questionID;
		UPDATE Answer
		SET t = 'GoodAnswer'
		WHERE question = questionID AND orderNumber = answerOrderNumber;
    END IF;
    COMMIT;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE changeQuestionsOrder(IN questionnaireId INT, IN question1OrderNumber INT, IN question2OrderNumber INT)
BEGIN
	DECLARE numberOfNotChangeableQuestions INT;
	START TRANSACTION;
	SELECT COUNT(*) INTO numberOfNotChangeableQuestions
	FROM Question Q
	WHERE Q.questionnaire = questionnaireId AND (Q.orderNumber = question1OrderNumber OR Q.orderNumber = question2OrderNumber) AND Q.active=TRUE;
	IF (numberOfNotChangeableQuestions = 0) THEN
		UPDATE Question 
		SET orderNumber = 100000
		WHERE questionnaire = questionnaireId AND orderNumber = question1OrderNumber;
		UPDATE Question 
		SET orderNumber = question1OrderNumber
		WHERE questionnaire = questionnaireId AND orderNumber = question2OrderNumber;
		UPDATE Question 
		SET orderNumber = question2OrderNumber
		WHERE questionnaire = questionnaireId AND orderNumber = 100000;
    END IF;
    COMMIT;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE changeAnswersOrder(IN questionId INT, IN answer1OrderNumber INT, IN answer2OrderNumber INT)
BEGIN
	DECLARE numberOfNotChangeableAnswers INT;
	START TRANSACTION;
	SELECT COUNT(*) INTO numberOfNotChangeableAnswers
	FROM Answer A
	WHERE A.question = questionId AND (A.orderNumber = answer1OrderNumber OR A.orderNumber = answer2OrderNumber) AND A.active=TRUE;
	IF (numberOfNotChangeableAnswers = 0) THEN
		UPDATE Answer 
		SET orderNumber = 100000
		WHERE question = questionId AND orderNumber = answer1OrderNumber;
		UPDATE Answer 
		SET orderNumber = answer1OrderNumber
		WHERE question = questionId AND orderNumber = answer2OrderNumber;
		UPDATE Answer 
		SET orderNumber = answer2OrderNumber
		WHERE question = questionId AND orderNumber = 100000;
    END IF;
    COMMIT;
END//
DELIMITER ;

-- -----------------------------------------------------------------------------------------------