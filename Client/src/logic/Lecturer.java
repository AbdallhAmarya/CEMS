package logic;

import java.util.Map;

public class Lecturer {
    private static Lecturer instance;
    private String ID;
    private String Username;
    private String Password;
    private String Email;
    private String PhoneNumber;
    private String FirstName;
    private String LastName;
    private String SubjectID;
    private String Department;

    private Lecturer(String iD, String username, String password, String email, String phoneNumber, String firstName,
                     String lastName, String subjectID, String department) {
        ID = iD;
        Username = username;
        Password = password;
        Email = email;
        PhoneNumber = phoneNumber;
        FirstName = firstName;
        LastName = lastName;
        SubjectID = subjectID;
        Department = department;
    }

    public static synchronized Lecturer getInstance(String iD, String username, String password, String email,
                                                    String phoneNumber, String firstName, String lastName,
                                                    String subjectID, String department) {
        instance = new Lecturer(iD, username, password, email, phoneNumber, firstName, lastName, subjectID,
                                    department);
        return instance;
    }

    public static Lecturer convertToLecturer(Map<String, Object> row) {
        String id = (String) row.get("ID");
        String username = (String) row.get("Username");
        String password = (String) row.get("Password");
        String email = (String) row.get("Email");
        String phoneNumber = (String) row.get("PhoneNumber");
        String firstName = (String) row.get("FirstName");
        String lastName = (String) row.get("LastName");
        String subjectID = (String) row.get("SubjectID");
        String department = (String) row.get("Department");
        return getInstance(id, username, password, email, phoneNumber, firstName, lastName, subjectID, department);
    }
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getSubjectID() {
		return SubjectID;
	}
	public void setSubjectID(String subjectID) {
		SubjectID = subjectID;
	}
	
	public void setDepartment(String department) {
		Department = department;
	}
	public String getDepartment() {
		return Department;
	}
	
}
