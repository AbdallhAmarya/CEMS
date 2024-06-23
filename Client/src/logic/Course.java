package logic;

import java.io.Serializable;

import java.util.Map;

public class Course implements Serializable {
    private static Course instance;
    private String ID;
    private String courseName;
    private String ID_Subject;
    private String department;

    private Course(String ID, String courseName, String ID_Subject, String department) {
        this.ID = ID;
        this.courseName = courseName;
        this.ID_Subject = ID_Subject;
        this.department = department;
    }

    public static Course getInstance(String ID, String courseName, String ID_Subject, String department) {
        instance = new Course(ID, courseName, ID_Subject, department);
        return instance;
    }

    public static Course convertCourse(Map<String, Object> row) {
        String ID = (String) row.get("ID");
        String courseName = (String) row.get("CourseName");
        String ID_Subject = (String) row.get("ID_Subject");
        String department = (String) row.get("Department");
        return getInstance(ID, courseName, ID_Subject, department);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getID_Subject() {
        return ID_Subject;
    }

    public void setID_Subject(String ID_Subject) {
        this.ID_Subject = ID_Subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}