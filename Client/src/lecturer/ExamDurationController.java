package lecturer;
import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static client.ChatClient.sqldone;
import static client.ChatClient.isexsit;
import static client.ChatClient.resultList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Lecturer;
import logic.StartExam;
import logic.sqlmessage;

public class ExamDurationController {
    @FXML
    private ComboBox<String> exam_id;
    @FXML
    private TextField new_duration_field;
    @FXML
    private TextArea reason_field;
    @FXML
    private Text status;
    @FXML
    private Text course_ftext;
    @FXML
    private Text old_du_text;

    public void initialize() {
        Lecturer lecturer = Lecturerinfo.get(0);
        String checkquery = "SELECT * FROM startexam WHERE LecturerName = ?";
        Object[] checkparams = { lecturer.getID() };
        sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
        chat.accept(checkmessage);
        List<StartExam> examList = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
            StartExam exam = StartExam.convertToStartExam(row);
            examList.add(exam);
            exam_id.getItems().add(exam.getExamID());
        }
        exam_id.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedExamID = (String) newValue;
                for (StartExam exam : examList) {
                    if (exam.getExamID().equals(selectedExamID)) {
                        course_ftext.setText(exam.getCourse());
                        old_du_text.setText(String.valueOf(exam.getDuration()));
                        break;
                    }
                }
            }
        });
    }

    public void back(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param event
     * @throws IOException
     */
    public void send(ActionEvent event) throws IOException {
    	String examId = (String) exam_id.getValue();
        String newDuration = new_duration_field.getText();
        String reason = reason_field.getText();

        if (examId.isEmpty() || newDuration.isEmpty() || reason.isEmpty()) {
            status.setText("Fields cannot be empty");
            return;
        }
        String query = "SELECT * FROM exam WHERE ID = ?";
    	Object[] params= {examId};
    	sqlmessage message=new sqlmessage("check",query,params);
	    chat.accept(message);
	    if(!isexsit) {
	    	status.setText("The exam not exsist");
	    	return;
	    }
	    
	    String query3 = "SELECT * FROM sms WHERE exam_id = ?";
    	Object[] params3= {examId};
    	sqlmessage message3=new sqlmessage("check",query3,params3);
	    chat.accept(message3);
	    if(isexsit) {
	    	status.setText("This test request has already been sent");
	    	return;
	    }
	    
	    
	    String query2 = "INSERT INTO sms (department, lecturer, exam_id, `new duration`, reason) VALUES (?, ?, ?, ?, ?)";
    	Object[] params2= {Lecturerinfo.get(0).getDepartment(),Lecturerinfo.get(0).getFirstName(),examId,newDuration,reason};
    	sqlmessage message2=new sqlmessage("save",query2,params2);
	    chat.accept(message2);
	    if(sqldone) {
	    	status.setText("sent succesfully");

	    }
	    else {
	    	status.setText("Failed to send");
	    }
    }
}

