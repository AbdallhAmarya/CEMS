package lecturer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static lecturer.ExamBankController.exam;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Exam;
import logic.ExamQuestion;
import logic.Question;
import logic.sqlmessage;

public class ExamBank2Controller {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<ExamQuestion> examTable;

    @FXML
    private TableColumn<Question, String> idColumn;

    @FXML
    private TableColumn<Question, String>LecturerColumn;

    @FXML
    private TableColumn<Question, String> qu_text_co;

    @FXML
    private TableColumn<Question, String> ans1_co;

    @FXML
    private TableColumn<Question, String> ans2_co;

    @FXML
    private TableColumn<Question, String> ans3_co;

    @FXML
    private TableColumn<Question, String> corect_ans_co;

    @FXML
    private TableColumn<ExamQuestion, String> point_co;

   

    @FXML
    private Text exam_id_text;

    @FXML
    private Text course_text1;
    
    private List<Exam> examList2 = new ArrayList<>();
    private ObservableList<ExamQuestion> examQuestionList = FXCollections.observableArrayList();
    public void initialize() {
        // Set up the table columns
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), examTable);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionNum"));
        LecturerColumn.setCellValueFactory(new PropertyValueFactory<>("LecturerID"));
        qu_text_co.setCellValueFactory(new PropertyValueFactory<>("QuestionText"));
        ans1_co.setCellValueFactory(new PropertyValueFactory<>("Answer1"));
        ans2_co.setCellValueFactory(new PropertyValueFactory<>("Answer2"));
        ans3_co.setCellValueFactory(new PropertyValueFactory<>("Answer3"));
        corect_ans_co.setCellValueFactory(new PropertyValueFactory<>("AnswerCorrect"));
        point_co.setCellValueFactory(new PropertyValueFactory<>("Point"));
        exam_id_text.setText(exam.getID());
        course_text1.setText(exam.getCourse());
        String query = "SELECT * FROM exam WHERE ID = ?";
        Object[] pram = {exam.getID()};
        sqlmessage message = new sqlmessage("get", query, pram);
        chat.accept(message);

        for (Map<String, Object> row : resultList) {
            Exam exam = Exam.convertToExam(row);
            examList2.add(exam);
        }
        Exam exam1=examList2.get(0);
        String[] parts=exam1.getQuestion().split(";");
        String[] parts2=exam1.getPointPerQuestion().split(";");
        int j=1;
        for (int i = 0; i < parts.length; i++) {
        	String query2 = "SELECT * FROM question WHERE QuestionNumber = ?";
            Object[] pram2 = {parts[i]};
            sqlmessage message2 = new sqlmessage("get", query2, pram2);
            chat.accept(message2);

            for (Map<String, Object> row : resultList) {
                Question question=Question.convertToQuestion(row);
                
                ExamQuestion ex_qu= ExamQuestion.getInstance(question,parts2[i+j]);
                j++;
                examQuestionList.add(ex_qu);
            }
        	
        }
        examTable.setItems(examQuestionList);
        
    }
	 public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Exam Bank.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	 }

    
}

