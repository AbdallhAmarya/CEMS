package logic;

public class CurrentExamSelectedQuestionStudent {
	private String QuestionID;
	private String QuestionText;
	private String StudentAnswer;
	private String rightAnswer;
	private String Point;
	public CurrentExamSelectedQuestionStudent(String questionID, String questionText, String studentAnswer,
			String rightAnswer, String point) {
		super();
		QuestionID = questionID;
		QuestionText = questionText;
		StudentAnswer = studentAnswer;
		this.rightAnswer = rightAnswer;
		Point = point;
	}
	public String getQuestionID() {
		return QuestionID;
	}
	public void setQuestionID(String questionID) {
		QuestionID = questionID;
	}
	public String getQuestionText() {
		return QuestionText;
	}
	public void setQuestionText(String questionText) {
		QuestionText = questionText;
	}
	public String getStudentAnswer() {
		return StudentAnswer;
	}
	public void setStudentAnswer(String studentAnswer) {
		StudentAnswer = studentAnswer;
	}
	public String getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	public String getPoint() {
		return Point;
	}
	public void setPoint(String point) {
		Point = point;
	}
	@Override
	public String toString() {
		return "CurrentExamSelectedQuestionStudent [QuestionID=" + QuestionID + ", QuestionText=" + QuestionText
				+ ", StudentAnswer=" + StudentAnswer + ", rightAnswer=" + rightAnswer + ", Point=" + Point + "]";
	}
}
