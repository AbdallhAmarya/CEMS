package logic;

import java.io.Serializable;
import java.util.Map;

public class Exam implements Serializable {
    private static Exam instance;
    private String info;
    private String ID;
    private String subject;
    private String course;
    private int duration;
    private String descriptionForLecturers;
    private String descriptionForStudents;
    private String question;
    private String totalPoint;
    private String pointPerQuestion;
    private String lecturer;

    private Exam(String info, String ID, String subject, String course, int duration,
                 String descriptionForLecturers, String descriptionForStudents, String question,
                 String totalPoint, String pointPerQuestion, String lecturer) {
        this.info = info;
        this.ID = ID;
        this.subject = subject;
        this.course = course;
        this.duration = duration;
        this.descriptionForLecturers = descriptionForLecturers;
        this.descriptionForStudents = descriptionForStudents;
        this.question = question;
        this.totalPoint = totalPoint;
        this.pointPerQuestion = pointPerQuestion;
        this.lecturer = lecturer;
    }

    public static Exam getInstance(String info, String ID, String subject, String course, int duration,
                                   String descriptionForLecturers, String descriptionForStudents, String question,
                                   String totalPoint, String pointPerQuestion, String lecturer) {
            instance = new Exam(info, ID, subject, course, duration,
                                descriptionForLecturers, descriptionForStudents, question,
                                totalPoint, pointPerQuestion, lecturer);
        return instance;
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

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescriptionForLecturers() {
        return descriptionForLecturers;
    }

    public void setDescriptionForLecturers(String descriptionForLecturers) {
        this.descriptionForLecturers = descriptionForLecturers;
    }

    public String getDescriptionForStudents() {
        return descriptionForStudents;
    }

    public void setDescriptionForStudents(String descriptionForStudents) {
        this.descriptionForStudents = descriptionForStudents;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getPointPerQuestion() {
        return pointPerQuestion;
    }

    public void setPointPerQuestion(String pointPerQuestion) {
        this.pointPerQuestion = pointPerQuestion;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public static Exam convertToExam(Map<String, Object> row) {
        String info = (String) row.get("info");
        String ID = (String) row.get("ID");
        String subject = (String) row.get("Subject");
        String course = (String) row.get("Course");
        int duration = row.get("Duration") != null ? ((Integer) row.get("Duration")).intValue() : 0;
        String descriptionForLecturers = (String) row.get("DescriptionForLecturers");
        String descriptionForStudents = (String) row.get("DescriptionForStudents");
        String question = (String) row.get("Question");
        String totalPoint = (String) row.get("TotalPoint");
        String pointPerQuestion = (String) row.get("PointPerQuestion");
        String lecturer = (String) row.get("Lecturer");

        return getInstance(info, ID, subject, course, duration,
                           descriptionForLecturers, descriptionForStudents, question,
                           totalPoint, pointPerQuestion, lecturer);
    }

    @Override
    public String toString() {
        return "Exam [info=" + info + ", ID=" + ID + ", Subject=" + subject + ", Course=" + course + ", Duration="
                + duration + ", DescriptionForLecturers=" + descriptionForLecturers + ", DescriptionForStudents="
                + descriptionForStudents + ", Question=" + question + ", TotalPoint=" + totalPoint
                + ", PointPerQuestion=" + pointPerQuestion + "]";
    }
}
