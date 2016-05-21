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
("JavaScript", "Boucle & Condition", TRUE),
("JEE", "Servlet", TRUE);

-- ========== Question ===========================================================================

INSERT INTO Question (questionnaire, orderNumber, value, active)
VALUES
(2, 1, "Qu'est qu'une servlet ?", TRUE),
(2, 2, "Quelle est la particularité d'une servlet ?", TRUE),
(1, 1, "Qu'est ce la structure if...else ?", TRUE),
(1, 2, "A quoi sert l'opérateur « ? » ?", TRUE),
(1, 3, "A quoi sert la boucle do...while ?", TRUE);

-- ========== Answer ===========================================================================
-- Questionnaire 2 : Servlet
INSERT INTO Answer(question, orderNumber, value, active, t)
VALUES
(1, 1, "Une méthode HTTP", 1, "BadAnswer"),
(1, 3, "Conteneur web", 1, "BadAnswer"),
(1, 4, "Classe capable de recevoir une requête HTTP, et de renvoyer une réponse HTTP.", 1, "GoodAnswer"),
(2, 1, "C'est aussi un JavaBeans", 1, "BadAnswer"),
(2, 2, "Elle n'est pas réutilisable", 1, "BadAnswer"),
(2, 3, "Elle est toujours abstraite", 0, "BadAnswer"),
(2, 4, "Traitement de requêtes et la personnalisation de réponses", 1, "GoodAnswer");

-- Questionnaire 1 : Boucle & Condition
INSERT INTO Answer(question, orderNumber, value, active, t)
VALUES
(3, 1, "C'est une boucle", 1, "BadAnswer"),
(3, 2, "C'est une condition", 1, "GoodAnswer"),
(4, 1, "C'est l'opérateur ternaire", 1, "GoodAnswer"),
(4, 2, "Opérateur ET, permettant de préciser une condition", 1, "BadAnswer"),
(4, 3, "Opérateur OU, permettant de préciser une condition", 1, "BadAnswer"),
(4, 4, "Opérateur supérieur ou égal", 1, "BadAnswer"),
(5, 1, "Boucle permettant d'itérer un certain nombre de requête tant que la condition est respectée.", 1, "BadAnswer"),
(5, 2, "Boucle permettant d'itérer un certain nombre de requête au moins une fois tant que la condition est respectée.", 1, "GoodAnswer");

-- ========== Attempt ===========================================================================
INSERT INTO Attempt(trainee, questionnaire, score, beginning, end)
VALUES
(1, 2, 2, NOW(), DATE_ADD(NOW(), INTERVAL 4 HOUR)),
(2, 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 10 HOUR)),
(2, 2, 0, NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR));

-- ========== AttemptAnswer ===========================================================================

INSERT INTO AttemptAnswer(attempt, answer)
VALUE
(1,3),
(1,7),
(2,3),
(2,5),
(3,8),
(3,12),
(3,14);