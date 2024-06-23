package student;

import javafx.event.ActionEvent;
import static gui.LogIn.Studentinfo;
import static student.StartOnlineExamController.startexam;
import logic.examresult;
import logic.sms;
import logic.CurrentExam;
import logic.CurrentExamSelectedQuestionStudent;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static student.StartOnlineExamController.exam;
import static client.ChatClient.isexsit;
import static client.ChatClient.resultList;
import static client.ChatClient.sqldone;
import static client.ClientUI.chat;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import logic.Question;
import logic.sqlmessage;
import logic.Exam;
public class StartOnlineExam2Controller implements Initializable {
    @FXML
    private TableView<Question> id_TableView;
    @FXML
    private TableColumn<Question, String> id_IDCol;
    @FXML
    private TableColumn<Question, String> id_QuestionCol;
    @FXML
    private TableColumn<Question, Void> id_AnswerCol;
    @FXML
    private Text id_TotalPoint;
    @FXML
    private Text id_notecheckid;
    @FXML
    private TextField id_Studentid;
    @FXML
    private Button id_submet;
    @FXML
    private TextArea id_Notes;
    @FXML
    private Button id_checkid;
    @FXML
    private Label label_id;
    @FXML
    private Text id_timeRemaining;
    private boolean answersShuffled = false;
    private boolean SubmitDone=false;
    private boolean tmp=false;
    private Thread thread;
    private Map<String, Question> Shuffel = new HashMap<>();
    private Map<String, CurrentExamSelectedQuestionStudent> examselect = new HashMap<>();
    private List<String> questionpoint = new ArrayList<>();
    private List<Question> questionList = new ArrayList<>();
    private List<String> questpoinNotselect = new ArrayList<>();
    private List<Exam> Examentery = new ArrayList<>();

