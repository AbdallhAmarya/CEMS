package logic;

import java.util.Map;

public class Subject {
    private static Subject instance;
	private String ID;
	private String SubjectName;
	private Subject(String iD, String subjectName) {
		ID = iD;
		SubjectName = subjectName;
	}
	public static Subject getInstance(String ID, String SubjectName) {
        if (instance == null) {
            instance = new Subject(ID, SubjectName);
        }
        return instance;
    }
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
	
	public static Subject convertSubject(Map<String, Object> row) {
		String ID = (String) row.get("ID");
        String subjectName = (String) row.get("SubjectName");
        return getInstance(ID, subjectName);
    }
}