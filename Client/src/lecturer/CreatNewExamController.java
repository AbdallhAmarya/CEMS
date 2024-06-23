package lecturer;
import static client.ChatClient.isexsit;

import static lecturer.CreatNewExam2Controller.SelectedQuestionPoint;



import static lecturer.CreatNewExam2Controller.SelectedQuestion;
import static gui.LogIn.Lecturerinfo;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Course;
import logic.Exam;
import logic.Subject;
import logic.sqlmessage;
import static lecturer.CreatNewExam2Controller.point;

public class CreatNewExamController {
    @FXML
    private Text id_Subject; 
    @FXML
    private ComboBox<String>  id_CourseID;   
    @FXML
    private TextField id_Duration;    
    @FXML
    private TextArea note_student;  
    @FXML
    private TextArea not_lectturer;  
    @FXML
    private Text id_note; 
    public static Exam Examinfo = Exam.getInstance(null, null, null, null, 0, null, null, null, null, null, null);
    public void initialize() {
    	id_CourseID.getSelectionModel().selectFirst();
    	SelectedQuestion.clear();
    	SelectedQuestionPoint.clear();
    	point = 0;
    	 //id_note.setText("Please fill in all fields.");
    	id_Subject.setText(Lecturerinfo.get(0).getSubjectID());
    	String checkquery = "SELECT SubjectName FROM subject WHERE ID = ?";
        Object[] checkparams = { Lecturerinfo.get(0).getSubjectID() };
        sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
        chat.accept(checkmessage);
        List<Subject> SubjectList = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
        	Subject subject = Subject.convertSubject(row);
        	SubjectList.add(subject);
        }
        id_Subject.setText(SubjectList.get(0).getSubjectName());        
        String checkquery2 = "SELECT * FROM course WHERE ID_Subject = ?";
        Object[] checkparams2 = { Lecturerinfo.get(0).getSubjectID() };
        sqlmessage checkmessage2 = new sqlmessage("get", checkquery2, checkparams2);
        chat.accept(checkmessage2);
        List<Course> SubjectList2 = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
        	Course subject = Course.convertCourse(row);
        	SubjectList2.add(subject);
        }
        for (Course course : SubjectList2) {
        	id_CourseID.getItems().add(course.getCourseName());
        }
        id_CourseID.getSelectionModel().selectFirst();
        
    }
    public void Select_Question(ActionEvent event) throws IOException {
    	 String selectedCourse = id_CourseID.getSelectionModel().getSelectedItem();
    	    if (selectedCourse == null) {
    	        showAlert("Please select a course.");
    	        return;
    	    }
    	String checkquery2 = "SELECT * FROM question WHERE Subject = ?";
    	Object[] checkparams2 = { id_Subject.getText() };
        sqlmessage checkmessage2 = new sqlmessage("check", checkquery2, checkparams2);
        chat.accept(checkmessage2);
        if(!isexsit) {
        	showAlert("There no Question Available");
       	 	return;
        }
        List<Course> SubjectList2 = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
        	Course subject = Course.convertCourse(row);
        	SubjectList2.add(subject);
        }
    	
    	
    	if(id_Duration.getText().isEmpty()){
    		id_note.setText("Please fill in all fields.");
    		return;
    	
    	}
    	Examinfo.setDuration(Integer.parseInt(id_Duration.getText()));
    	Examinfo.setSubject(id_Subject.getText());
    	Examinfo.setCourse(id_CourseID.getValue());
    	if(!not_lectturer.getText().isEmpty())
    		Examinfo.setDescriptionForLecturers(not_lectturer.getText());
    	if(!note_student.getText().isEmpty())
    		Examinfo.setDescriptionForStudents(note_student.getText());
    	//System.out.println(Examinfo.getCourse());
    	System.out.println(id_CourseID.getSelectionModel().getSelectedItem());
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/CreateNewExam2.fxml"));
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	scene.getStylesheets().add(getClass().getResource("/lecturer/CreateExam2.css").toExternalForm()); // Add this line to link the CSS file
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    	
    }
    public void back(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType okayButton = new ButtonType("Okay");
        alert.getButtonTypes().setAll(okayButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okayButton) {
                stage.close();
            }
        });
    }

}
