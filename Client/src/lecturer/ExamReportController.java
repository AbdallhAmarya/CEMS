package lecturer;
import static client.ChatClient.isexsit;

import javafx.scene.control.TextArea;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import logic.CheckingCopies;
import logic.Exam;
import logic.StartExam;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import logic.examresult;
import logic.sqlmessage;


public class ExamReportController implements Initializable{
	@FXML
	private TableView<examresult> id_TableView1;
	@FXML
	private TableView<CheckingCopies> id_TableView2;
	@FXML
	private TableColumn<examresult, Integer> tb1_ID;
	@FXML
	private TableColumn<examresult, String> tb1_examid;
	@FXML
	private TableColumn<examresult, String> tb1_course;
	@FXML
	private TableColumn<examresult, String> tb1_examresult;
	@FXML
	private TableColumn<examresult, String> tb1_date;
	@FXML
	private TableColumn<examresult, String> tb1_time;
	@FXML
	private TableColumn<examresult, String> tb1_StudentID;
	@FXML
	private TableColumn<examresult, String> id_tb1Status;
	@FXML
	private TableColumn<examresult, Void> tb1_button;
	@FXML
	private TableColumn<CheckingCopies, Integer> tb2_id;
	@FXML
	private TableColumn<CheckingCopies, String> tb2_examid;
	@FXML
	private TableColumn<CheckingCopies, String> tb2_student1;
	@FXML
	private TableColumn<CheckingCopies, String> tb2_student2;
	@FXML
	private TableColumn<CheckingCopies, String> tb2_precent;
	
	
	
	@FXML
	private TextField id_Examid;
	@FXML
	private Text id_dateText;
	@FXML
	private Text id_ExcutionText;
	@FXML
	private Text id_DurationText;
	@FXML
	private Text id_NumofText;
	@FXML
	private Text id_InTimeText;
	@FXML
	private Text id_LateText;
	public static String Examcode="";
	private List<CheckingCopies> checkingcopies = new ArrayList<>();
	
	

