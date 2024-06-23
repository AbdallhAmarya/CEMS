package DepartmentHead;

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

public class ShowReportController {

    @FXML
    private ComboBox<String> info_report_head;

    @FXML
    private Button show_button;
    
    @FXML
    private Button back_button;
    
    @FXML
    private Text status;
    
    public void initialize() {
        // Add options to the infoStationComboBox
    	info_report_head.getItems().addAll("various tests of a particular lecturer", "various tests of course","various tests of student");
    }
    
    public void show(ActionEvent event) throws IOException {
        String selectedRole = info_report_head.getValue();
        if (selectedRole == null) {
            status.setText("Please select a role.");
            return;
        }
        if (selectedRole.equals("various tests of a particular lecturer") ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Show Report 1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        
        else if (selectedRole.equals("various tests of course") ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Show Report 2.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (selectedRole.equals("various tests of student") ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Show Report 3.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    
    
	 public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/DepartmentHeadMain.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	 }
    
    
}
