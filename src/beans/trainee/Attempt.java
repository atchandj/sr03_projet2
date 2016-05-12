package beans.trainee;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Attempt {
	private String topicName;
	private String questionnaireName;
	private int score;
	private Timestamp begining;
	private Timestamp end;
	private int durationInSeconds;
	private double scoreDivByDurationTimes100;

    public Attempt(String topicName, String questionnaireName, int score, Timestamp begining, Timestamp end, int durationInSeconds, double scoreDivByDurationTimes100){
        this.topicName = topicName;
        this.questionnaireName = questionnaireName;
        this.score = score;
        this.begining = begining;
        this.end = end;
        this.durationInSeconds = durationInSeconds;
        this.scoreDivByDurationTimes100 = scoreDivByDurationTimes100;
    }
    
    public Attempt(){
        this.topicName = null;
        this.questionnaireName = null;
        this.score = 0;
        this.begining = null;
        this.end = null;
        this.durationInSeconds = 0;
        this.scoreDivByDurationTimes100 = 0.0;
    }
    
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getQuestionnaireName() {
		return questionnaireName;
	}
	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getBegining() {
		String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.begining);
		return formattedAccountCreation;
	}
	public void setBegining(Timestamp begining) {
		this.begining = begining;
	}
	public String getEnd() {
		String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.end);
		return formattedAccountCreation;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public int getDurationInSeconds() {
		return durationInSeconds;
	}
	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
	public double getScoreDivByDurationTimes100() {
		return (double)Math.round(scoreDivByDurationTimes100 * 1000d) / 1000d;
	}
	public void setScoreDivByDurationTimes100(double scoreDivByDurationTimes100) {
		this.scoreDivByDurationTimes100 = scoreDivByDurationTimes100;
	}
}
