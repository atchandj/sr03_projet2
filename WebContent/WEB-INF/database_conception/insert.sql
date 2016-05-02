-- ========== Trainee ===========================================================================

INSERT INTO Trainee 
(email, surname, name, password, phone, company, accountCreation, accountStatus)
VALUES ("ckyle@gmail.com", "Kyle", "Cédric", "ckyle", "0156201074", "UTC", NOW(), TRUE);

INSERT INTO Trainee 
(email, surname, name, password, phone, company, accountCreation, accountStatus)
VALUES ("jdiesel@gmail.com", "Diesel", "Jean", "jdiesel", "0656221084", "UTC", NOW(), TRUE);

-- ========== SuperUser ===========================================================================

INSERT INTO SuperUser 
(email, surname, name, password, phone, company, accountCreation, accountStatus)
VALUES ("atchandj@gmail.com", "Tchandjou", "Adrien", "atchandj", "0654651084", "UTC", "2015-07-02 15:00:12", TRUE);

INSERT INTO SuperUser 
(email, surname, name, password, phone, company, accountCreation, accountStatus)
VALUES ("daniel@gmail.com", "Artchounin", "Daniel", "daniel", "0657311307", "UTC", "2015-05-02 11:00:12", TRUE);

-- ========== Topic ===========================================================================

INSERT INTO Topic
VALUES 
("WebServices"),
("Développement JAVA"),
("JavaScript"),
("Ajax"),
("C/C++"),
("JEE");

-- ========== Questionnaire ===========================================================================

INSERT INTO Questionnaire (topic, name, active)
VALUES("JavaScript", "Boucle & Condition", TRUE);

INSERT INTO Questionnaire (topic, name, active)
VALUES("JEE", "Servlet", TRUE);

-- ========== Question ===========================================================================

INSERT INTO Question (questionnaire, orderNumber, value, active)
VALUES(1, 1, "Qu'est qu'une servelt ?", TRUE);

INSERT INTO Question (questionnaire, orderNumber, value, active)
VALUES(1, 2, "Quelle est la particularité d'une servlet ?", TRUE);