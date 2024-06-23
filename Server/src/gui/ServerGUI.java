package gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import database.DBController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import logic.Client_info;
import server.CemsServer;
import server.ServerUI;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;



public class ServerGUI implements Initializable {
    @FXML
    private TableView<Client_info> clientTable;
    @FXML
    private TextField id_IPaddress;
    @FXML
    private TextField id_Port;
    @FXML
    private TextField id_DB_path;
    @FXML
    private TextField id_DB_UserName;
    @FXML
    private TextField id_DB_Password;
    @FXML
    private Button connectionbutton;
    @FXML
    private FontAwesomeIcon exitbutton;
    @FXML
    private TableColumn<Client_info, String> ipColumn;
    @FXML
    private TableColumn<Client_info, String> hostColumn;
    @FXML
    private TableColumn<Client_info, String> statusColumn;
    @FXML
    private Text connectedMessage;
    @FXML
    private TextArea consolearea;
    private static CemsServer server=null;
    private String Port;
    public static String username;
    public static String password;
    public static String db_name;
    private ConsoleOutput consoleOutput;
    private boolean isServerRunning = false;
    private ObservableList<Client_info> updatedData = FXCollections.observableArrayList();
    DBController db = new DBController();
    
    public static SimpleIntegerProperty num = new SimpleIntegerProperty(0);
  

    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ServerGUI.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectServer(ActionEvent event) throws Exception {
        try {
            if (isServerRunning) {
                System.out.println("Server is already running.");
                connectedMessage.setText("Server is already running");
                return;
           }
            Port = id_Port.getText();
            username = id_DB_UserName.getText();
            password = id_DB_Password.getText();
            db_name = id_DB_path.getText();
            server = new CemsServer(Integer.valueOf(Port),this);
            id_IPaddress.setText(server.getIP().getHostAddress());
            ServerUI.runServer(Port);
            isServerRunning = true;
            connectedMessage.setText("Server started");
        } catch (Exception e) {
            System.out.println("Error starting server: " + e.getMessage());
            connectedMessage.setText("Server failed to start");
        }
    }

    public void StopServer(ActionEvent event) throws Exception {

        try {
            // Stop the server
            if (server != null) {
                server.stopListening();
                server.close();
                server = null;

                // Clear the client table
                clientTable.getItems().clear();
                // Update the UI or show a message indicating that the server has been stopped
                System.out.println("Server stopped successfully.");
                connectedMessage.setText("Server stopped");
                isServerRunning = false;
            } else {
                System.out.println("Server is already stopped or not started.");
                connectedMessage.setText("Server is not running");
            }
        } catch (IOException e) {
            System.out.println("Error stopping server: " + e.getMessage());
            connectedMessage.setText("Error stopping server");
        }
    }
    
   
    @FXML
    public void exitApplication(javafx.scene.input.MouseEvent event) 
    {
    	Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        id_Port.setText("5555");
        id_DB_path.setText("jdbc:mysql://localhost/cems?serverTimezone=Asia/Jerusalem");
        id_DB_UserName.setText("root");
        id_DB_Password.setText("Aa123456");
        consoleOutput = new ConsoleOutput(consolearea);
        System.setOut(consoleOutput);
        System.setErr(consoleOutput);
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip")); 
        hostColumn.setCellValueFactory(new PropertyValueFactory<>("host")); 
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        num.addListener((obs, oldValue, newValue) -> {
        	if (newValue.intValue() == 1) {
                refreshTableView();
                num.set(0);
            }});
        
        
    }
    
    
    
    public void refreshTableView() {
        // Clear the existing data
        updatedData.clear();

        // Add your logic to update the data in the updatedData list
        HashMap<String, Client_info> clientInfoMap = server.getClientInfoMap();
        for (Map.Entry<String, Client_info> entry : clientInfoMap.entrySet()) {
            Client_info clientInfo = entry.getValue();
            updatedData.add(clientInfo);
        }

        // Update the table view
        clientTable.setItems(updatedData);
        clientTable.refresh();
    }
}
