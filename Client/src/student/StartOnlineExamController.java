package student;
import static gui.LogIn.Studentinfo;
import static student.StartOnlineExam2Controller.point;
import static client.ChatClient.isexsit;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.sqlmessage;
import logic.CurrentExam;
import logic.Exam;
import logic.StartExam;

public class StartOnlineExamController implements Initializable {
	public static List<StartExam> startexam = new ArrayList<>();
	public static List<Exam> exam = new ArrayList<>();
	@FXML
	private TextField id_ExamCode;
	public static String examcode;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		exam.clear();
		startexam.clear();
	}
	
	public void Start(ActionEvent event) throws IOException{
		if(id_ExamCode.getText().isEmpty()) {
			return;
		}
		examcode=id_ExamCode.getText();
		point = 1;
		String checkquery = "SELECT * FROM startexam WHERE ExamCode = ? AND IsLocked = ?";
        Object[] checkparams = { id_ExamCode.getText() ,0};
        sqlmessage checkmessage = new sqlmessage("check", checkquery, checkparams);
        chat.accept(checkmessage);
        if(!isexsit) {
        	showAlert("There No Exam available");
        	return;
        }
        
        
        String checkquery2 = "SELECT * FROM startexam WHERE ExamCode = ?";
        Object[] checkparams2 = { id_ExamCode.getText() };
        sqlmessage checkmessage2 = new sqlmessage("get", checkquery2, checkparams2);
        chat.accept(checkmessage2);
        for (Map<String, Object> row : resultList) {
        	StartExam exm = StartExam.convertToStartExam(row);
        	startexam.add(exm);
        }
        
        
        String checkquery7 = "SELECT * FROM examresult WHERE startexamNum = ? AND StudentID = ?";
        Object[] checkparams7 = { startexam.get(0).getStartnum(),Studentinfo.get(0).getID()};
        sqlmessage checkmessage7 = new sqlmessage("check", checkquery7, checkparams7);
        chat.accept(checkmessage7);
        if(isexsit) {
        	showAlert("You've already completed the exam!!");
        	return;
        }
        StartExam examid = startexam.get(0);
        System.out.println("the code: "+startexam.get(0).getStartnum());
        exam.clear();
        // System.out.println(examid.getExamID());
        String checkquery3 = "SELECT * FROM exam WHERE ID = ?";
        Object[] checkparams3 = { examid.getExamID() };
        sqlmessage checkmessage3 = new sqlmessage("get", checkquery3, checkparams3);
        chat.accept(checkmessage3);
        for (Map<String, Object> row : resultList) {
            Exam exm = Exam.convertToExam(row);
            exam.add(exm);
        } 
        System.out.println(exam.get(0));
        count();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/StartOnlineExam2.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Close the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
 
        
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
	public void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/StudentMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Close the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
	
	private void count() {
		String query = "SELECT * FROM currentexam WHERE examid = ?";
		Object[] pram = {startexam.get(0).getExamID()};
		 sqlmessage message = new sqlmessage("get",query , pram);
		 chat.accept(message);
		 CurrentExam ex=CurrentExam.convertToCurrentExam(resultList.get(0));
         String query2 = "UPDATE currentexam SET count = ? WHERE examid = ?";
         Object[] pram2 = {ex.getCount()+1,startexam.get(0).getExamID()};
		 sqlmessage message2 = new sqlmessage("edit",query2 , pram2);
		 chat.accept(message2);
	}
	
}