	public static List<examresult> Examresult = new ArrayList<>();
	private List<StartExam> Startexamlist = new ArrayList<>();
	private String startexamNum;

	
	public static List<Exam> examqeusLecturer = new ArrayList<>();
    public static examresult ShowExaminfoLecturer;
    public static int rowIndexLecturer;
	
   
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		id_Examid.setText(Examcode);
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), id_TableView1);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(1000), id_TableView2);
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(1.0);
        fadeTransition2.play();
        retrieveExam();
		id_TableView1.setItems(FXCollections.observableArrayList(Examresult));
	}
	
	
	
	 private void retrieveExam() {
		 if(id_Examid.getText().isEmpty()) {
			 System.out.println("Enter ExamCode");
			 return;
		 }
		 Examcode=id_Examid.getText();
		 id_dateText.setText("");
		 id_ExcutionText.setText("");
		 id_DurationText.setText("");
		 id_NumofText.setText("");
		 id_InTimeText.setText("");
		 id_LateText.setText("");
		 
		 String checkquery5 = "SELECT Startnum FROM startexam where ExamCode = ? AND LecturerName = ?";
	     Object[] checkparams5 = {id_Examid.getText(),Lecturerinfo.get(0).getID()};
	     sqlmessage checkmessage5 = new sqlmessage("check", checkquery5, checkparams5);
	     chat.accept(checkmessage5);
	     if(!isexsit) {
	    	 showAlert("There no exam Availble");
	    	 return;
	     }
	    	 
		 
		 String checkquery = "SELECT Startnum FROM startexam where ExamCode = ? AND LecturerName = ?";
	     Object[] checkparams = {id_Examid.getText(),Lecturerinfo.get(0).getID()};
	     sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
	     List<StartExam> startExam = new ArrayList<>();
	     chat.accept(checkmessage);
	     for (Map<String, Object> row : resultList) {
	    	 StartExam exam = StartExam.convertToStartExam(row);
	    	 startExam.add(exam);
	     }
	     
	     startexamNum=Integer.toString(startExam.get(0).getStartnum());
	     System.out.println(Lecturerinfo.get(0).getFirstName()+" "+startExam.get(0).getStartnum());
	     String checkquery2 = "SELECT * FROM examresult where lecturerID = ? AND startexamNum = ?";
	     Object[] checkparams2 = {Lecturerinfo.get(0).getID(),startExam.get(0).getStartnum()};
	     sqlmessage checkmessage2 = new sqlmessage("get", checkquery2, checkparams2);
	     chat.accept(checkmessage2);
	     for (Map<String, Object> row : resultList) {
	         examresult exam = examresult.convertToExamResult(row);
	         Examresult.add(exam);
	     }
	  }
	
	 private Callback<TableColumn<examresult, Void>, TableCell<examresult, Void>> createButtonCellFactory() {
		    return new Callback<TableColumn<examresult, Void>, TableCell<examresult, Void>>() {
		        @Override
		        public TableCell<examresult, Void> call(TableColumn<examresult, Void> param) {
		            final TableCell<examresult, Void> cell = new TableCell<examresult, Void>() {
		                private final Button showButton = new Button("Copies");
		                private final Button Edit = new Button("Edit grade");
		                private final Button Accept = new Button("Accept");
		                private final Button ShowExam = new Button("Show Exam");
		                private final Button WriteNote = new Button("Write Note");
		                private final VBox container = new VBox();

		                {
		                    container.getChildren().addAll(showButton, Edit, ShowExam,WriteNote,Accept);
		                    container.setSpacing(5);
		                    showButton.getStyleClass().add("table-button");
		                    Edit.getStyleClass().add("table-button");
		                    Accept.getStyleClass().add("table-button");
		                    WriteNote.getStyleClass().add("table-button");
		                    ShowExam.getStyleClass().add("table-button");
		                    showButton.setStyle("-fx-font-size: 18px;");
		                    Edit.setStyle("-fx-font-size: 14px;");
		                    Accept.setStyle("-fx-font-size: 14px;");
		                    ShowExam.setStyle("-fx-font-size: 14px;");
		                    WriteNote.setStyle("-fx-font-size: 14px;");
		                    showButton.setOnAction(event -> {
		                    	FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), showButton);
		                        fadeTransition.setFromValue(0.0);
		                        fadeTransition.setToValue(1.0);
		                        fadeTransition.play();
		                        examresult examResult = (examresult) getTableRow().getItem();
		                        if (examResult != null) {
		                        	
		                        	if(checkcopiesexist(examResult.getID())) {
			                            retrieveCopies(examResult.getID());
			                            ViewCopies();
		                        	}else {
		                        		showAlert("Acording to a copy check,there is no fear of copies");
		                        	}
		                        		
		                            
		                        }
		                    });
		                    Edit.setOnAction(event -> {
		                    	FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Edit);
		                        fadeTransition.setFromValue(0.0);
		                        fadeTransition.setToValue(1.0);
		                        fadeTransition.play();
		                        examresult examResult = (examresult) getTableRow().getItem();
		                        if (examResult != null) {
		                            Stage currentStage = (Stage) id_TableView1.getScene().getWindow();

		                            openSmallWindow(currentStage, examResult);
		                        }
		                    });
		                    Accept.setOnAction(event -> {
		                    	FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Accept);
		                        fadeTransition.setFromValue(0.0);
		                        fadeTransition.setToValue(1.0);
		                        fadeTransition.play();
		                        examresult examResult = (examresult) getTableRow().getItem();
		                        if (examResult != null) {
		                            setdata(examResult);
		                            Accept.setVisible(false); 
		                        }
		                    });
		                    
                            ShowExam.setOnAction(event -> {
		                    	FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Accept);
		                        fadeTransition.setFromValue(0.0);
		                        fadeTransition.setToValue(1.0);
		                        fadeTransition.play();
		                        examresult examResult = (examresult) getTableRow().getItem();
		                        if (examResult != null) {
		                        	rowIndexLecturer = getTableRow().getIndex();
		                        	ShowExaminfoLecturer = examResult;
		                            preapreExamShow(); 
		                            try {
										Show(event);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                        }
		                    });
                            WriteNote.setOnAction(event -> {
		                    	FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Edit);
		                        fadeTransition.setFromValue(0.0);
		                        fadeTransition.setToValue(1.0);
		                        fadeTransition.play();
		                        examresult examResult = (examresult) getTableRow().getItem();
		                        if (examResult != null) {
		                            Stage currentStage = (Stage) id_TableView1.getScene().getWindow();

		                            writenotewindow(currentStage, examResult);
		                        }
		                    });
		                }

		                @Override
		                protected void updateItem(Void item, boolean empty) {
		                    super.updateItem(item, empty);
		                    if (empty) {
		                        setGraphic(null);
		                    } else {
		                        examresult examResult = (examresult) getTableRow().getItem();
		                        if (examResult != null) {
		                            // Set the initial visibility for all buttons and elements
		                            showButton.getStyleClass().add("table-button-add");
		                            Edit.getStyleClass().add("table-button-add");
		                            
		                            // Check the condition and hide the "Accept" button if necessary
		                            if (examResult.getStatus().equals("1")) {
		                                Accept.setVisible(false);
		                            } else {
		                                Accept.setVisible(true);
		                            }
		                        }
		                        setGraphic(container);
		                    }
		                }

		            };
		            return cell;
		        }
		    };
		}

	 private boolean checkcopiesexist(Integer Examid) {
		 String checkquery = "SELECT * FROM checkingcopies where ExamResultID = ?";
	     Object[] checkparams = {Examid};
	     sqlmessage checkmessage = new sqlmessage("check", checkquery, checkparams);
	     chat.accept(checkmessage);
	     return isexsit;
	 }
	 
	 private void retrieveCopies(Integer Examid) {
		 id_TableView2.getItems().clear();
		 checkingcopies.clear();
		 String checkquery = "SELECT * FROM checkingcopies where ExamResultID = ?";
	        Object[] checkparams = {Examid};
	        sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
	        chat.accept(checkmessage);
	        for (Map<String, Object> row : resultList) {
	        	CheckingCopies exam = CheckingCopies.convertToCheckingCopies(row);
	        	if(Double.parseDouble(exam.getSimilarPrecent())>=30&&!(exam.getStudent1ID().equals(exam.getStudent2ID())))
	        		checkingcopies.add(exam);
	        }
	    }
	 public void ViewCopies() {
		tb2_id.setCellValueFactory(new PropertyValueFactory<>("autoincrement"));
		tb2_examid.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		tb2_student1.setCellValueFactory(new PropertyValueFactory<>("Student1ID"));
		tb2_student2.setCellValueFactory(new PropertyValueFactory<>("Student2ID"));
		tb2_precent.setCellValueFactory(new PropertyValueFactory<>("SimilarPrecent"));
		tb1_ID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tb2_examid.setCellFactory(TextFieldTableCell.forTableColumn());
		tb1_course.setCellFactory(TextFieldTableCell.forTableColumn());
		tb1_examresult.setCellFactory(TextFieldTableCell.forTableColumn());
		tb1_date.setCellFactory(TextFieldTableCell.forTableColumn());
		tb1_time.setCellFactory(TextFieldTableCell.forTableColumn());
		tb1_StudentID.setCellFactory(TextFieldTableCell.forTableColumn());
		tb2_precent.setCellFactory(column -> {
		    return new TableCell<CheckingCopies, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty || item == null) {
		                setText(null);
		            } else {
		                double percentage = Double.parseDouble(item);
		                String formattedPercentage = String.format("%.2f%%", percentage);
		                setText(formattedPercentage);
		            }
		        }
		    };
		});
		id_TableView2.setItems(FXCollections.observableArrayList(checkingcopies));
	}
	 public void Select(ActionEvent event) throws IOException {
		 Examcode= id_Examid.getText();
		 Examresult.clear();
		 id_TableView1.getItems().clear();
		 id_TableView2.getItems().clear();
		 checkingcopies.clear();
		 tb1_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		 tb1_examid.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		 tb1_course.setCellValueFactory(new PropertyValueFactory<>("Course"));
		 tb1_examresult.setCellValueFactory(new PropertyValueFactory<>("ExamResult"));
		 tb1_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		 tb1_time.setCellValueFactory(new PropertyValueFactory<>("DurationTake"));
		 tb1_StudentID.setCellValueFactory(new PropertyValueFactory<>("StudentID"));		
		 id_tb1Status.setCellValueFactory(new PropertyValueFactory<>("FormSubmission"));	
		 tb1_ID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		 tb1_examid.setCellFactory(TextFieldTableCell.forTableColumn());
		 tb1_course.setCellFactory(TextFieldTableCell.forTableColumn());
		 id_tb1Status.setCellFactory(TextFieldTableCell.forTableColumn());
		 tb1_examresult.setCellFactory(TextFieldTableCell.forTableColumn());
		 tb1_date.setCellFactory(TextFieldTableCell.forTableColumn());
		 tb1_time.setCellFactory(TextFieldTableCell.forTableColumn());
		 tb1_StudentID.setCellFactory(TextFieldTableCell.forTableColumn());
		 tb1_button.setCellFactory(createButtonCellFactory());
		 retrieveExam();
		 id_TableView1.setItems(FXCollections.observableArrayList(Examresult));
		 ShowInfoOfTheExam(Examcode);
	 }
	 private void ShowInfoOfTheExam(String examcode) {
		 Startexamlist.clear();
		 String checkquery2 = "SELECT * FROM startexam where ExamCode = ?";
	     Object[] checkparams2 = {examcode};
	     sqlmessage checkmessage2 = new sqlmessage("get", checkquery2, checkparams2);
	     chat.accept(checkmessage2);
	     for (Map<String, Object> row : resultList) {
	    	 StartExam exam = StartExam.convertToStartExam(row);
	    	 Startexamlist.add(exam);
	     }
	     id_dateText.setText(Startexamlist.get(0).getDate().toString());
	     id_ExcutionText.setText(Startexamlist.get(0).getStartTime().toString());
	     id_DurationText.setText(Integer.toString(Startexamlist.get(0).getDuration()));
	     
	     String checkquery = "SELECT FormSubmission FROM examresult where startexamNum = ?";
	     Object[] checkparams = {startexamNum};
	     sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
	     List<examresult> examR = new ArrayList<>();
	     chat.accept(checkmessage);
	     for (Map<String, Object> row : resultList) {
	    	 examresult result = examresult.convertToExamResult(row);
	    	 examR.add(result);
	     }
	     
	     id_NumofText.setText(Integer.toString(examR.size()));
	     int intime=0;
	     for (int i = 0; i < examR.size(); i++) {
			if(examR.get(i).getFormSubmission().equals("in Time"))
				intime++;
		}
	     id_InTimeText.setText(Integer.toString(intime));
	     id_LateText.setText(Integer.toString(examR.size()-intime));
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
	 private void openSmallWindow(Stage primaryStage, examresult examresult) {
		    // Create a new stage
		    Stage smallWindow = new Stage();
		    smallWindow.setTitle("Small Window");

		    // Create UI components for the small window
		    Label gradeLabel = new Label("Grade:");
		    Label studentIdLabel = new Label("StudentID:");
		    Label newgrade = new Label("New grade:");
		    Label Note = new Label("Note:");
		    Text newGradeTextField = new Text(examresult.getExamResult()); // Set the initial value using Grade
		    Text StudentIDTextField = new Text(examresult.getStudentID()); // Set the initial value using StudentID
		    TextField NewgradText = new TextField();
		    TextArea NoteText = new TextArea();
		    Button saveButton = new Button("Save");
		    Text Error = new Text();
		    gradeLabel.setStyle("-fx-text-fill: blue;");
		    studentIdLabel.setStyle("-fx-text-fill: blue;");
		    newgrade.setStyle("-fx-text-fill: blue;");
		    Note.setStyle("-fx-text-fill: blue;");
		    saveButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
		    // Set the layout for the small window
		    VBox layout = new VBox(15);
		    layout.getChildren().addAll(gradeLabel, newGradeTextField, studentIdLabel, StudentIDTextField,newgrade,
		    		NewgradText,Note,NoteText,saveButton,Error);
		    layout.setAlignment(Pos.CENTER);
		    layout.setPadding(new Insets(15));
		    // Set the scene for the small window
		    Scene scene = new Scene(layout);
		    FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), layout);
		    fadeInTransition.setFromValue(0.0);
		    fadeInTransition.setToValue(1.0);
		    fadeInTransition.play();

		    // Show the small window
		    smallWindow.initOwner(primaryStage);
		    smallWindow.initModality(Modality.APPLICATION_MODAL);
		    smallWindow.setScene(scene);
		    smallWindow.show();
		    // Set the width and height of the stage
		   // smallWindow.setWidth(400); // Set the desired width
		    //smallWindow.setHeight(500); // Set the desired height
		    saveButton.setOnAction(event -> {
		    	if(NewgradText.getText().isEmpty()||NoteText.getText().isEmpty()) {
		    		Error.setText("Please enter a valid grade,note");
		    		return;
		    	}
		    	String checkquery3 = "UPDATE examresult SET ExamResult = ?, note = ? WHERE ID = ?";
			    Object[] checkparams3 = {NewgradText.getText(),NoteText.getText(),examresult.getID()};
			    sqlmessage checkmessage3 = new sqlmessage("save", checkquery3, checkparams3);
			    chat.accept(checkmessage3);
			    try {
					Select(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        smallWindow.close();
		    });
		}
	 private void writenotewindow(Stage primaryStage, examresult examresult) {
		    // Create a new stage
		    Stage smallWindow = new Stage();
		    smallWindow.setTitle("Small Window");

		    // Create UI components for the small window
		    Label gradeLabel = new Label("Grade:");
		    Label studentIdLabel = new Label("StudentID:");
		    Label Note = new Label("Note:");
		    Text newGradeTextField = new Text(examresult.getExamResult()); // Set the initial value using Grade
		    Text StudentIDTextField = new Text(examresult.getStudentID()); // Set the initial value using StudentID
		    TextArea NoteText = new TextArea();
		    Button Confirm = new Button("Confirm");
		    Text Error = new Text();
		    gradeLabel.setStyle("-fx-text-fill: blue;");
		    studentIdLabel.setStyle("-fx-text-fill: blue;");
		    Note.setStyle("-fx-text-fill: blue;");
		    Confirm.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
		    // Set the layout for the small window
		    VBox layout = new VBox(15);
		    layout.getChildren().addAll(gradeLabel, newGradeTextField, studentIdLabel, StudentIDTextField,
		    		Note,NoteText,Confirm,Error);
		    layout.setAlignment(Pos.CENTER);
		    layout.setPadding(new Insets(15));
		    // Set the scene for the small window
		    Scene scene = new Scene(layout);
		    FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), layout);
		    fadeInTransition.setFromValue(0.0);
		    fadeInTransition.setToValue(1.0);
		    fadeInTransition.play();

		    // Show the small window
		    smallWindow.initOwner(primaryStage);
		    smallWindow.initModality(Modality.APPLICATION_MODAL);
		    smallWindow.setScene(scene);
		    smallWindow.show();
		    // Set the width and height of the stage
		   // smallWindow.setWidth(400); // Set the desired width
		    //smallWindow.setHeight(500); // Set the desired height
		    Confirm.setOnAction(event -> {
		    	if(NoteText.getText().isEmpty()) {
		    		Error.setText("Please enter a valid note");
		    		return;
		    	}
		    	String checkquery3 = "UPDATE examresult SET noteforstudent = ? WHERE ID = ?";
			    Object[] checkparams3 = {NoteText.getText(),examresult.getID()};
			    sqlmessage checkmessage3 = new sqlmessage("save", checkquery3, checkparams3);
			    chat.accept(checkmessage3);
			    System.out.println("Exam Changed");
			    try {
					Select(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        smallWindow.close();
		    });
		}
	 
	 private void setdata(examresult examResult) {
		 String updateQuery = "UPDATE examresult SET status = ? WHERE ID = ?";
		 Object[] updateParams = {"1", examResult.getID()};
		 sqlmessage updateMessage = new sqlmessage("edit", updateQuery, updateParams);
		 chat.accept(updateMessage);
		 String query2 = "UPDATE student_grad SET tmp = ? WHERE StudentId = ?";
     	 Object[] params2 = {1,examResult.getStudentID()};
	     sqlmessage message2 = new sqlmessage("edit", query2, params2);
	     chat.accept(message2); 
		 showAlert("the grade have been send to student");
		 
	 }
	    public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    }
	    
	    public void Show(ActionEvent event) throws IOException {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/ExamReprot2.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	        // Close the current window
	        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        currentStage.close();
	    }
	    
	    public void preapreExamShow() {
	    	String checkquery1 = "SELECT * FROM exam WHERE ID = ?";
	        Object[] checkparams1 = { ShowExaminfoLecturer.getExamID() };
	        sqlmessage checkmessage1 = new sqlmessage("get", checkquery1, checkparams1);
	        chat.accept(checkmessage1);
	        for (Map<String, Object> row : resultList) {
	            Exam exm = Exam.convertToExam(row);
	            examqeusLecturer.add(exm);
	        } 
	    }
}
