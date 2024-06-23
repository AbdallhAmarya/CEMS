package DepartmentHead;

import static client.ChatClient.isexsit;
import static client.ClientUI.chat;

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
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.sqlmessage;
import static gui.LogIn.DepartmentHeadinfo;

public class DepartmentHeadMain {
	
    @FXML
    private Button Statistic_button;
    
    @FXML
    private Button Q_B_button;
    
    @FXML
    private Button examstat_button;
    
    @FXML
    private Text welcome_text;
    
    private boolean stop =false;
    public static int flag=0;
    
    public void initialize() {
        if (DepartmentHeadinfo != null && !DepartmentHeadinfo.isEmpty()) {
            String name = "Welcome back " +DepartmentHeadinfo.get(0).getFirstName() + " " + DepartmentHeadinfo.get(0).getLastName();
            welcome_text.setText(name);
        }
    	SMSThread smsThread = new SMSThread();
        Thread thread = new Thread(smsThread);
        thread.setDaemon(true); 
        thread.start();
    }
	
	  public void goTosms(ActionEvent event) throws IOException {
		    stop=true;
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/sms.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
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
		 
		 public void ss(ActionEvent event) throws IOException{
			    stop=true;
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Student grade.fxml"));
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.setScene(scene);
		        stage.show();
		        // Close the current window
		        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        currentStage.close();
		 }
		 
		  public void Statistic(ActionEvent event) throws IOException {
			    stop=true;
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Show Report.fxml"));
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.show();
		   }
		  
		  public void showquestion(ActionEvent event) throws IOException {
			    stop=true;
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/head Question Bank.fxml"));
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.show();
		   }
		  
		  public void gotoExamtatistic(ActionEvent event) throws IOException {
			  stop=true;
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/head exam stat.fxml"));
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.show();
		   }
		  public void goToExamBank(ActionEvent event) throws IOException {
			    stop=true;
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Exam Bank.fxml"));
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.show();
		    }
		  
		  
		  public class SMSThread extends Task<Void> {
			    @Override
			    protected Void call() throws Exception {
			        while (true) {
			            if (stop) {
			                break;
			            }
			            if(flag==0) {
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
			            }
			            Thread.sleep(5000);
			        }

			        return null;
			    }

			    private boolean haveSMS() {
			    	String query = "SELECT * FROM sms WHERE Department = ? AND response IS NULL";
			        Object[] params = { DepartmentHeadinfo.get(0).getDepartment() };
			        sqlmessage message = new sqlmessage("check", query, params);
			        chat.accept(message);
			        if (isexsit) {
			        	flag=1;
			            return true;
			        }
			        return false;
			    }
			}
		  
		  public void mani(ActionEvent event) throws IOException {
			  stop=true;
			  FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/static report for 2 exam.fxml"));
		      Parent root = loader.load();
		      Scene scene = new Scene(root);
		      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		      stage.setScene(scene);
		      stage.show();
		  }
}
