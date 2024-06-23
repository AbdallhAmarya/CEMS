 package lecturer;
import logic.Course;

import static lecturer.CreatNewExam2Controller.SelectedQuestionPoint; 
import logic.Question;
import logic.sqlmessage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static lecturer.CreatNewExam2Controller.point;
import static lecturer.CreatNewExamController.Examinfo;
import static client.ChatClient.resultList;
import static client.ChatClient.sqldone;
import static client.ClientUI.chat;
import static gui.LogIn.Lecturerinfo;
import static lecturer.CreatNewExam2Controller.SelectedQuestion;
public class CreateNewExam3Controller implements Initializable{
    @FXML
    private Label id_TotalPoint;
    @FXML
    private Label id_TotalQuesation;
    @FXML
    private Label id_Subject;
    @FXML
    private Label id_ExamCourse;
    @FXML
    private Label id_ExamCode;
    @FXML
    private Label id_Duration;
    @FXML
    private Button id_Back;
    @FXML 
    private Button id_Finish;
    public void initialize(URL location, ResourceBundle resources) {
    	id_TotalPoint.setText(Integer.toString(point));
    	id_TotalQuesation.setText(Integer.toString(SelectedQuestion.size()));
    	id_Subject.setText(Examinfo.getSubject());
    	id_ExamCourse.setText(Examinfo.getCourse());
    	id_Duration.setText(Integer.toString(Examinfo.getDuration()));
    }
    public void Finish(ActionEvent event) throws IOException{
    	StringBuilder QuestionId = new StringBuilder();
    	for (Map.Entry<String, Question> entry : SelectedQuestion.entrySet()) {
    		QuestionId.append(entry.getValue().getQuestionNum());
    		QuestionId.append(";");;
		}
    	if (QuestionId.length() > 0) {
    		QuestionId.deleteCharAt(QuestionId.length() - 1);
        }
    	String QuestionIdString = QuestionId.toString();
    	String checkquery = "SELECT ID FROM course WHERE ID_Subject = ? AND CourseName = ?";
        Object[] checkparams = { Lecturerinfo.get(0).getSubjectID(),Examinfo.getCourse()};
        sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
        chat.accept(checkmessage);
        List<Course> CoursetList = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
        	Course subject = Course.convertCourse(row);
        	CoursetList.add(subject);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : SelectedQuestionPoint.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(";");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(";");
        }
        // Remove the last comma if present
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        
        
        String checkquery3 = "INSERT INTO exam (ID,Subject,Course,Duration,DescriptionForLecturers,DescriptionForStudents,Question,TotalPoint,PointPerQuestion,Lecturer) VALUES (?,?,?,?,?,?,?,?,?,?)";
        String result = stringBuilder.toString();
        String Examid =Lecturerinfo.get(0).getSubjectID()+CoursetList.get(0).getID();
        Object[] checkparams3 = { Examid,Examinfo.getSubject(),Examinfo.getCourse(),Examinfo.getDuration(),Examinfo.getDescriptionForLecturers(),
    			Examinfo.getDescriptionForStudents(),QuestionIdString,Integer.toString(point),result,Lecturerinfo.get(0).getFirstName()};
        sqlmessage checkmessage3 = new sqlmessage("save", checkquery3, checkparams3);
        chat.accept(checkmessage3); 
        if(sqldone)
        	System.out.println("Exam created");
        else
        	System.out.println("operation Failure Try again");
        
        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/LecturerMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Close the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    	//checkExamSave;
    }
    public void back(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lecturer/CreateNewExam2.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	        // Close the current window
	        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        currentStage.close();
	 }
}
