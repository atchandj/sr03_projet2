-- ========== Trainee ===========================================================================

INSERT INTO Trainee 
(email, surname, name, password, phone, company, accountCreation, accountStatus)
VALUES 
("ckyle@gmail.com", "Kyle", "Cédric", "ckyle", "0156201074", "UTC", NOW(), TRUE),
("jdiesel@gmail.com", "Diesel", "Jean", "jdiesel", "0656221084", "UTC", NOW(), TRUE);

-- ========== SuperUser ===========================================================================

INSERT INTO SuperUser 
(email, surname, name, password, phone, company, accountCreation, accountStatus)
VALUES 
("atchandj@gmail.com", "Tchandjou", "Adrien", "atchandj", "0654651084", "UTC", "2015-07-02 15:00:12", TRUE),
("daniel@gmail.com", "Artchounin", "Daniel", "daniel", "0657311307", "UTC", "2015-05-02 11:00:12", TRUE);

-- ========== Topic ===========================================================================

INSERT INTO Topic
VALUES 
("WebServices"),
("Développement JAVA"),
("JavaScript"),
("Ajax"),
("C et C++"),
("JEE");

-- ========== Questionnaire ===========================================================================

INSERT INTO Questionnaire (topic, name, active)
VALUES
("JavaScript", "Boucle and Condition", FALSE),
("JEE", "Servlet", FALSE);

-- ========== Question ===========================================================================

INSERT INTO Question (questionnaire, orderNumber, value, active)
VALUES
(1, 1, "Qu'est qu'une servlet ?", TRUE),
(1, 2, "Quelle est la particularité d'une servlet ?", TRUE);

-- ========== Answer ===========================================================================
INSERT INTO Answer(question, orderNumber, value, active, t)
VALUES
(1, 1, "a", 1, "BadAnswer"),
(1, 2, "b", 0, "GoodAnswer"),
(1, 3, "c", 1, "BadAnswer"),
(1, 4, "Classe capable de recevoir une requête HTTP, et de renvoyer une réponse HTTP.", 1, "BadAnswer"),
(2, 1, "a", 1, "BadAnswer"),
(2, 2, "b", 0, "BadAnswer"),
(2, 3, "c", 1, "GoodAnswer"),
(2, 4, "d", 0, "BadAnswer"),
(2, 5, "C'est le conteneur web", 1, "BadAnswer");

-- ========== Attempt ===========================================================================
INSERT INTO Attempt(trainee, questionnaire, score, beginning, end)
VALUES
(1, 2, 15, NOW(), DATE_ADD(NOW(), INTERVAL 4 HOUR)),
(2, 1, 7, NOW(), DATE_ADD(NOW(), INTERVAL 10 HOUR)),
(2, 2, 18, NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR));