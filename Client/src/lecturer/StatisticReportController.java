package lecturer;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Lecturer;
import logic.Subject;
import logic.sqlmessage;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class StatisticReportController {

    @FXML
    private ComboBox<String> exam_id;
    @FXML 
    private Text status;
    public static String exam_ID;

    @FXML
    private void initialize() {
    	ArrayList<String> examList = fetchExamData();
        for (String examid : examList) {
            exam_id.getItems().add(examid);
        }
        
        
    }
    
    
    private ArrayList<String> fetchExamData() {
        String query = "SELECT * FROM subject WHERE ID = ?";
        Lecturer lecturer = Lecturerinfo.get(0);
        String subjectValue = lecturer.getSubjectID();
        Object[] params = { subjectValue };
        sqlmessage message = new sqlmessage("get", query, params);
        chat.accept(message);

        Subject subject = null;
        if (!resultList.isEmpty()) {
            Map<String, Object> row = resultList.get(0);
            subject = Subject.convertSubject(row);
        }

        ArrayList<String> examList = new ArrayList<>();

        if (subject != null) {
            String query2 = "SELECT ID FROM exam WHERE subject = ?";
            String subjectName = subject.getSubjectName();
            Object[] params2 = { subjectName };
            sqlmessage message2 = new sqlmessage("get", query2, params2);
            chat.accept(message2);
        }

        for (Map<String, Object> row : resultList) {
            String examId = row.get("ID").toString(); // Assuming the column name is "ID"
            examList.add(examId);
        }

        return examList;
    }
    
    public void back(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void show_exam_static(ActionEvent event) throws IOException {
    	exam_ID=exam_id.getValue();
    	if  (exam_ID == null || exam_ID.isEmpty())  {
    		status.setText("Please select an exam ID");
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/statiistc report2.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}
