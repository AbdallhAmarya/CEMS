package logic;

import java.io.Serializable;
import java.util.Map;

public class Exam implements Serializable {
	private String info;
    private String ID;
    private String Subject;
    private String Course;
    private int Duration;
    private String DescriptionForLecturers;
    private String DescriptionForStudents;
    private String question;
    private String TotalPoint;
    private String PointPerQuestion;
    public Exam() {
    }
    
	public Exam(String info, String iD, String subject, String course, int duration, String descriptionForLecturers,
			String descriptionForStudents, String question, String totalPoint, String pointPerQuestion) {
		super();
		this.info = info;
		ID = iD;
		Subject = subject;
		Course = course;
		Duration = duration;
		DescriptionForLecturers = descriptionForLecturers;
		DescriptionForStudents = descriptionForStudents;
		this.question = question;
		TotalPoint = totalPoint;
		PointPerQuestion = pointPerQuestion;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public String getDescriptionForLecturers() {
		return DescriptionForLecturers;
	}

	public void setDescriptionForLecturers(String descriptionForLecturers) {
		DescriptionForLecturers = descriptionForLecturers;
	}

	public String getDescriptionForStudents() {
		return DescriptionForStudents;
	}

	public void setDescriptionForStudents(String descriptionForStudents) {
		DescriptionForStudents = descriptionForStudents;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getTotalPoint() {
		return TotalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		TotalPoint = totalPoint;
	}

	public String getPointPerQuestion() {
		return PointPerQuestion;
	}

	public void setPointPerQuestion(String pointPerQuestion) {
		PointPerQuestion = pointPerQuestion;
	}
	public static Exam convertToExam(Map<String, Object> row) {
        String info = (String) row.get("info");
        String subject = (String) row.get("Subject");
        String course = (String) row.get("Course");
        String examNumber = (String) row.get("ExamNumber");
        int duration = (int) row.get("Duration");
        String descriptionForLecturers = (String) row.get("DescriptionForLecturers");
        String descriptionForStudents = (String) row.get("DescriptionForStudents");
        String question = (String) row.get("question");
        String TotalPoint = (String) row.get("TotalPoint");
        String PointPerQuestion = (String) row.get("PointPerQuestion");

        return new Exam(info, subject, course, examNumber, duration, descriptionForLecturers,
                descriptionForStudents, question,TotalPoint,PointPerQuestion);
    }
}

