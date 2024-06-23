package lecturer;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExamQuestionBankController {
	
    @FXML
    private ComboBox<String> ExamQuestionComboBox;

    @FXML
    private Button next_button;
    
    @FXML
    private Button back_button;
    
    @FXML
    private Text status;
    
    public void initialize() {
        // Add options to the infoStationComboBox
    	ExamQuestionComboBox.getItems().addAll("Exams", "Questions");
    }
    
    public void next(ActionEvent event) throws IOException {
        String selectedRole = ExamQuestionComboBox.getValue();
        if (selectedRole == null) {
            status.setText("Please select a role.");
            return;
        }
        if (selectedRole.equals("Exams") ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Exam Bank.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (selectedRole.equals("Questions") ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/Question Bank.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    
	 public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	 }

}
