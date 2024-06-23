package student;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.CurrentExam;
import logic.myfile;
import logic.sms;
import logic.sqlmessage;
import static client.ChatClient.isexsit;
import static client.ChatClient.resultList;
import static gui.LogIn.Studentinfo;
import static client.ChatClient.Myfile;
import static client.ClientUI.chat;
import static student.StartManualExamController.exam_id;
import static student.StartManualExamController.startexam;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


public class StartManualExamController2 {
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Text course_name;
    @FXML
    private Text time;
    @FXML
    private TextField file_path;
    @FXML
    private Text id;
    @FXML
    private Button back; 
    @FXML
    private Button submit; 
    
    private int examDuration=0;
    private boolean examLockStatus= false;
    private int previousDuration=startexam.get(0).getDuration();
    private boolean havenewduration=false;
    private boolean submitdone=false;
    private boolean check=false;
    private Thread thread ;
    
    public void initialize() {
        course_name.setText(startexam.get(0).getCourse());
        id.setText(exam_id);
        LockStatusThread lockStatusThread = new LockStatusThread();
        thread = new Thread(lockStatusThread);
        thread.setDaemon(true); // Set the thread as a daemon thread to automatically terminate when the application exits
        thread.start();
    }
    
    @FXML
    private void upload() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(anchorpane.getScene().getWindow());
        if (file != null) {
            file_path.setText(file.getAbsolutePath());
        }
    }
    
    @FXML
    private void download() {
        myfile file = new myfile("get", null, "exam/" + exam_id, null, null);
        chat.accept(file);
        byte[] fileData = Myfile.getFileData();
        
        if (fileData != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.setInitialFileName(exam_id + ".word");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Documents (*.word)", "*.word");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showSaveDialog(anchorpane.getScene().getWindow());
            
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(selectedFile);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();
                    
                    Platform.runLater(() -> {
                        Alert successAlert = new Alert(AlertType.INFORMATION);
                        successAlert.setTitle("File Saved");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("File saved successfully at: " + filePath);
                        successAlert.showAndWait();
                    });
                    
                    TimeConverterThread ThreadTime = new TimeConverterThread(startexam.get(0).getDuration(), time,submit);
                    ThreadTime.start();
                    
                    Thread ThreadLocked = new Thread(() -> {
                    });
                    ThreadLocked.start();
                    back.setVisible(false);
                    Desktop.getDesktop().open(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("File Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to create the file.");
                    alert.showAndWait();
                }
            }
        } else {
            Alert retrievalErrorAlert = new Alert(AlertType.ERROR);
            retrievalErrorAlert.setTitle("Error");
            retrievalErrorAlert.setHeaderText(null);
            retrievalErrorAlert.setContentText("Failed to retrieve the file.");
            retrievalErrorAlert.showAndWait();
        }
    }
    
    @FXML
    private void submit() {
        String filePath = file_path.getText();
        
        if (filePath.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("File Path Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a file.");
            alert.showAndWait();
            return;
        }
        
        byte[] fileData = null;
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            fileData = new byte[(int) file.length()];
            fileInputStream.read(fileData);
            fileInputStream.close();
            back.setVisible(true);
            submit.setVisible(false);
            submitdone=true;
            count2();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("File Read Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to read file data.");
            alert.showAndWait();           
            return;
        }
        
        myfile fileObject = new myfile("save", Studentinfo.get(0).getID(), exam_id, "student", fileData);
        chat.accept(fileObject);
        
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Exam Submission");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Exam with ID " + exam_id + " has been successfully submitted.");
        successAlert.showAndWait();
        return;
    }
    
    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/StudentMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    public class TimeConverterThread extends Thread {
        private int minutes;
        private Text timeRemainingText;
        private boolean addedOneMinute;
        private Button submitButton;
        private boolean addDurationOneTime;

        public TimeConverterThread(int minutes, Text timeRemainingText, Button submitButton) {
            this.minutes = minutes;
            this.timeRemainingText = timeRemainingText;
            this.addedOneMinute = false;
            this.submitButton = submitButton;
            this.addDurationOneTime=false;
        }

        @Override
        public void run() {
            int totalSeconds = minutes * 60;

            while (totalSeconds >= 0) {
                int remainingMinutes = totalSeconds / 60;
                int remainingSeconds = totalSeconds % 60;

                Platform.runLater(() -> {
                    String timeFormat = String.format("%02d:%02d", remainingMinutes, remainingSeconds);
                    timeRemainingText.setText(timeFormat);
                });

                try {
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (submitdone) {
                	break;
                }
                if(havenewduration && !addDurationOneTime) {
                	totalSeconds=totalSeconds+examDuration* 60;
                	addDurationOneTime=true;
                }
                if(examLockStatus) {
                	 Platform.runLater(() -> {
                         timeRemainingText.setText("00:00");
                     });
                	 back.setVisible(true);
                	 break;
                }

                if (remainingMinutes == 0 && remainingSeconds == 0 && !addedOneMinute) {
                    // Add 1 minute when time reaches 00:00 for the first time
                    totalSeconds += 60;
                    addedOneMinute = true;
                } else if (remainingMinutes == 0 && remainingSeconds == 0 && addedOneMinute) {
                    // Show alert and hide submitButton when time reaches 00:00 for the second time
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Time Over");
                        alert.setHeaderText(null);
                        alert.setContentText("The time is over. You can no longer submit.");
                        alert.showAndWait();
                        submitdone=true;
                        submitButton.setVisible(false);
                        back.setVisible(true);
                    });
                    count2();
                    break;
                } else {
                    totalSeconds--;
                }
            }

            Platform.runLater(() -> {
                timeRemainingText.setText("00:00");
            });
        }
    }
    
    
    public class LockStatusThread extends Task<Void> {


    	 @Override
    	    protected Void call() throws Exception {
    	        while (true) {
    	        	 if (submitdone) {
    	                	break;
    	                }
    	            boolean isLocked = isExamLocked();

    	            if (isLocked) {
    	                Platform.runLater(() -> {
    	                    // Display a message or take appropriate action when the exam is locked
    	                    Alert alert = new Alert(AlertType.WARNING);
    	                    alert.setTitle("Exam Locked");
    	                    alert.setHeaderText(null);
    	                    alert.setContentText("The exam has been locked. You can no longer submit.");
    	                    alert.showAndWait();
    	                    submitdone=true;
    	                    submit.setVisible(false);
    	                   
    	                });
    	                break;
    	            }
    	            

    	           if (!check) {
    	        	   int examDuration1 = getExamDuration();
       	            if ( previousDuration+examDuration1 != previousDuration) {
       	                Platform.runLater(() -> {
       	                    // Display a message or take appropriate action when the duration has changed
       	                    Alert alert = new Alert(AlertType.WARNING);
       	                    alert.setTitle("Exam Duration Updated");
       	                    alert.setHeaderText(null);
       	                    alert.setContentText("The duration of the exam has been updated. Please check the new duration.");
       	                    alert.showAndWait();

       	                });
       	                check=true;
       	            }
    	           }

    	            
    	            Thread.sleep(5000); // Check every 5 seconds (adjust as needed)
    	        }
				return null;
    	    }


        private boolean isExamLocked() {
        	String query = "SELECT * FROM startexam WHERE ExamID = ? AND IsLocked = ?";
        	Object [] pram= {exam_id,1};
        	sqlmessage mesaage=new sqlmessage("check",query,pram);
        	chat.accept(mesaage);
        	if (isexsit) {
        		examLockStatus=true;
        	}
            return examLockStatus;
        }

        private int getExamDuration() {
        	String query = "SELECT * FROM sms WHERE exam_id = ? AND response =? ";
        	Object [] pram= {exam_id,"Accept"};
        	sqlmessage mesaage=new sqlmessage("check",query,pram);
        	chat.accept(mesaage);
        	if (isexsit) {
        		sqlmessage mesaage2=new sqlmessage("get",query,pram);
            	chat.accept(mesaage2);
            	 for (Map<String, Object> row : resultList) {
            		 sms sms1 = sms.convertToSMS(row);
            		 examDuration=sms1.getNewDuration()-previousDuration;
            		 
            	 }
            	 havenewduration=true;
            	
        	}
            
            return examDuration;
        }
    }
    
	private void count2() {
    	 String query = "SELECT * FROM currentexam WHERE examid = ?";
		 Object[] pram = {exam_id};
		 sqlmessage message = new sqlmessage("get",query , pram);
		 chat.accept(message);
		 CurrentExam ex=CurrentExam.convertToCurrentExam(resultList.get(0));
		 if((ex.getCount()-1)==0) {
			 String deletequery = "DELETE FROM currentexam WHERE examid = ?";
			 Object[] deletepram = {exam_id};
			 sqlmessage deletemessage = new sqlmessage("delete",deletequery , deletepram);
			 chat.accept(deletemessage);
			 String editquery = "UPDATE startexam SET IsLocked = ? WHERE examid = ?";
			 Object[] editpram = {1,exam_id};
			 sqlmessage editmessage = new sqlmessage("edit",editquery , editpram);
			 chat.accept(editmessage);
			 Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						chat.accept("CheckExams;" + startexam.get(0).getExamID());
						chat.accept("ShowExamstatic;" + startexam.get(0).getExamID()+";"+startexam.get(0).getLecturerName());
					}
				});
				 t.start();
	         //thread.stop();
		 }
		 else {
         String query2 = "UPDATE currentexam SET count = ? WHERE examid = ?";
         Object[] pram2 = {ex.getCount()-1,exam_id};
		 sqlmessage message2 = new sqlmessage("edit",query2 , pram2);
		 chat.accept(message2);
		 }
    }




}
