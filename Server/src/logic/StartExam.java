package logic;

import java.io.Serializable;
import java.sql.Time;
import java.util.Map;

public class StartExam implements Serializable{
	private Integer Startnum;
	private String ExamID;
	private String LecturerName;
	private String ExamCode;
	private Time StartTime;
	private int Duration;
	private int IsLocked;
	private String Subject;
	private String Course;
	private int AddDuration;
	
	
	
	public StartExam(Integer startnum, String examID, String lecturerName, String examCode, Time startTime,
			int duration, int isLocked, String subject, String course, int addDuration) {
		super();
		Startnum = startnum;
		ExamID = examID;
		LecturerName = lecturerName;
		ExamCode = examCode;
		StartTime = startTime;
		Duration = duration;
		IsLocked = isLocked;
		Subject = subject;
		Course = course;
		AddDuration = addDuration;
	}
	
	public Integer getStartnum() {
		return Startnum;
	}



	public void setStartnum(Integer startnum) {
		Startnum = startnum;
	}



	public String getExamID() {
		return ExamID;
	}



	public void setExamID(String examID) {
		ExamID = examID;
	}



	public String getLecturerName() {
		return LecturerName;
	}



	public void setLecturerName(String lecturerName) {
		LecturerName = lecturerName;
	}



	public String getExamCode() {
		return ExamCode;
	}



	public void setExamCode(String examCode) {
		ExamCode = examCode;
	}



	public Time getStartTime() {
		return StartTime;
	}



	public void setStartTime(Time startTime) {
		StartTime = startTime;
	}



	public int getDuration() {
		return Duration;
	}



	public void setDuration(int duration) {
		Duration = duration;
	}



	public int getIsLocked() {
		return IsLocked;
	}



	public void setIsLocked(int isLocked) {
		IsLocked = isLocked;
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



	public int getAddDuration() {
		return AddDuration;
	}



	public void setAddDuration(int addDuration) {
		AddDuration = addDuration;
	}

	

	@Override
	public String toString() {
		return "StartExam [Startnum=" + Startnum + ", ExamID=" + ExamID + ", LecturerName=" + LecturerName
				+ ", ExamCode=" + ExamCode + ", StartTime=" + StartTime + ", Duration=" + Duration + ", IsLocked="
				+ IsLocked + ", Subject=" + Subject + ", Course=" + Course + ", AddDuration=" + AddDuration + "]";
	}

	public static StartExam convertToStartExam(Map<String, Object> row) {
			Integer  id = (Integer) row.get("Startnum");
	        String examID = (String) row.get("ExamID");
	        String lecturerName = (String) row.get("LecturerName");
	        String examCode = (String) row.get("ExamCode");
	        Time startTime = (Time) row.get("StartTime");
	        int duration = row.get("Duration") != null ? ((Integer) row.get("Duration")).intValue() : 0;
	        int isLocked = row.get("IsLocked") != null ? ((Integer) row.get("IsLocked")).intValue() : 0;
	        String subject = (String) row.get("Subject");
	        String course = (String) row.get("Course");
	        int addduration = row.get("AddDuration") != null ? ((Integer) row.get("AddDuration")).intValue() : 0;
	        return new StartExam(id,examID, lecturerName, examCode, startTime, duration, isLocked, subject, course,addduration);
	}
}
