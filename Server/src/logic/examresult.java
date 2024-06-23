package logic;

import java.io.Serializable;
import java.util.Map;

public class examresult implements Serializable{
	private Integer ID;
	private String ExamID;
	private String ExamResult;
	private String Subject;
	private String Course;
	private String ExamAnswer;
	private String pointperquestion;
	private String rightanswer;
	private String lecturerID;
	private String date; 
	private String time;
	private String status;
	private String note;
	private String StudentID;
	private Integer startexamNum;
	
	
	public examresult(Integer iD, String examID, String examResult, String subject, String course, String examAnswer,
			String pointperquestion, String rightanswer, String lecturerId, String date, String time, String status,
			String note, String studentID, Integer startexamNum) {
		super();
		ID = iD;
		ExamID = examID;
		ExamResult = examResult;
		Subject = subject;
		Course = course;
		ExamAnswer = examAnswer;
		this.pointperquestion = pointperquestion;
		this.rightanswer = rightanswer;
		this.lecturerID = lecturerId;
		this.date = date;
		this.time = time;
		this.status = status;
		this.note = note;
		StudentID = studentID;
		this.startexamNum = startexamNum;
	}
	public static examresult convertToExamResult(Map<String, Object> row) {
	    Integer ID = (Integer) row.get("ID");
	    String ExamID = (String) row.get("ExamID");
	    String ExamResult = (String) row.get("ExamResult");
	    String Subject = (String) row.get("Subject");
	    String Course = (String) row.get("Course");
	    String ExamAnswer = (String) row.get("ExamAnswer");
	    String PointPerQuestion = (String) row.get("pointperquestion");
	    String RightAnswer = (String) row.get("rightanswer");
	    String Lecturer = (String) row.get("lecturerID");
	    String date = (String) row.get("date");
	    String time = (String) row.get("time");
	    String Status = (String) row.get("status");
	    String StudentID = (String) row.get("StudentID");
	    String Note = (String) row.get("note");
	    Integer startexamNum = (Integer) row.get("startexamNum");
	    return new examresult(ID, ExamID, ExamResult, Subject, Course, ExamAnswer, PointPerQuestion,
	        RightAnswer, Lecturer, date, time, Status, Note,StudentID,startexamNum);
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getExamID() {
		return ExamID;
	}
	public void setExamID(String examID) {
		ExamID = examID;
	}
	public String getExamResult() {
		return ExamResult;
	}
	public void setExamResult(String examResult) {
		ExamResult = examResult;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getCourse() {
		return Course;
	}
	public void setCourse(String course) {
		Course = course;
	}
	public String getExamAnswer() {
		return ExamAnswer;
	}
	public void setExamAnswer(String examAnswer) {
		ExamAnswer = examAnswer;
	}
	public String getPointperquestion() {
		return pointperquestion;
	}
	public void setPointperquestion(String pointperquestion) {
		this.pointperquestion = pointperquestion;
	}
	public String getRightanswer() {
		return rightanswer;
	}
	public void setRightanswer(String rightanswer) {
		this.rightanswer = rightanswer;
	}
	public String getLecturerID() {
		return lecturerID;
	}
	public void setLecturerID(String lecturerID) {
		this.lecturerID = lecturerID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStudentID() {
		return StudentID;
	}
	public void setStudentID(String studentID) {
		StudentID = studentID;
	}
	public Integer getStartexamNum() {
		return startexamNum;
	}
	public void setStartexamNum(Integer startexamNum) {
		this.startexamNum = startexamNum;
	}
	@Override
	public String toString() {
		return "examresult [ID=" + ID + ", ExamID=" + ExamID + ", ExamResult=" + ExamResult + ", Subject=" + Subject
				+ ", Course=" + Course + ", ExamAnswer=" + ExamAnswer + ", pointperquestion=" + pointperquestion
				+ ", rightanswer=" + rightanswer + ", lecturer=" + lecturerID + ", date=" + date + ", time=" + time
				+ ", status=" + status + ", note=" + note + ", StudentID=" + StudentID + ", startexamNum="
				+ startexamNum + "]";
	}
}
