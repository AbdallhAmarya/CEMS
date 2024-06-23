package lecturer;

import static client.ChatClient.resultList;


import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import logic.Exam;
import logic.Lecturer;
import logic.sqlmessage;

public class StartExamController implements Initializable {
    @FXML
    private TableView<Exam> id_TableView;
    @FXML
    private TableColumn<Exam, String> id_IDCol;
    @FXML
    private TableColumn<Exam, String> id_SubCol;
    @FXML
    private TableColumn<Exam, String> id_CourseCol;
    @FXML
    private TableColumn<Exam, String> id_DesStuCol;
    @FXML
    private TableColumn<Exam, String> id_DesLecCol;
    @FXML
    private TableColumn<Exam, String> id_PointCol;
    @FXML
    private TableColumn<Exam, Integer> id_DuraCol;
    @FXML
    private TableColumn<Exam, Void> id_ButtonCol;

    private List<Exam> ExamList = new ArrayList<>();
    public static String ExamIdSelected;
    public static Exam examinfo = Exam.getInstance(ExamIdSelected, ExamIdSelected, ExamIdSelected, ExamIdSelected, 0, ExamIdSelected, ExamIdSelected, ExamIdSelected, ExamIdSelected, ExamIdSelected, ExamIdSelected);
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), id_TableView);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        ExamList.clear();
        retrieveExam();
        id_IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        id_SubCol.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        id_CourseCol.setCellValueFactory(new PropertyValueFactory<>("Course"));
        id_DuraCol.setCellValueFactory(new PropertyValueFactory<>("Duration"));
        id_DesLecCol.setCellValueFactory(new PropertyValueFactory<>("DescriptionForLecturers"));
        id_DesStuCol.setCellValueFactory(new PropertyValueFactory<>("DescriptionForStudents"));
        id_PointCol.setCellValueFactory(new PropertyValueFactory<>("TotalPoint"));

        id_IDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        id_SubCol.setCellFactory(TextFieldTableCell.forTableColumn());
        id_CourseCol.setCellFactory(TextFieldTableCell.forTableColumn());
        id_DesStuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        id_DesLecCol.setCellFactory(TextFieldTableCell.forTableColumn());
        id_PointCol.setCellFactory(TextFieldTableCell.forTableColumn());
        id_DuraCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        id_ButtonCol.setCellFactory(createButtonCellFactory());
        id_TableView.setItems(FXCollections.observableArrayList(ExamList));
    }

    private void retrieveExam() {
    	Lecturer lecturer = Lecturerinfo.get(0);
        String checkquery = "SELECT * FROM exam WHERE Subject = ?";
        Object[] checkparams = { lecturer.getDepartment()};
        sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
        chat.accept(checkmessage);
        for (Map<String, Object> row : resultList) {
            Exam exam = Exam.convertToExam(row);
            ExamList.add(exam);
        }
    }
    
    public void StartExam2() throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/StartExam2.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Close the current window
        Stage currentStage = (Stage) id_TableView.getScene().getWindow();
        currentStage.close();
    }
    private Callback<TableColumn<Exam, Void>, TableCell<Exam, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Exam, Void>, TableCell<Exam, Void>>() {
            @Override
            public TableCell<Exam, Void> call(TableColumn<Exam, Void> param) {
                final TableCell<Exam, Void> cell = new TableCell<Exam, Void>() {
                    private final Button Select = new Button("Select");
                    private final VBox container = new VBox();
                    {
                        container.getChildren().addAll(Select);
                        container.setSpacing(5);
                        Select.getStyleClass().add("table-button");
                        Select.setOnAction(event -> {
                            Exam exam = (Exam) getTableRow().getItem();
                            if (exam != null) {
                            	examinfo=exam;
                            	ExamIdSelected=exam.getID();
                            	try {
									StartExam2();
								} catch (IOException e) {e.printStackTrace();}
                            }
                            // Update the table view
                            retrieveExam();
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Exam exam = (Exam) getTableRow().getItem();
                            if (exam != null) {
                            	
                            }
                            setGraphic(container);
                        }
                    }
                };
                return cell;
            }
        };
    }
    @FXML
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
