package logic;

import java.util.Map;

public class DepartmentHead {
    private static DepartmentHead instance;
    private String ID;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String department;

    private DepartmentHead(String ID, String username, String password, String email, String phoneNumber,
            String firstName, String lastName, String department) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public static DepartmentHead getInstance(String ID, String username, String password, String email,
            String phoneNumber, String firstName, String lastName, String department) {
        instance = new DepartmentHead(ID, username, password, email, phoneNumber, firstName, lastName, department);
        return instance;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public static DepartmentHead convertToDepartmentHead(Map<String, Object> row) {
        String ID = (String) row.get("ID");
        String username = (String) row.get("Username");
        String password = (String) row.get("Password");
        String email = (String) row.get("Email");
        String phoneNumber = (String) row.get("PhoneNumber");
        String firstName = (String) row.get("FirstName");
        String lastName = (String) row.get("LastName");
        String department = (String) row.get("department");
        return getInstance(ID, username, password, email, phoneNumber, firstName, lastName, department);
    }
}
