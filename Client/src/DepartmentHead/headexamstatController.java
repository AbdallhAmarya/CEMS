package DepartmentHead;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.DepartmentHeadinfo;
import static gui.LogIn.Username;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import logic.Exam;
import logic.ExamStatistics;
import logic.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.DepartmentHead;
import logic.sqlmessage;

public class headexamstatController {
 	@FXML
    private Text course_text;

 	@FXML
    private Text median_text;
    
	@FXML
    private Text average_text;
	
	@FXML
    private Text Lowest_Grade_text;
	
	@FXML
    private Text hieghest_grade;
	
	@FXML
    private Text failing_percentage_text;
	
    @FXML
    private ComboBox<String> ExamID_combo;
    
    @FXML
    private BarChart<String, Number> graph;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
	@FXML
    private Button show_button;
    
	private List<Course> course_list = new ArrayList<>();
	private List<Exam> exam_list = new ArrayList<>();
	private List<String> examID_list = new ArrayList<>();


    public void initialize() {
    	String department = null;
    	for (DepartmentHead departmentHead : DepartmentHeadinfo) {
    	    if (departmentHead.getUsername().equals(Username)) {
    	        department = departmentHead.getDepartment();
    	        break; // Exit the loop once a match is found
    	    }
    	}
    	
		String query = "SELECT * FROM course WHERE Department= ?";
		Object[] params = { department };
		sqlmessage message = new sqlmessage("get", query, params);
		chat.accept(message);
		
		for (Map<String, Object> row : resultList) {
			Course course = Course.convertCourse(row);
			course_list.add(course);
		}
		
		String query1 = "SELECT * FROM exam";
		Object[] params1 = {};
		sqlmessage message1 = new sqlmessage("get", query1, params1);
		chat.accept(message1);
		
		for (Map<String, Object> row : resultList) {
			Exam exam = Exam.convertToExam(row);
			exam_list.add(exam);
		}
		
		for(Exam exam : exam_list) {
			for(Course course : course_list) {
				if(exam.getCourse().equals(course.getCourseName())) {
					examID_list.add(exam.getID());
				}
			}
		}
        for(String s: examID_list) {
        	ExamID_combo.getItems().addAll(s);
        }
    }
    
    public void show(ActionEvent event) throws IOException {
        median_text.setText("");
        average_text.setText("");
        Lowest_Grade_text.setText("");
        hieghest_grade.setText("");
        course_text.setText("");
        failing_percentage_text.setText("");
        graph.getData().clear();

	    String selectedRole = ExamID_combo.getValue();
		String query = "SELECT * FROM examstatistic WHERE ExamID= ?";
		Object[] params = {selectedRole};
		sqlmessage message = new sqlmessage("get", query, params);
		chat.accept(message);
		
        Map<String, Object> row = resultList.get(0);
        ExamStatistics exam_statistic= ExamStatistics.convertToExamStatistics(row);
        
        median_text.setText(String.format("%.2f", exam_statistic.getMedian()));
        average_text.setText(String.format("%.2f", exam_statistic.getAverage()));
        Lowest_Grade_text.setText(String.format("%d", exam_statistic.getLowestGrade()));
        hieghest_grade.setText(String.format("%d", exam_statistic.getHighestGrade()));


		String query1 = "SELECT * FROM exam WHERE ID= ?";
		Object[] params1 = {selectedRole};
		sqlmessage message1 = new sqlmessage("get", query1, params1);
		chat.accept(message1);
		
        Map<String, Object> row1 = resultList.get(0);
        Exam ex= Exam.convertToExam(row1);
        
        course_text.setText(ex.getCourse());
        failing_percentage_text.setText(String.format("%d", exam_statistic.getRange1Count()+exam_statistic.getRange2Count()+exam_statistic.getRange3Count()+exam_statistic.getRange4Count()+exam_statistic.getRange5Count()));
        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
        String[] grades = {"0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-100"};
        for (int i = 0; i < 10; i++) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(grades[i]); // Custom column names as fruits
            seriesList.add(series);
        }
        int[] columns = new int[10];
        columns[0] = exam_statistic.getRange1Count();
        columns[1] = exam_statistic.getRange2Count();
        columns[2] = exam_statistic.getRange3Count();
        columns[3] = exam_statistic.getRange4Count();
        columns[4] = exam_statistic.getRange5Count();
        columns[5] = exam_statistic.getRange6Count();
        columns[6] = exam_statistic.getRange7Count();
        columns[7] = exam_statistic.getRange8Count();
        columns[8] = exam_statistic.getRange9Count();
        columns[9] = exam_statistic.getRange10Count();
        for (int i = 0; i < columns.length; i++) {
        	seriesList.get(i).getData().add(new XYChart.Data<>("", columns[i]));
        }
        graph.getData().addAll(seriesList);

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
