package lecturer;

import static client.ClientUI.chat;

import static lecturer.EditExistingQuestionController.id_QuestionExist;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import static client.ChatClient.resultList;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Question;
import logic.sqlmessage;

public class QuestionBankController implements Initializable {

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
    
    public static int  move=0;


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
	     String query = "SELECT * FROM question";
	     Object[] param= {};
	     sqlmessage message=new sqlmessage("get",query,param);
	     chat.accept(message);

	     
	     ObservableList<Question> questionList = FXCollections.observableArrayList();
	        
	     for (Map<String, Object> row : resultList) {
	    	 Question question = Question.convertToQuestion(row);
             questionList.add(question);
             
	        }
	     
	     questionTable.setItems(questionList);
	     
	     questionTable.setOnMouseClicked(event -> {
	    	    if (event.getClickCount() == 1) { // Detect single click
	    	        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
	    	        if (selectedQuestion != null) {
	    	            String questionNumber = selectedQuestion.getQuestionNum();
	    	            id_QuestionExist=questionNumber;
	    	            move=1;
	    	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/EditExistingQuestion2.fxml"));
	    		        Parent root = null;
						try {
							root = loader.load();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    		        Scene scene = new Scene(root);
	    		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    		        stage.setScene(scene);
	    		        stage.show();
	    	        }
	    	    }
	    	});
	    }
	
	 public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Exam Question Bank.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    }
	 
	 public void addnewquestion(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Create New Question.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    }
		
	
}
