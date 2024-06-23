package lecturer;

import java.io.IOException;
import java.util.Map;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.sms;
import logic.sqlmessage;

import static gui.LogIn.Lecturerinfo;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;

public class SmsController {

    @FXML
    private Button backButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<sms> sms_table;

    @FXML
    private TableColumn<sms, String> exam_id_column;

    @FXML
    private TableColumn<sms, Integer> new_duration_column;

    @FXML
    private TableColumn<sms, String> reason_column;

    @FXML
    private TableColumn<sms, String> response_column;

    private ObservableList<sms> smsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), sms_table);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        exam_id_column.setCellValueFactory(new PropertyValueFactory<>("examId"));
        new_duration_column.setCellValueFactory(new PropertyValueFactory<>("newDuration"));
        reason_column.setCellValueFactory(new PropertyValueFactory<>("reason"));
        response_column.setCellValueFactory(new PropertyValueFactory<>("response"));

        loadData();
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

    @FXML
    public void refresh(ActionEvent event) throws IOException {
        smsList.clear();
        sms_table.getItems().clear();
        sms_table.refresh();

        loadData();
    }

    private void loadData() {
        String query = "SELECT * FROM sms WHERE lecturer = ?";
        Object[] params = { Lecturerinfo.get(0).getFirstName() };
        sqlmessage message = new sqlmessage("get", query, params);
        chat.accept(message);

        for (Map<String, Object> row : resultList) {
            sms sms1 = sms.convertToSMS(row);
            String response = sms1.getResponse();
            if (response != null && !response.isEmpty()) {
                smsList.add(sms1);
            }
        }

        sms_table.setItems(smsList);
    }
}