    public static int point = 0;
    private int flag=0;
    private int TotalQuestion = 0;
    public static List<String[]> selectedQuestionArray = new ArrayList<>();
    private int examDuration=0;
    private boolean examLockStatus= false;
    private int previousDuration=startexam.get(0).getDuration();
    private boolean havenewduration=false;
    private long startTime;
    private long endTime;
    private String DurationTake;
    private String FormSubmission;
    private boolean Submitform=false;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), id_TableView);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    	selectedQuestionArray.clear();
    	questionpoint.clear();
    	questionList.clear();
    	id_Studentid.setText("");
    	point = 0;
    	String checkquery9 = "SELECT DescriptionForStudents FROM exam WHERE ID = ?";
        Object[] checkparams9 = { startexam.get(0).getExamID()};
        sqlmessage checkmessage9 = new sqlmessage("get", checkquery9, checkparams9);
        chat.accept(checkmessage9);
        for (Map<String, Object> row : resultList) {
        	Exam exam = Exam.convertToExam(row);
        	Examentery.add(exam);
        }
    	id_Notes.setText(Examentery.get(0).getDescriptionForStudents());
    	id_Notes.setEditable(false);
    	id_Notes.setStyle("-fx-text-fill: black;");
        retrieveQuestions();
        id_IDCol.setCellValueFactory(new PropertyValueFactory<>("questionNum"));
        id_QuestionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        id_AnswerCol.setCellFactory(createButtonCellFactory());
        id_TableView.setItems(FXCollections.observableArrayList(questionList));
        for (Question question : questionList) {
        	CurrentExamSelectedQuestionStudent select1 = new CurrentExamSelectedQuestionStudent(question.getQuestionNum(),question.getQuestionText(),"No Answer",
        			question.getAnswerCorrect(),"");
        	examselect.put(question.getQuestionNum(),select1 );
        	questpoinNotselect.add(question.getQuestionNum());
        }
        System.out.println("the nume of selected"+Integer.toString(questionList.size()-examselect.size()));
        int i=0;
        for (String str : questionpoint) {
        	examselect.get(questpoinNotselect.get(i)).setPoint(str);
        	i++;
        }
        LockStatusThread lockStatusThread = new LockStatusThread();
        thread = new Thread( lockStatusThread);
        thread.setDaemon(true); // Set the thread as a daemon thread to automatically terminate when the application exits
        thread.start();
    }
  
    public class LockStatusThread extends Task<Void> {
   	 @Override
   	    protected Void call() throws Exception {
   	        while (true) {
   	        	if(SubmitDone) {
                	break;
                }
   	        	if(Submitform)
   	        		break;
   	            // Check the lock status of the exam in the database
   	            boolean isLocked = isExamLocked();
   	            if (isLocked) {
   	                Platform.runLater(() -> {
   	                    // Display a message or take appropriate action when the exam is locked
   	                    Alert alert = new Alert(AlertType.WARNING);
   	                    alert.setTitle("Exam Locked");
   	                    alert.setHeaderText(null);
   	                    alert.setContentText("The exam has been locked. You can no longer submit.");
   	                    alert.showAndWait();  	                
   	                    //submit.setVisible(false);
   	                });
   	            }
   	            // Check if the duration has been updated in the database
   	            if(!tmp) {
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
   	   	                tmp=true;
   	   	            }
   	            }   
   	            Thread.sleep(5000); // Check every 5 seconds (adjust as needed)
   	        }
				return null;
   	    }


   	private boolean isExamLocked() {
       	String query = "SELECT * FROM startexam WHERE ExamID = ? AND IsLocked = ? AND ExamCode = ?";
       	Object [] pram= {startexam.get(0).getExamID(),1,startexam.get(0).getExamCode()};
       	sqlmessage mesaage=new sqlmessage("check",query,pram);
       	chat.accept(mesaage);
       	if (isexsit) {
       		examLockStatus=true;
       	}
           return examLockStatus;
       }

       private int getExamDuration() {
       	String query = "SELECT * FROM sms WHERE exam_id = ? AND response =? ";
       	Object [] pram= {startexam.get(0).getExamID(),"Accept"};
       	sqlmessage mesaage=new sqlmessage("check",query,pram);
       	chat.accept(mesaage);
       	if (isexsit) {
       		sqlmessage mesaage2=new sqlmessage("get",query,pram);
           	chat.accept(mesaage2);
           	 for (Map<String, Object> row : resultList) {
           		 sms sms1 = sms.convertToSMS(row);
           		 examDuration=sms1.getNewDuration()-previousDuration;
           		 System.out.println("the new duration "+sms1.getNewDuration());
           	 }
           	 havenewduration=true;
           	
       	}
           return examDuration;
       }
   }
    
    private void retrieveQuestions() {
        questionpoint.clear();
        questionList.clear();
        String[] Questionid = exam.get(0).getPointPerQuestion().split(";");
        for (String string : Questionid) {
			System.out.println(string);
		}
        String checkquery2 = "SELECT * FROM question WHERE QuestionNumber = ?";
        for (int i = 0; i < Questionid.length; i += 2) {
            Object[] checkparams2 = { Questionid[i] };
            sqlmessage checkmessage2 = new sqlmessage("get", checkquery2, checkparams2);
            chat.accept(checkmessage2);
            for (Map<String, Object> row : resultList) {
                Question question = Question.convertToQuestion(row);
                questionList.add(question);
            }
            TotalQuestion++;
            questionpoint.add(Questionid[i + 1]);
        }
        for (Question string : questionList) {
        	System.out.println(string);
		}
    }

    private Callback<TableColumn<Question, Void>, TableCell<Question, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Question, Void>, TableCell<Question, Void>>() {
            @Override
            public TableCell<Question, Void> call(TableColumn<Question, Void> param) {
                return new TableCell<Question, Void>() {
                    private final RadioButton radioButton1 = new RadioButton("");
                    private final RadioButton radioButton2 = new RadioButton("");
                    private final RadioButton radioButton3 = new RadioButton("");
                    private final RadioButton radioButton4 = new RadioButton("");
                    private final ToggleGroup toggleGroup = new ToggleGroup();
                    private final VBox container = new VBox();
                    private final Text questionPointText = new Text();
                    {
                       
                        //radioButton1.setSelected(true); // Set the first radio button as selected
                        container.getChildren().addAll(questionPointText, radioButton1, radioButton2, radioButton3, radioButton4);
                        container.setSpacing(5);
                        radioButton1.setToggleGroup(toggleGroup);
                        radioButton2.setToggleGroup(toggleGroup);
                        radioButton3.setToggleGroup(toggleGroup);
                        radioButton4.setToggleGroup(toggleGroup);

                        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue != null) {
                                // Radio button is selected
                                Question question = (Question) getTableRow().getItem();
                                if (question != null) {
                                    RadioButton selectedRadioButton = (RadioButton) newValue;
                                    String selectedAnswer = selectedRadioButton.getText();
                                    String[] selectedQuestionInfo = new String[5];
                                    selectedQuestionInfo[0] = question.getQuestionNum();
                                    selectedQuestionInfo[1] = question.getQuestionText();
                                    selectedQuestionInfo[2] = selectedAnswer;
                                    selectedQuestionInfo[3] = question.getAnswerCorrect();
                                    selectedQuestionInfo[4] = questionPointText.getText().replace("Point: ", "");
                                    selectedQuestionArray.add(selectedQuestionInfo);
                                    CurrentExamSelectedQuestionStudent select = new CurrentExamSelectedQuestionStudent(selectedQuestionInfo[0],selectedQuestionInfo[1],
                                            selectedQuestionInfo[2],selectedQuestionInfo[3],selectedQuestionInfo[4]);
                                    if(examselect.containsKey(selectedQuestionInfo)) {
                                        examselect.replace(selectedQuestionInfo[0], select);
                                    }else {
                                        examselect.put(select.getQuestionID(), select);
                                    }
                                    for (Map.Entry<String, CurrentExamSelectedQuestionStudent> entry : examselect.entrySet()) {
                                        String key = entry.getKey();
                                        CurrentExamSelectedQuestionStudent value = entry.getValue();
                                        System.out.println("Key: " + key);
                                        System.out.println("Value: " + value.toString());
                                    }
                                    System.out.println("------------------------------------");

                                }
                            } else {
                                // No radio button is selected
                                System.out.println("No radio button selected");
                            }
                        });

                        updateItem(null, true);
                    }
                    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setGraphic(null);
                        } else {
                            Question question = (Question) getTableRow().getItem();
                            if (question != null) {
                                List<String> answers = Arrays.asList(
                                        question.getAnswer1(),
                                        question.getAnswer2(),
                                        question.getAnswer3(),
                                        question.getAnswerCorrect()
                                );

                                Collections.shuffle(answers);
                                radioButton1.setText(answers.get(0));
                                radioButton2.setText(answers.get(1));
                                radioButton3.setText(answers.get(2));
                                radioButton4.setText(answers.get(3));

                                int questionIndex = questionList.indexOf(question);
                                if (questionIndex >= 0 && questionIndex < questionpoint.size()) {
                                    String point = questionpoint.get(questionIndex);
                                    questionPointText.setText("Point: " + point);
                                }

                                // Clear the selected toggle before updating the radio buttons
                                toggleGroup.selectToggle(null);

                                if (examselect.containsKey(question.getQuestionNum())) {
                                    CurrentExamSelectedQuestionStudent selectedQuestion = examselect.get(question.getQuestionNum());
                                    String selectedAnswer = selectedQuestion.getStudentAnswer();

                                    if (radioButton1.getText().equals(selectedAnswer)) {
                                        toggleGroup.selectToggle(radioButton1);
                                    } else if (radioButton2.getText().equals(selectedAnswer)) {
                                        toggleGroup.selectToggle(radioButton2);
                                    } else if (radioButton3.getText().equals(selectedAnswer)) {
                                        toggleGroup.selectToggle(radioButton3);
                                    } else if (radioButton4.getText().equals(selectedAnswer)) {
                                        toggleGroup.selectToggle(radioButton4);
                                    }
                                }

                                setGraphic(container);

                                // Disable or enable the radio buttons based on the flag
                                radioButton1.setDisable(flag == 0);
                                radioButton2.setDisable(flag == 0);
                                radioButton3.setDisable(flag == 0);
                                radioButton4.setDisable(flag == 0);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        };
    }

    
    public void check(ActionEvent event) throws IOException {
    	if(id_Studentid.getText().isEmpty()) {
    		id_notecheckid.setText("Put you ID");
    		return;
    	}
    	String checkquery2 = "SELECT * FROM student WHERE ID = ? AND Username = ?";
        Object[] checkparams2 = {id_Studentid.getText(),Studentinfo.get(0).getUsername() };
        sqlmessage checkmessage2 = new sqlmessage("check", checkquery2, checkparams2);
        chat.accept(checkmessage2);
        if(!isexsit) {
        	id_notecheckid.setText("Uncorrect ID");
        	id_Studentid.setText("");
        	return;
        }
        id_notecheckid.setText("");
        flag = 1;
        // Update the disable property of the radio buttons
        id_TableView.refresh();
        id_Studentid.setVisible(false);
        label_id.setVisible(false);
        id_checkid.setVisible(false);
        TimeConverterThread ThreadTime = new TimeConverterThread(exam.get(0).getDuration(), id_timeRemaining);
        ThreadTime.start();
        startTime = System.currentTimeMillis();
    }
    
    public void Back(ActionEvent event) throws IOException {
    	questionList.clear();
        id_TableView.setItems(FXCollections.observableArrayList(questionList));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/Start Online Exam.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Close the current window
        Stage currentStage = (Stage) id_TableView.getScene().getWindow();
        currentStage.close();
        
    }
    
    public class TimeConverterThread extends Thread {
        private int minutes;
        private Text timeRemainingText;
        private boolean addDurationOneTime;

        public TimeConverterThread(int minutes, Text timeRemainingText) {
            this.minutes = minutes;
            this.timeRemainingText = timeRemainingText;
            this.addDurationOneTime = false;
        }

        @Override
        public void run() {
            int totalSeconds = minutes * 60;
            while (totalSeconds > 0) {
                int remainingMinutes = totalSeconds / 60;
                int remainingSeconds = totalSeconds % 60;

                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    String timeFormat = String.format("%02d:%02d", remainingMinutes, remainingSeconds);
                    timeRemainingText.setText(timeFormat);
                });
                
                if (havenewduration && !addDurationOneTime) {
                    totalSeconds = totalSeconds + examDuration * 60;
                    addDurationOneTime = true;
                }
                if(SubmitDone) {
                	break;
                }
                if(Submitform) {
   	        		break;}
                if (examLockStatus) {
                	Submitform=false;
                    Platform.runLater(() -> {
                        timeRemainingText.setText("00:00");
                    });
           
                    ActionEvent event = new ActionEvent();

                    Platform.runLater(() -> {
                    	if(!Submitform)
                    		Submet(event);
                    });
                    break;
                }
                try {
                    Thread.sleep(1000); // Sleep for 0.5 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totalSeconds--;
            } 
            if (totalSeconds==0) {
            ActionEvent event = new ActionEvent();
            Platform.runLater(() -> {
                timeRemainingText.setText("00:00");
                if(!Submitform)
                	Submet(event);
            });
            //count2();
        }}
    }

    
    public void Submet(ActionEvent event) {
        if(event.getSource() instanceof Button)
        	Submitform=true;
    	endTime = System.currentTimeMillis(); // Record the end time
        long elapsedTime = endTime - startTime; // Calculate the elapsed time in milliseconds
        // Convert the elapsed time to the desired format (e.g., minutes and seconds)
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        DurationTake = minutes+":"+seconds;
        // Call the submission function or perform any other desired action
    	SubmitDone=true;
    	count2();
    	SubmetExam();
   	 if (sqldone) {
        	System.out.println("The system has submitted your exam.");
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/StudentMain.fxml"));
			try {
		        Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.setScene(scene);
		        stage.show();
		        // Close the current window
		        Stage currentStage = (Stage) id_TableView.getScene().getWindow();
		        currentStage.close();
			} catch (IOException e) {e.printStackTrace();}
        }
        else
        	System.out.println("Submet Failur.Try again!");
    }
    public void SubmetExam() {
    	examresult result = calculateexam();
    	String checkquery3 = "INSERT INTO examresult (ExamID,ExamResult,Subject,Course,ExamAnswer,pointperquestion,rightanswer,lecturerID,date,"
    			+ "time,status,note,StudentID,startexamNum,DurationTake,FormSubmission) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] checkparams3 = {result.getExamID(),result.getExamResult(),result.getSubject(),result.getCourse()
        		,result.getExamAnswer(),result.getPointperquestion(),result.getRightanswer(),result.getLecturerID()
        		,result.getDate(),result.getTime(),result.getStatus(),result.getNote(),result.getStudentID(),result.getStartexamNum(),
        		result.getDurationTake(),result.getFormSubmission()};
        sqlmessage checkmessage3 = new sqlmessage("save", checkquery3, checkparams3);
        System.out.println("Name lucturer "+result.getLecturerID());
        chat.accept(checkmessage3); 
    }
    public examresult calculateexam() {
    	int ResultPoint=0;
    	StringBuilder ExamAnswer = new StringBuilder();
    	StringBuilder pointperquestion = new StringBuilder();
    	StringBuilder RightAnswer = new StringBuilder();
    	for (Map.Entry<String, CurrentExamSelectedQuestionStudent> exam : examselect.entrySet()) {
    		if(exam.getValue().getStudentAnswer().equals(exam.getValue().getRightAnswer()))
    			ResultPoint+=Integer.parseInt(exam.getValue().getPoint());
    		ExamAnswer.append(exam.getValue().getStudentAnswer());
    		if(exam.getValue().getStudentAnswer().equals(exam.getValue().getRightAnswer()))
    			pointperquestion.append(exam.getValue().getPoint());
    		else
    			pointperquestion.append("0");
    		
    		ExamAnswer.append(";");
    		pointperquestion.append(";");
    		RightAnswer.append(exam.getValue().getRightAnswer());
    		RightAnswer.append(";");
    		
		}
    	ExamAnswer.deleteCharAt(ExamAnswer.length() - 1);
    	pointperquestion.deleteCharAt(pointperquestion.length() - 1);
    	RightAnswer.deleteCharAt(RightAnswer.length() - 1);
    	LocalDate Date = LocalDate.now();
    	//Get Time
    	Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        Time currentTime = new Time(calendar.getTimeInMillis());
    	//Get Date
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        examresult returnexam = examresult.getInstance(0,exam.get(0).getID(),String.valueOf(ResultPoint),exam.get(0).getSubject(),exam.get(0).getCourse(),
    			ExamAnswer.toString(),pointperquestion.toString(),RightAnswer.toString(),startexam.get(0).getLecturerName(),
    			sqlDate.toString(),currentTime.toString(),"0","","",Studentinfo.get(0).getID(),startexam.get(0).getStartnum(),DurationTake,FormSubmission);
        if(!Submitform)
        	returnexam.setFormSubmission("late");
        else
        	returnexam.setFormSubmission("in Time");
        return returnexam;
    	
    }
    
    private void count2() {
   	 String query = "SELECT * FROM currentexam WHERE examid = ?";
		 Object[] pram = {startexam.get(0).getExamID()};
		 sqlmessage message = new sqlmessage("get",query , pram);
		 chat.accept(message);
		 CurrentExam ex=CurrentExam.convertToCurrentExam(resultList.get(0));
		 if((ex.getCount()-1)==0) {
			 String deletequery = "DELETE FROM currentexam WHERE examid = ?";
			 Object[] deletepram = {startexam.get(0).getExamID()};
			 sqlmessage deletemessage = new sqlmessage("delete",deletequery , deletepram);
			 chat.accept(deletemessage);
			 String editquery = "UPDATE startexam SET IsLocked = ? WHERE examid = ?";
			 Object[] editpram = {1,startexam.get(0).getExamID()};
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
		 }
		 else {
        String query2 = "UPDATE currentexam SET count = ? WHERE examid = ?";
        Object[] pram2 = {ex.getCount()-1,startexam.get(0).getExamID()};
		 sqlmessage message2 = new sqlmessage("edit",query2 , pram2);
		 chat.accept(message2);
		 }
   }

}
