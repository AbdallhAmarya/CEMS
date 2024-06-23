package logic;

import java.io.Serializable;
import java.util.Map;

public class Question implements Serializable {
    private static Question instance;
    private String Info;
    private String QuestionNum;
    private String Subject;
    private String CourseName;
    private String QuestionText;
    private String LecturerID;
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String AnswerCorrect;

    private Question(String info, String questionNum, String subject, String courseName, String questionText,
            String lecturerID, String answer1, String answer2, String answer3, String answerCorrect) {
        this.Info = info;
        this.QuestionNum = questionNum;
        this.Subject = subject;
        this.CourseName = courseName;
        this.QuestionText = questionText;
        this.LecturerID = lecturerID;
        this.Answer1 = answer1;
        this.Answer2 = answer2;
        this.Answer3 = answer3;
        this.AnswerCorrect = answerCorrect;
    }

    public static synchronized Question getInstance(String info, String questionNum, String subject, String courseName,
            String questionText, String lecturerID, String answer1, String answer2, String answer3,
            String answerCorrect) {
        instance = new Question(info, questionNum, subject, courseName, questionText, lecturerID, answer1, answer2,
                    answer3, answerCorrect);
        return instance;
    }

    public static Question convertToQuestion(Map<String, Object> row) {
        String info = (String) row.get("Info");
        String questionNum = (String) row.get("QuestionNumber");
        String subject = (String) row.get("Subject");
        String courseName = (String) row.get("CourseName");
        String questionText = (String) row.get("QuestionText");
        String lecturerID = (String) row.get("LecturerID");
        String answer1 = (String) row.get("Answer1");
        String answer2 = (String) row.get("Answer2");
        String answer3 = (String) row.get("Answer3");
        String answerCorrect = (String) row.get("AnswerCorrect");
        return getInstance(info, questionNum, subject, courseName, questionText, lecturerID, answer1, answer2, answer3,
                answerCorrect);
    }
	
	
	public String getInfo() {
		return Info;
	}


	public void setInfo(String info) {
		Info = info;
	}


	public String getQuestionNum() {
		return QuestionNum;
	}


	public void setQuestionNum(String questionNum) {
		QuestionNum = questionNum;
	}


	public String getSubject() {
		return Subject;
	}


	public void setSubject(String subject) {
		Subject = subject;
	}


	public String getCourseName() {
		return CourseName;
	}


	public void setCourseName(String courseName) {
		CourseName = courseName;
	}


	public String getQuestionText() {
		return QuestionText;
	}


	public void setQuestionText(String questionText) {
		QuestionText = questionText;
	}


	public String getLecturerID() {
		return LecturerID;
	}


	public void setLecturerID(String lecturerID) {
		LecturerID = lecturerID;
	}


	public String getAnswer1() {
		return Answer1;
	}


	public void setAnswer1(String answer1) {
		Answer1 = answer1;
	}


	public String getAnswer2() {
		return Answer2;
	}


	public void setAnswer2(String answer2) {
		Answer2 = answer2;
	}


	public String getAnswer3() {
		return Answer3;
	}


	public void setAnswer3(String answer3) {
		Answer3 = answer3;
	}


	public String getAnswerCorrect() {
		return AnswerCorrect;
	}


	public void setAnswerCorrect(String answerCorrect) {
		AnswerCorrect = answerCorrect;
	}


	


	@Override
	public String toString() {
		return "Question [Info=" + Info + ", QuestionNum=" + QuestionNum + ", Subject=" + Subject + ", CourseName="
				+ CourseName + ", QuestionText=" + QuestionText + ", LecturerID=" + LecturerID + ", Answer1=" + Answer1
				+ ", Answer2=" + Answer2 + ", Answer3=" + Answer3 + ", AnswerCorrect=" + AnswerCorrect + "]";
	}
		
	/*
	 
	 public static Question convertToQuestion(Map<String, Object> row) {
	        String info = (String) row.get("Info");
	        String questionNum = (String) row.get("QuestionNumber");
	        String subject = (String) row.get("Subject");
	        String courseName = (String) row.get("CourseName");
	        String questionText = (String) row.get("QuestionText");
	        String lecturerID = (String) row.get("LecturerID");
	        Answer answer = (Answer) row.get("Answer");
	        return new Question(info, questionNum, subject, courseName, questionText, lecturerID, answer);
	    }
	    * 
	 */
	
}
