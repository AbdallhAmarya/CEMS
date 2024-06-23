package gui;

import static client.ClientUI.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.manger;
import logic.sqlmessage;
import static client.ChatClient.resultList;

public class NewUserController {
    @FXML
    private TextField userpassword_field;
    
    @FXML
    private TextField username_field;
    
    @FXML
    private TextField userphone_field;
    
    @FXML
    private TextField useremail_field;
    
    @FXML
    private TextField userlast_field;
    
    @FXML
    private TextField userfirst_field;
    
    @FXML
    private TextField userID_field;
    
    @FXML
    private TextField yourID_field;
    
    @FXML
    private Button send_button;
    
    @FXML
    private Button back_button;
    
    @FXML
    private Text check_text;
    
    @FXML
    private ComboBox<String> roles_combo;
    
	private List<String> mangerID = new ArrayList<>();

        
    public void initialize() {
    	roles_combo.getItems().addAll("Student", "Lecturer", "Department_head");
    	chat.accept("openconnection");
    	
    }
    
    public void send(ActionEvent event) throws IOException {
    	check_text.setText("");
    	String s = userpassword_field.getText();
    	String s1 = username_field.getText();
    	String s2 = userphone_field.getText();
    	String s3 =useremail_field.getText();
    	String s4 =userlast_field.getText();
    	String s5 =userfirst_field.getText();
    	String s6 =userID_field.getText();
    	String s7 =yourID_field.getText();
        String selectedRole = roles_combo.getValue();

    	
        if (s.isEmpty() || s1.isEmpty()|| s2.isEmpty()|| s3.isEmpty()|| s4.isEmpty()|| s5.isEmpty()
        		|| s6.isEmpty()|| s7.isEmpty() || selectedRole.isEmpty()) {
        	check_text.setText("One of the fields are empty.");
        }
        else{
            String query = "SELECT * FROM manger WHERE ID= ? ";
            Object[] params = {s7};
            sqlmessage message = new sqlmessage("get", query, params);
            chat.accept(message);
    		for (Map<String, Object> row : resultList) {
    			manger man = manger.convertToManger(row);
    			mangerID.add(man.getID());
    		}
            if(mangerID.contains(s7)) {
            	if(selectedRole.equals("Student")) {
            		check_text.setText("Student saves successfully.");
            	}
            	else if (selectedRole.equals("Lecturer")) {
            		check_text.setText("Lecturer saves successfully.");
            	}
            	else if(selectedRole.equals("Department_head")) {
            		check_text.setText("Department head saves successfully.");
            	}
            }
            else {
            	check_text.setText("You don't Have a permission.");
            }
        }

    }
    
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginClient.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
