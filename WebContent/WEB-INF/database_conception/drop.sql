DROP VIEW vBadAnswer;
DROP VIEW vGoodAnswer;
DROP VIEW NotActivableQuestionnaire;
DROP VIEW ActivableQuestionnaire;
DROP VIEW NotDeletableQuestionnaire;
DROP VIEW DeletableQuestionnaire;
DROP VIEW NotActivableQuestion;
DROP VIEW ActivableQuestion;
DROP VIEW NotDeletableQuestion;
DROP VIEW DeletableQuestion;
DROP VIEW NotChangeableTrueAnswerQuestion;
DROP VIEW ChangeableTrueAnswerQuestion;

DROP TABLE AttemptAnswer;
DROP TABLE Answer;
DROP TABLE Question;
DROP TABLE Attempt;
DROP TABLE Questionnaire;
DROP TABLE Topic;
DROP TABLE SuperUser;
DROP TABLE Trainee;

DROP PROCEDURE IF EXISTS deleteAnswer;
DROP PROCEDURE IF EXISTS addAnswer;
DROP PROCEDURE IF EXISTS addQuesion;
DROP PROCEDURE IF EXISTS changeTrueAnswerOfAQuestion;