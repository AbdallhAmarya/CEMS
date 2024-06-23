package lecturer;
import static lecturer.ExamReportController.Examcode;

import static lecturer.ExamReportController.examqeusLecturer;
import static lecturer.ExamReportController.Examresult;
import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;
import java.io.IOException;
import static client.ChatClient.isexsit;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.sqlmessage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class LecturerMain {
    @FXML
    private Text welcome_text;
    
	private boolean stop=false;
	
	public void initialize() {
	    if (Lecturerinfo != null && !Lecturerinfo.isEmpty()) {
	    	String name = "Welcome back " +Lecturerinfo.get(0).getFirstName() + " " + Lecturerinfo.get(0).getLastName();
	    	welcome_text.setText(name);
	    }
		Examcode="";
		SMSThread smsThread = new SMSThread();
        Thread thread = new Thread(smsThread);
        thread.setDaemon(true); 
        thread.start();
        Examresult.clear();
    	examqeusLecturer.clear();
		
        //chat.accept("ShowExamstatic;021148;1111");
       
    }
	 public void move(ActionEvent event) throws IOException{
		 stop=true;
		 Button button = (Button) event.getSource();
	     String buttonName = button.getText();
	     String fxmlPath = buttonName + ".fxml";
	     if(buttonName.equals("Start Exam")){
	    	 String checkquery = "SELECT * FROM startexam WHERE Subject = ?";
	         Object[] checkparams = { Lecturerinfo.get(0).getSubjectID() };
	         sqlmessage checkmessage = new sqlmessage("check", checkquery, checkparams);
	         chat.accept(checkmessage);
	         
	         if(isexsit) {
	        	 showAlert("You Already started Exam");
	        	 return;
	         }
	     }
	     if(buttonName.equals("Change Exam duration")){
	    	 
	     }
	        try {
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.setScene(scene);
		        stage.show();
		        // Close the current window
		        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        currentStage.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }
	 
	 public void logout(ActionEvent event) throws IOException{
		 stop=true;
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginClient.fxml"));
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
	 
	 public void ExamQuestionBank(ActionEvent event) throws IOException{
		    stop=true;
         	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Exam Question Bank.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	        // Close the current window
	        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        currentStage.close();
	 }
	 
	 public class SMSThread extends Task<Void> {

		    @Override
		    protected Void call() throws Exception {
		        while (true) {
		            if (stop) {
		                break;
		            }

		            boolean hasSMS = haveSMS();

		            if (hasSMS) {
		                Platform.runLater(() -> {
		                    Alert alert = new Alert(AlertType.WARNING);
		                    alert.setTitle("SMS");
		                    alert.setHeaderText(null);
		                    alert.setContentText("You have a SMS");
		                    alert.showAndWait();
		                });
		            }

		            Thread.sleep(5000);
		        }

		        return null;
		    }

		    private boolean haveSMS() {
		    	String query = "SELECT * FROM seesms WHERE Department = ? AND response IS NOT NULL AND lecturer= ? ";
		        Object[] params = { Lecturerinfo.get(0).getDepartment(),Lecturerinfo.get(0).getFirstName() };
		        sqlmessage message = new sqlmessage("check", query, params);
		        chat.accept(message);
		        if (isexsit) {
			         String deleteQuery = "DELETE FROM seesms WHERE Department = ? AND response IS NOT NULL AND lecturer= ? ";
			         Object[] pram1= {Lecturerinfo.get(0).getDepartment(),Lecturerinfo.get(0).getFirstName()};
			         sqlmessage message3 = new sqlmessage("delete", deleteQuery, pram1);
			         chat.accept(message3);
		            return true;
		        }
		        return false;
		    }
		    
		  
		}
	 
	 
	 
}
