package lecturer;

import logic.Question;

import static client.ChatClient.sqldone;
import static client.ClientUI.chat;
import static lecturer.EditExistingQuestionController.id_QuestionExist;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;

import static client.ChatClient.resultList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.sqlmessage;

public class EditExistingQuestion2Controller {
	@FXML
    private Text id_note;
    @FXML
    private TextField id_question;
    @FXML
    private TextField id_answer1;
    @FXML
    private TextField id_answer2;
    @FXML
    private TextField id_answer3;
    @FXML
    private TextField id_rightanswer;
    public void initialize() {
        String checkquery = "SELECT * FROM question WHERE QuestionNumber = ?";
        Object[] checkparams = { id_QuestionExist };
        sqlmessage checkmessage = new sqlmessage("get", checkquery, checkparams);
        chat.accept(checkmessage);
        List<Question> questionList = new ArrayList<>();
        for (Map<String, Object> row : resultList) {
            Question question = Question.convertToQuestion(row);
            questionList.add(question);
        } 
        id_question.setText(questionList.get(0).getQuestionText());
        id_answer1.setText(questionList.get(0).getAnswer1());
        id_answer2.setText(questionList.get(0).getAnswer2());
        id_answer3.setText(questionList.get(0).getAnswer3());
        id_rightanswer.setText(questionList.get(0).getAnswerCorrect());
    }
    public void Edit(ActionEvent event) throws IOException {
    	String checkquery = "UPDATE question SET QuestionText = ? , Answer1 = ? ,Answer2 = ? , Answer3 = ?, AnswerCorrect = ?"
    			+ " WHERE QuestionNumber = ?";
        Object[] checkparams = {id_question.getText(),id_answer1.getText(),id_answer2.getText(),id_answer3.getText(),
        		id_rightanswer.getText() ,id_QuestionExist };
        System.out.println(id_QuestionExist);
        sqlmessage checkmessage = new sqlmessage("edit", checkquery, checkparams);
        chat.accept(checkmessage);
        if(sqldone==false) {
        	System.out.println("update failed");
        	return;
        }
        if(sqldone==true) {
        	System.out.println("update successfully");
        	back(event);
        	return;
        }
    }
    public void back(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
