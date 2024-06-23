package gui;
import static client.ChatClient.isexsit;
import logic.Student;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.sqlmessage;
import logic.DepartmentHead;
import logic.Lecturer;
public class LogIn {

    @FXML
    private TextField username_textfield;

    @FXML
    private PasswordField password_textfield;

    @FXML
    private ComboBox<String> infoStationComboBox;

    @FXML
    private Button login_button;
    
    @FXML
    private Button register_button;

    @FXML
    private Button exit_button;
    
    @FXML
    private Text status;
    public static String Username;
    public static List<DepartmentHead> DepartmentHeadinfo = new ArrayList<>();
    public static List<Student> Studentinfo = new ArrayList<>();
    public static List<Lecturer> Lecturerinfo = new ArrayList<>();
    public void initialize() {
    	Studentinfo.clear();
    	Lecturerinfo.clear();
    	DepartmentHeadinfo.clear();
        // Add options to the infoStationComboBox
        infoStationComboBox.getItems().addAll("Student", "Lecturer", "Department_head");
    }
    
    public void exitapp(ActionEvent event) throws IOException {
    	chat.accept("closeconnection");
        chat.accept("closeClient");
        System.exit(1);
    }
    
    public void register(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/New User.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void login(ActionEvent event) throws IOException {
    	chat.accept("openconnection");
        String username = username_textfield.getText();
        String password = password_textfield.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            status.setText("Please enter username and password.");
            return;
        }
        
        String selectedRole = infoStationComboBox.getValue();
        
        if (selectedRole == null) {
            status.setText("Please select a role.");
            return;
        }
        
        String query = "SELECT * FROM " + selectedRole + " WHERE username = ? AND password = ?";
        Object[] params = { username, password };
        sqlmessage message = new sqlmessage("check", query, params);
        chat.accept(message);
        
        if (isexsit ) {
        	Username=username;
            if (selectedRole.equals("Student") ) {
            	String checkquery = "SELECT * FROM Student WHERE Username = ?";
                Object[] checkparams = { Username };
                sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
                chat.accept(checkmessage);
                for (Map<String, Object> row : resultList) {
                	Student student = Student.convertToStudent(row);
                    Studentinfo.add(student);
                }
            	
            	
                // Redirect to StudentMain.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/StudentMain.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else if (selectedRole.equals("Lecturer") ) {
            	String checkquery = "SELECT * FROM lecturer WHERE Username = ?";
                Object[] checkparams = { Username };
                sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
                chat.accept(checkmessage);
                for (Map<String, Object> row : resultList) {
                    Lecturer lecturer = Lecturer.convertToLecturer(row);
                    Lecturerinfo.add(lecturer);
                }
                // Redirect to LecturerMain.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else if (selectedRole.equals("Department_head") ) {
            	String checkquery = "SELECT * FROM department_head WHERE Username = ?";
                Object[] checkparams = { Username };
                sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
                chat.accept(checkmessage);
                for (Map<String, Object> row : resultList) {
                	DepartmentHead departmenthead = DepartmentHead.convertToDepartmentHead(row);
                	DepartmentHeadinfo.add(departmenthead);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/DepartmentHeadMain.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } 
        } else {
        	status.setText("Username or password incorrect.");
        }
    }
}
