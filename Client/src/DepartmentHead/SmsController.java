package DepartmentHead;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.sms;
import logic.sqlmessage;
import static gui.LogIn.DepartmentHeadinfo;
import static DepartmentHead.DepartmentHeadMain.flag;
import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SmsController implements Initializable {
    @FXML
    private TableView<sms> sms_table;

    @FXML
    private TableColumn<sms, String> lec_column;

    @FXML
    private TableColumn<sms, String> id_column;

    @FXML
    private TableColumn<sms, Integer> duration_coulmn;

    @FXML
    private TableColumn<sms, String> reaason_column;

    @FXML
    private TableColumn<sms, Void> response_column;
    
    private ObservableList<sms> smsList = FXCollections.observableArrayList();


  
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set cell value factories for each column
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), sms_table);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        lec_column.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
        id_column.setCellValueFactory(new PropertyValueFactory<>("examId"));
        duration_coulmn.setCellValueFactory(new PropertyValueFactory<>("newDuration"));
        reaason_column.setCellValueFactory(new PropertyValueFactory<>("reason"));

        loadData();

        response_column.setCellFactory(column -> new TableCell<sms, Void>() {
            private final Button acceptButton = new Button("Accept");
            private final Button rejectButton = new Button("Reject");
            private final HBox buttonContainer = new HBox(acceptButton, rejectButton);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    if (getTableRow() != null) {
                        sms smsObj = (sms) getTableRow().getItem();
                        if (smsObj.getResponse() == null || smsObj.getResponse().isEmpty()) {
                            // Show Accept and Reject buttons only if response is not set
                            acceptButton.setOnAction(event -> handleAcceptButton(smsObj));
                            rejectButton.setOnAction(event -> handleRejectButton(smsObj));
                            setGraphic(buttonContainer);
                        } else {
                            // Show the response text if already set
                            Text responseText = new Text(smsObj.getResponse());
                            setGraphic(responseText);
                        }
                    }
                }
            }
        });
    }


    
    private void handleAcceptButton(sms smsObj) {
    	flag=0;
    	String query = "UPDATE sms SET response = ? WHERE exam_id = ?";
        Object[] params = { "Accept", smsObj.getExamId() };
        sqlmessage message = new sqlmessage("edit", query, params);
        chat.accept(message);
        String insertQuery = "INSERT INTO seesms (Department, lecturer, exam_id, `new duration`, reason, response) VALUES (?, ?, ?, ?, ?, ?)";
   	    Object[] pram2= {smsObj.getDepartment(),smsObj.getLecturer(),smsObj.getExamId(),smsObj.getNewDuration(),smsObj.getReason(),"Accept"};
        sqlmessage message2 = new sqlmessage("save", insertQuery, pram2);
        chat.accept(message2);
        loadData();
    	
        
    }
   
    private void handleRejectButton(sms smsObj) {
    	flag=0;
    	String query = "UPDATE sms SET response = ? WHERE exam_id = ?";
        Object[] params = { "Reject", smsObj.getExamId() };
        sqlmessage message = new sqlmessage("edit", query, params);
        chat.accept(message);
        String insertQuery = "INSERT INTO seesms (Department, lecturer, exam_id, `new duration`, reason, response) VALUES (?, ?, ?, ?, ?, ?)";
   	    Object[] pram2= {smsObj.getDepartment(),smsObj.getLecturer(),smsObj.getExamId(),smsObj.getNewDuration(),smsObj.getReason(),"Reject"};
        sqlmessage message2 = new sqlmessage("save", insertQuery, pram2);
        chat.accept(message2);
        loadData();
       
    }
    
    private void loadData() {
    	smsList.clear();
        String query = "SELECT * FROM sms WHERE Department = ?";
        Object[] params = { DepartmentHeadinfo.get(0).getDepartment() };
        sqlmessage message = new sqlmessage("get", query, params);
        chat.accept(message);

        for (Map<String, Object> row : resultList) {
            sms sms1 = sms.convertToSMS(row);
            String response = sms1.getResponse();
            if (response == null ) {
                smsList.add(sms1);
            }
            
        }

        sms_table.setItems(smsList);
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
