package lecturer;
import static client.ChatClient.isexsit;
import java.io.IOException;
import static client.ClientUI.chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.sqlmessage;
import static client.ChatClient.sqldone;

public class EditExistingQuestionController {
	@FXML
    private TextField id_question;
	
    @FXML
    private Button id_Back;
    
    public static String id_QuestionExist;
    private String QuestionId;
    
    
    
    public void Edit() throws IOException {
    	if(!checkField()) {
    		System.out.println("Put Question ID please\n");
    		id_question.setText("");
    		return;
    	}
    	checkdataExist();
	    if(!isexsit) {
	    	System.out.println("Question not Exist");
	    	id_question.setText("");
	    	return;
	    }
	    isexsit=false;
	    id_QuestionExist=QuestionId;
	    EditExistingQuestion2();
    }
    
    public void Delete() throws IOException {
    	if(!checkField()) {
    		System.out.println("Put Question ID please\n");
    		id_question.setText("");
    		return;
    	}
    	checkdataExist();
    	if(!isexsit) {
	    	System.out.println("Question not Exist");
	    	id_question.setText("");
	    	return;
	    }
    	String checkquery ="DELETE FROM question WHERE QuestionNumber = ?";
	    Object[] checkparams= {QuestionId};
	    sqlmessage checkmessage=new sqlmessage("delete",checkquery,checkparams);
	    chat.accept(checkmessage);
	    if(!sqldone) {
	    	System.out.println("No records found matching the criteria.");
	    	id_question.setText("");
	    	return;
	    }
	    System.out.println("Deletion successful");
	    id_question.setText("");
    }
    public void EditExistingQuestion2() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/EditExistingQuestion2.fxml"));
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.show();
    }
    public boolean checkField() {
    	QuestionId = id_question.getText();
    	if(QuestionId.isEmpty())
    		return false;
    	return true;
    }
    public void checkdataExist() {
    	String checkquery ="SELECT QuestionNumber FROM question WHERE QuestionNumber = ?";
	    Object[] checkparams= {QuestionId};
	    sqlmessage checkmessage=new sqlmessage("check",checkquery,checkparams);
	    chat.accept(checkmessage);
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
