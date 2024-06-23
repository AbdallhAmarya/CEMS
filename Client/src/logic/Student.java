package logic;

import java.util.Map;

public class Student {
	private String ID;
	private String Username;
	private String Password;
	private String Email;
	private String PhoneNumber;
	private String FirstName;
	private String LastName;
	private String Course;
	private String Department;
	public Student(String iD, String username, String password, String email, String phoneNumber, String firstName,
			String lastName, String course, String department) {
		super();
		ID = iD;
		Username = username;
		Password = password;
		Email = email;
		PhoneNumber = phoneNumber;
		FirstName = firstName;
		LastName = lastName;
		Course = course;
		Department = department;
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
	public String getCourse() {
		return Course;
	}
	public void setCourse(String course) {
		Course = course;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public static Student convertToStudent(Map<String, Object> row) {
        String id = (String) row.get("ID");
        String username = (String) row.get("Username");
        String password = (String) row.get("Password");
        String email = (String) row.get("Email");
        String phoneNumber = (String) row.get("PhoneNumber");
        String firstName = (String) row.get("FirstName");
        String lastName = (String) row.get("LastName");
        String course = (String) row.get("Course");
        String Department = (String) row.get("Department");
        return new Student(id, username, password, email, phoneNumber, firstName, lastName, course, Department);
    }
}
