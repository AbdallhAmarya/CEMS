package gui;



import java.io.IOException;

import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IpPageController {
    @FXML
    private TextField ip_textfield;
    @FXML
    private TextField port_textfield;
    @FXML
    private Button enterFromAnotherComputerButton;
    @FXML
    private Button enterAsLocalhostButton;
    
    public ClientUI client;
    
   
    
    @FXML
    public void handleEnterFromAnotherComputerButton(ActionEvent event) throws IOException {
        String ipAddress = ip_textfield.getText();
        int port = Integer.parseInt(port_textfield.getText());
        client.chat=new ClientController(ipAddress, port);
        client.chat.accept("AddClient");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginClient.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void enterAsLocalhostButton(ActionEvent event) throws IOException {
        String ipAddress = "localhost";
        int port = 5555;
        client.chat=new ClientController(ipAddress, port);
        client.chat.accept("AddClient");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginClient.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void exitapp(ActionEvent event) throws IOException {
    	System.exit(1);
    }



  
}

