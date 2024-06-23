package logic;

import java.io.Serializable;
import java.util.Map;

public class examresult implements Serializable {
    private static examresult instance;

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
    private String noteforstudent;
    private String StudentID;
    private Integer startexamNum;
    private String DurationTake;
    private String FormSubmission;

    private examresult(Integer iD, String examID, String examResult, String subject, String course, String examAnswer,
                      String pointperquestion, String rightanswer, String lecturerID, String date, String time,
                      String status, String note, String noteforstudent, String studentID, Integer startexamNum, String durationTake,
                      String formSubmission) {
        super();
        ID = iD;
        ExamID = examID;
        ExamResult = examResult;
        Subject = subject;
        Course = course;
        ExamAnswer = examAnswer;
        this.pointperquestion = pointperquestion;
        this.rightanswer = rightanswer;
        this.lecturerID = lecturerID;
        this.date = date;
        this.time = time;
        this.status = status;
        this.note = note;
        this.noteforstudent = noteforstudent;
        StudentID = studentID;
        this.startexamNum = startexamNum;
        DurationTake = durationTake;
        FormSubmission = formSubmission;
    }

    public static examresult getInstance(Integer iD, String examID, String examResult, String subject, String course,
                                         String examAnswer, String pointperquestion, String rightanswer,
                                         String lecturerID, String date, String time, String status, String note,String noteforstudent,
                                         String studentID, Integer startexamNum, String durationTake,
                                         String formSubmission) {
        synchronized (examresult.class) {
                    instance = new examresult(iD, examID, examResult, subject, course, examAnswer, pointperquestion,
                            rightanswer, lecturerID, date, time, status, note,noteforstudent, studentID, startexamNum, durationTake,
                            formSubmission);
        }
        return instance;
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
        String Noteforstudent = (String) row.get("noteforstudent");
        Integer startexamNum = (Integer) row.get("startexamNum");
        String durationTake = (String) row.get("DurationTake");
        String formSubmission = (String) row.get("FormSubmission");
        return getInstance(ID, ExamID, ExamResult, Subject, Course, ExamAnswer, PointPerQuestion, RightAnswer, Lecturer,
                date, time, Status, Note,Noteforstudent, StudentID, startexamNum, durationTake, formSubmission);
    }

	public static examresult getInstance() {
		return instance;
	}

	public static void setInstance(examresult instance) {
		examresult.instance = instance;
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

	public String getNoteforstudent() {
		return noteforstudent;
	}

	public void setNoteforstudent(String noteforstudent) {
		this.noteforstudent = noteforstudent;
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

	public String getDurationTake() {
		return DurationTake;
	}

	public void setDurationTake(String durationTake) {
		DurationTake = durationTake;
	}

	public String getFormSubmission() {
		return FormSubmission;
	}

	public void setFormSubmission(String formSubmission) {
		FormSubmission = formSubmission;
	}

	@Override
	public String toString() {
		return "examresult [ID=" + ID + ", ExamID=" + ExamID + ", ExamResult=" + ExamResult + ", Subject=" + Subject
				+ ", Course=" + Course + ", ExamAnswer=" + ExamAnswer + ", pointperquestion=" + pointperquestion
				+ ", rightanswer=" + rightanswer + ", lecturerID=" + lecturerID + ", date=" + date + ", time=" + time
				+ ", status=" + status + ", note=" + note + ", noteforstudent=" + noteforstudent + ", StudentID="
				+ StudentID + ", startexamNum=" + startexamNum + ", DurationTake=" + DurationTake + ", FormSubmission="
				+ FormSubmission + "]";
	}
}
