package DepartmentHead;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.DepartmentHeadinfo;
import static gui.LogIn.Username;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import logic.Exam;
import logic.ExamStatistics;
import logic.examresult;
import logic.Course;
import logic.sqlmessage;

public class ShowReport2Controller {
    @FXML
    private ComboBox<String> choose_cor_combo;

    @FXML
    private Button show_button;
    
    @FXML
    private Button back_button;
    
    @FXML
    private Text status;
    
    @FXML
    private Text AVG_text;
    
    @FXML
    private Text Median_text;
    
    @FXML
    private BarChart<String, Number> graph;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    private List<Course> course_List = new ArrayList<>();
    private List<Exam> exam_List = new ArrayList<>();
    private List<ExamStatistics> examStatistics_List = new ArrayList<>();
    private List<String> examID_List = new ArrayList<>();
    private List<examresult> exresult_List = new ArrayList<>();
	private List<String> temp = new ArrayList<>();

    
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
			course_List.add(course);
		}
		for (Course course : course_List) {
			String id = course.getID();
			choose_cor_combo.getItems().addAll(id);
		}
    	
    }
    
	 public void show(ActionEvent event) throws IOException {
		 	course_List.clear();
		 	exam_List.clear();
		 	examStatistics_List.clear();
		 	examID_List.clear();
		    graph.getData().clear(); // Clear previous data from the BarChart
		 	double sum=0;
		 	int counter=0;
	        String selectedRole = choose_cor_combo.getValue();
	        if (selectedRole == null) {
	            status.setText("Please select a role.");
	            return;
	        }
	        else {
	            List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
	            String[] grades = {"0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-100"};
	            for (int i = 0; i < 10; i++) {
	                XYChart.Series<String, Number> series = new XYChart.Series<>();
	                series.setName(grades[i]); // Custom column names as fruits
	                seriesList.add(series);
	            }
	            
	    		String query = "SELECT * FROM course WHERE ID= ?";
	    		Object[] params = { selectedRole };
	    		sqlmessage message = new sqlmessage("get", query, params);
	    		chat.accept(message);
	    		
	    		for (Map<String, Object> row : resultList) {
	    			Course course = Course.convertCourse(row);
	    			course_List.add(course);
	    		}
	    		
	    		String query1 = "SELECT * FROM exam";
	    		Object[] params1 = {};
	    		sqlmessage message1 = new sqlmessage("get", query1, params1);
	    		chat.accept(message1);
	    		
	    		for (Map<String, Object> row : resultList) {
	    			Exam exam = Exam.convertToExam(row);
	    			exam_List.add(exam);
	    		}
	    		
	    		for(Course course : course_List) {
	    			for(Exam exam : exam_List) {
	    				if(exam.getCourse().equals(course.getCourseName())) {
	    					examID_List.add(exam.getID());
	    				}
	    			}
	    		}
	    		String query2 = "SELECT * FROM examstatistic";
	    		Object[] params2 = {};
	    		sqlmessage message2 = new sqlmessage("get", query2, params2);
	    		chat.accept(message2);
	    		
	    		for (Map<String, Object> row : resultList) {
	    			ExamStatistics ex = ExamStatistics.convertToExamStatistics(row);
	    			examStatistics_List.add(ex);
	    		}
	            int columns1=0,columns2=0,columns3=0,columns4=0,columns5=0,columns6=0,columns7=0,
	            columns8=0,columns9=0,columns10=0;
	    		for(ExamStatistics ex : examStatistics_List) {
	    			for(String s : examID_List) {
	    				if(ex.getExamId().equals(s)) {
	    					columns1 += ex.getRange1Count();
	    					columns2 += ex.getRange2Count();
	    					columns3 += ex.getRange3Count();
	    					columns4 += ex.getRange4Count();
	    					columns5 += ex.getRange5Count();
	    					columns6 += ex.getRange6Count();
	    					columns7 += ex.getRange7Count();
	    					columns8 += ex.getRange8Count();
	    					columns9 += ex.getRange9Count();
	    					columns10 += ex.getRange10Count();
	    					sum+=ex.getAverage();
	    					counter++;
	    				}
	    			}
	    		}
	    		
	    	      // Add data to the series
	               seriesList.get(0).getData().add(new XYChart.Data<>("", columns1));
	               seriesList.get(1).getData().add(new XYChart.Data<>("", columns2));
	               seriesList.get(2).getData().add(new XYChart.Data<>("", columns3));
	               seriesList.get(3).getData().add(new XYChart.Data<>("", columns4));
	               seriesList.get(4).getData().add(new XYChart.Data<>("", columns5));
	               seriesList.get(5).getData().add(new XYChart.Data<>("", columns6));
	               seriesList.get(6).getData().add(new XYChart.Data<>("", columns7));
	               seriesList.get(7).getData().add(new XYChart.Data<>("", columns8));
	               seriesList.get(8).getData().add(new XYChart.Data<>("", columns9));
	               seriesList.get(9).getData().add(new XYChart.Data<>("", columns10));
	            graph.getData().addAll(seriesList);

	    		if(counter==0) {
		    		AVG_text.setText("There is no exams for this course");
	    		}
	    		else {
			    	sum=sum/counter;
			    	AVG_text.setText(String.format("%.2f", sum));
			    	Median_text.setText(String.format("%.2f", cal_med()));
	    		}
	        }

	 }
    
	 private double cal_med() {
		 temp.clear();
		 exresult_List.clear();
		 String query = "SELECT * FROM examresult";
		 Object[] params = {};
	     sqlmessage message = new sqlmessage("get", query, params);
		 chat.accept(message);
		 
	 	for (Map<String, Object> row : resultList) {
	 		examresult ex = examresult.convertToExamResult(row);
	 		exresult_List.add(ex);
		}
	 	
	 	for(Course course : course_List) {
	 		for(examresult ex : exresult_List) {
	 			if(ex.getCourse().equals(course.getCourseName())) {
	 	 			temp.add(ex.getExamResult());
	 			}
	 		}
	 	}
		String[] scores=new String[temp.size()];
 		scores = temp.toArray(scores);
        int[] intArray = new int[scores.length];
        for (int i = 0; i < scores.length; i++) {
            intArray[i] = Integer.parseInt(scores[i]);
        }
        Arrays.sort(intArray);
        double median;
        int n = scores.length;
        if (n % 2 == 0) {
            int middleIndex1 = n / 2 - 1;
            int middleIndex2 = n / 2;
            median = (intArray[middleIndex1] + intArray[middleIndex2]) / 2.0;
        } else {
            int middleIndex = n / 2;
            median = intArray[middleIndex];
        }
        return median;
	 }
	 
	 public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/Show Report.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	 }
}
