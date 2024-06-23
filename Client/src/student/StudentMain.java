package student;

import static client.ChatClient.isexsit;
import static client.ClientUI.chat;
import static gui.LogIn.Studentinfo;
import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import logic.sqlmessage;


public class StudentMain {
    @FXML
    private Text welcome_text;
    
	private boolean stop=false;
	
	public void initialize() {
	    if (Studentinfo != null && !Studentinfo.isEmpty()) {
	    	String name = "Welcome back " +Studentinfo.get(0).getFirstName() + " " + Studentinfo.get(0).getLastName();
	    	welcome_text.setText(name);
	    }
    	SMSThread smsThread = new SMSThread();
        Thread thread = new Thread(smsThread);
        thread.setDaemon(true); 
        thread.start();
    }
	
	public void move(ActionEvent event) throws IOException{
		stop=true;
		 Button button = (Button) event.getSource();
	     String buttonName = button.getText();
	     String fxmlPath = buttonName + ".fxml";
	        try {
	        	if(buttonName.equals("Exams Result")){
	        		String checkquery = "SELECT * FROM examresult WHERE status = ? AND StudentID = ?";
			        Object[] checkparams = { "1", Studentinfo.get(0).getID()  };
			        sqlmessage checkmessage = new sqlmessage("check", checkquery, checkparams);
			        chat.accept(checkmessage);
			        if(!isexsit) {
			        	showAlert("There no Result Exam");
			        	return;
			        }
	        	}
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
	                    alert.setContentText("You get a new grade");
	                    alert.showAndWait();
	                });
	            }
	            
	            Thread.sleep(5000);
	        }

	        return null;
	    }

	    private boolean haveSMS() {
	    	String query = "SELECT * FROM student_grad WHERE StudentId = ? AND tmp = ? ";
	        Object[] params = {Studentinfo.get(0).getID(),1  };
	        sqlmessage message = new sqlmessage("check", query, params);
	        chat.accept(message);
	        if (isexsit) {
	        	String query2 = "UPDATE student_grad SET tmp = ? WHERE StudentId = ?";
	        	Object[] params2 = {0,Studentinfo.get(0).getID()  };
		        sqlmessage message2 = new sqlmessage("edit", query2, params2);
		        chat.accept(message2);
	            return true;
	        }
	        return false;
	    }
	}
	
	
	
	
}
