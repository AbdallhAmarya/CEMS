package lecturer;
import static client.ClientUI.chat;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.sqlmessage;

public class LockExamController {
	@FXML
    private TextField examIDTextField;

    @FXML
    private Button lockButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;
    @FXML
    private Text status;
    
    public void back(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void LockExam(ActionEvent event) throws IOException{
    	String examid= examIDTextField.getText();
    	if (examid.isEmpty()) {
    		status.setText("Exam Code is required");
    		return;
    	}
    	String query = "UPDATE startexam SET isLocked = ? WHERE ExamID = ?";
    	Object[] params= {1,examid};
    	sqlmessage message=new sqlmessage("edit",query,params);
	    chat.accept(message);
	    /*
	    
	    Thread checkExam = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					//PutTheAnswerIntoMatrix();
					System.out.println(examIDTextField.getText());
				chat.accept("CheckExams;" + examIDTextField.getText());
				chat.accept("ShowExamstatic;"+examIDTextField.getText()+";"+Lecturerinfo.get(0).getID());
				
				if(ExamCheckingCopies) {
					System.out.println("the exams whith ID "+examIDTextField.getText()+" have been checked");
					ExamCheckingCopies=false;
				}
				} catch (InterruptedException e) {e.printStackTrace();}
				
			}
		});
	    checkExam.start();
	     * 
	     */
    }
    public void exitapp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Close the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
