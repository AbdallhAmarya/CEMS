package lecturer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.manual_exam;
import logic.myfile;
import logic.sqlmessage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static client.ChatClient.Myfile;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;

public class SeeFileController {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ComboBox<String> exam_id_box;

    @FXML
    private void initialize() {
        String query = "SELECT * FROM manual_exam WHERE subject = ?";
        Object[] param = {Lecturerinfo.get(0).getDepartment()};
        sqlmessage message = new sqlmessage("get", query, param);
        chat.accept(message);

        for (Map<String, Object> row : resultList) {
            manual_exam exam = manual_exam.convertToManualExam(row);
            exam_id_box.getItems().add(exam.getExam_id());}
        }
    @FXML
    private void get() {
        if (exam_id_box.getValue() == null || exam_id_box.getValue().isEmpty()) {
            Alert emptyComboBoxAlert = new Alert(AlertType.ERROR);
            emptyComboBoxAlert.setTitle("Error");
            emptyComboBoxAlert.setHeaderText(null);
            emptyComboBoxAlert.setContentText("ComboBox is empty. Please select an exam.");
            emptyComboBoxAlert.showAndWait();
            return;
        }

        myfile file = new myfile("get", null, "exam/" + exam_id_box.getValue(), null, null);
        chat.accept(file);

        byte[] fileData = Myfile.getFileData();
        if (fileData != null) {
            // Let the user choose the file location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.setInitialFileName(exam_id_box.getValue() + ".word");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Documents (*.word)", "*.word");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File selectedFile = fileChooser.showSaveDialog(anchorpane.getScene().getWindow());
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();

                try {
                    // Create the file at the chosen location
                    FileOutputStream fileOutputStream = new FileOutputStream(selectedFile);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();

                    // Show a window with a successful message
                    Platform.runLater(() -> {
                        Alert successAlert = new Alert(AlertType.INFORMATION);
                        successAlert.setTitle("File Saved");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("File saved successfully at: " + filePath);
                        successAlert.showAndWait();
                    });

                    // Open the saved file
                    Desktop.getDesktop().open(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle file creation error
                }
            }
        } else {
            // File data is null, handle retrieval error
            Alert retrievalErrorAlert = new Alert(AlertType.ERROR);
            retrievalErrorAlert.setTitle("Error");
            retrievalErrorAlert.setHeaderText(null);
            retrievalErrorAlert.setContentText("Failed to retrieve the file.");
            retrievalErrorAlert.showAndWait();
        }
    }

    public void back(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Manual Exam.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
