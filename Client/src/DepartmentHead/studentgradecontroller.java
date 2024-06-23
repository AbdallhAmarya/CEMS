package DepartmentHead;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.DepartmentHeadinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.examresult;
import logic.Course;
import logic.sqlmessage;

public class studentgradecontroller {
	
    @FXML
    private Button show_button;
    
    @FXML
    private Button back_button;
    
    @FXML
    private Text error_text;
    
	@FXML
    private TextField S_ID_textfield;
	
    @FXML
    private TableView<examresult> examTable;

    @FXML
    private TableColumn<examresult, String> exID;
    
    @FXML
    private TableColumn<examresult, String> Grade;
    
    @FXML
    private TableColumn<examresult, String> courseName;
    
    private List<Course> course_list = new ArrayList<>();
    private List<examresult> examres_list = new ArrayList<>();
    private List<examresult> examres2_list = new ArrayList<>();
    private List<String> stID_list = new ArrayList<>();
    private ObservableList<examresult> gradetable = FXCollections.observableArrayList();

    public void initialize() {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), examTable);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    	exID.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
    	Grade.setCellValueFactory(new PropertyValueFactory<>("ExamResult"));
    	courseName.setCellValueFactory(new PropertyValueFactory<>("Course"));
        String query = "SELECT * FROM course WHERE Department= ?";
        Object[] pram = {DepartmentHeadinfo.get(0).getDepartment()};
        sqlmessage message = new sqlmessage("get", query, pram);
        chat.accept(message);
        
        for (Map<String, Object> row : resultList) {
            Course course = Course.convertCourse(row);
            course_list.add(course);
        }
        
        String query1 = "SELECT * FROM examresult";
        Object[] pram1 = {};
        sqlmessage message1 = new sqlmessage("get", query1, pram1);
        chat.accept(message1);

        
        for (Map<String, Object> row : resultList) {
            examresult ex = examresult.convertToExamResult(row);
            examres_list.add(ex);
        }
        
        for(examresult ex : examres_list) {
        	for(Course course : course_list) {
        		if(ex.getCourse().equals(course.getCourseName())) {
        			examres2_list.add(ex);
        			stID_list.add(ex.getStudentID());
        		}
        	}
        }
    }
    
    public void show(ActionEvent event) throws IOException {
    	gradetable.clear();
    	String sID=S_ID_textfield.getText();
		error_text.setText("");
		if (S_ID_textfield.getText().isEmpty()) {
    		error_text.setText("Please fill textfield.");
    	}
		else if(!stID_list.contains(S_ID_textfield.getText())) {
    		error_text.setText("This student doesn't have grades.");
    	}
    	else {            
            for (examresult ex :examres2_list) {
            	if(ex.getStudentID().equals(sID))
            		gradetable.add(ex);
            }
            examTable.setItems(gradetable);
    	}
    }
    
    public void back(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/DepartmentHeadMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
