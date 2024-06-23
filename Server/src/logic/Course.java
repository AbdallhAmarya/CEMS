package logic;

import java.util.Map;

public class Course {
	private String ID;
	private String CourseName;
	private String ID_Subject;
	public Course(String iD, String courseName, String iD_Subject) {
		super();
		ID = iD;
		CourseName = courseName;
		ID_Subject = iD_Subject;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public String getID_Subject() {
		return ID_Subject;
	}
	public void setID_Subject(String iD_Subject) {
		ID_Subject = iD_Subject;
	}
	public static Course convertToCourse(Map<String, Object> row) {
        String id = (String) row.get("ID");
        String courseName = (String) row.get("CourseName");
        String idSubject = (String) row.get("ID_Subject");
        return new Course(id, courseName, idSubject);
    }
	    

}
