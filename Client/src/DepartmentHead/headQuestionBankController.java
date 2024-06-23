package DepartmentHead;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.DepartmentHeadinfo;
import static gui.LogIn.Username;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.DepartmentHead;
import logic.Lecturer;
import logic.Question;
import logic.sqlmessage;

public class headQuestionBankController implements Initializable{
    @FXML
    private TableView<Question> questionTable;

    @FXML
    private TableColumn<Question, String> idColumn;

    @FXML
    private TableColumn<Question, String> subjectColumn;

    @FXML
    private TableColumn<Question, String> numberColumn;

    @FXML
    private TableColumn<Question, String> lecturerColumn;

    @FXML
    private TableColumn<Question, String> courseColumn;

    @FXML
    private TableColumn<Question, String> textColumn;
    
    @FXML
    private Button back_butt;
    
    private List<Question> questionList = new ArrayList<>();
    private List<Lecturer> lecturer_List = new ArrayList<>();

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), questionTable);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionNum"));
		subjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
	    lecturerColumn.setCellValueFactory(new PropertyValueFactory<>("LecturerID"));
	    courseColumn.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
	    textColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionText"));
    	String department = null;
    	for (DepartmentHead departmentHead : DepartmentHeadinfo) {
    	    if (departmentHead.getUsername().equals(Username)) {
    	        department = departmentHead.getDepartment();
    	        break; // Exit the loop once a match is found
    	    }
    	}
         ObservableList<Question> showquestionList = FXCollections.observableArrayList();
	     String query = "SELECT * FROM lecturer WHERE Department= ?";
	     Object[] param= {department};
	     sqlmessage message=new sqlmessage("get",query,param);
	     chat.accept(message);
	     
	     for (Map<String, Object> row : resultList) {
	    	 Lecturer lecturer = Lecturer.convertToLecturer(row);
             lecturer_List.add(lecturer);
	     }
	     
	     String query1 = "SELECT * FROM question";
	     Object[] param1= {};
	     sqlmessage message1=new sqlmessage("get",query1,param1);
	     chat.accept(message1);

	     for (Map<String, Object> row : resultList) {
	    	 Question question = Question.convertToQuestion(row);
             questionList.add(question);
	     }
	     
	     for(Question question : questionList) {
	    	 for(Lecturer lecturer : lecturer_List) {
	    		 if(question.getLecturerID().equals(lecturer.getID())) {
	    			 showquestionList.add(question);
	    		 }
	    	 }
	     }
	     questionTable.setItems(showquestionList);
 
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
