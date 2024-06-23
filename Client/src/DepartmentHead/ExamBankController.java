package DepartmentHead;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Exam;
import logic.sqlmessage;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.DepartmentHeadinfo;

import java.io.IOException;
import java.util.Map;


public class ExamBankController {
    @FXML
    private TableView<Exam> examTable;

    @FXML
    private TableColumn<Exam, String> idColumn;

    @FXML
    private TableColumn<Exam, String> LecturerColumn;

    @FXML
    private TableColumn<Exam, String> courseColumn;

    @FXML
    private TableColumn<Exam, String> durationColumn;

    @FXML
    private TableColumn<Exam, Void> show_column;
    
    public static Exam exam;

    public void initialize() {
        // Set up the table columns
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), examTable);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        LecturerColumn.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("Duration"));

        // Define the cell factory for the showColumn to display a button
        show_column.setCellFactory(column -> new ButtonCell());

        String query = "SELECT * FROM exam WHERE Subject = ?";
        Object[] pram = {DepartmentHeadinfo.get(0).getDepartment()};
        sqlmessage message = new sqlmessage("get", query, pram);
        chat.accept(message);
        ObservableList<Exam> examList = FXCollections.observableArrayList();

        for (Map<String, Object> row : resultList) {
            Exam exam = Exam.convertToExam(row);
            examList.add(exam);
        }

        examTable.setItems(examList);
    }

	 public void back(ActionEvent event) throws IOException {
		   	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/DepartmentHeadMain.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	 } 

    private class ButtonCell extends TableCell<Exam, Void> {
        private final Button showButton;

        public ButtonCell() {
            showButton = new Button("Show");
            showButton.setOnAction(event -> {
                Exam exam1 = (Exam) getTableRow().getItem();
                if (exam1 != null) {
                	exam=exam1;
                	FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam Bank 2.fxml"));
                    Parent root = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    Scene scene = new Scene(root);
                    Stage stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(showButton);
            } else {
                setGraphic(null);
            }
        }
    }
}
