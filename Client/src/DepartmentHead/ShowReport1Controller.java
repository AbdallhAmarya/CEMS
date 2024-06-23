package DepartmentHead;

import static client.ClientUI.chat;

import static client.ChatClient.resultList;
import static gui.LogIn.DepartmentHeadinfo;
import static gui.LogIn.Username;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
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
import logic.Lecturer;
import logic.examresult;
import logic.ExamStatistics;
import logic.sqlmessage;

public class ShowReport1Controller {
    @FXML
    private ComboBox<String> choose_lec_combo;

    @FXML
    private Button show_button;
    
    @FXML
    private Button back_button;
    
    @FXML
    private Text status_text;
    
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
    
    private List<Lecturer> lecturer_List = new ArrayList<>();
    private List<ExamStatistics> examStatistics_List = new ArrayList<>();
    private List<examresult> exresult_List = new ArrayList<>();
    private List<String> lecId_list = new ArrayList<>();
	private List<String> temp = new ArrayList<>();
	
    
    public void initialize() {
    	String department = null;
    	for (DepartmentHead departmentHead : DepartmentHeadinfo) {
    	    if (departmentHead.getUsername().equals(Username)) {
    	        department = departmentHead.getDepartment();
    	        break; // Exit the loop once a match is found
    	    }
    	}

		String query = "SELECT * FROM lecturer WHERE Department= ?";
		Object[] params = { department };
		sqlmessage message = new sqlmessage("get", query, params);
		chat.accept(message);

		for (Map<String, Object> row : resultList) {
			Lecturer lecturer = Lecturer.convertToLecturer(row);
			lecturer_List.add(lecturer);
		}
		String query2 = "SELECT * FROM examstatistic";
		Object[] params2 = {};
		sqlmessage message2 = new sqlmessage("get", query2, params2);
		chat.accept(message2);
		
		for (Map<String, Object> row : resultList) {
			ExamStatistics examStatistics = ExamStatistics.convertToExamStatistics(row);
			examStatistics_List.add(examStatistics);
		}
		
		for (Lecturer lecturer : lecturer_List) {
			for (ExamStatistics examStatistics : examStatistics_List) {
				if(examStatistics.getLecturerID().equals(lecturer.getID())) {
					String id = lecturer.getID();
					lecId_list.add(id);
				}
			}
		}
        Set<String> setWithoutDuplicates = new HashSet<>(lecId_list);
        lecId_list = new ArrayList<>(setWithoutDuplicates);
        for(String s: lecId_list) {
        	choose_lec_combo.getItems().addAll(s);
        }
    }
    
    public void show(ActionEvent event) throws IOException {
    	AVG_text.setText("");
 		Median_text.setText("");
 	    graph.getData().clear(); // Clear previous data from the BarChart
	    status_text.setText("");
	    examStatistics_List.clear();
		int counter=0;
		double sum=0;
	    String selectedRole = choose_lec_combo.getValue();
	    if (selectedRole == null) {
	        status_text.setText("Please select a role.");
	    }
	    else {
	    	String query2 = "SELECT * FROM examstatistic WHERE LecturerID=?";
	    	Object[] params2 = {selectedRole};
	    	sqlmessage message2 = new sqlmessage("get", query2, params2);
	    	chat.accept(message2);
	    		
	    	for (Map<String, Object> row : resultList) {
	    		ExamStatistics examStatistics = ExamStatistics.convertToExamStatistics(row);
	    		examStatistics_List.add(examStatistics);
	    	}
	    	if(examStatistics_List.isEmpty()) {
	    		AVG_text.setText("This lecturer doesn't have exams");
	    	}
	    	else {
	    		//----------------New-------------//
	            List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
	            String[] grades = {"0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-100"};
	            for (int i = 0; i < 10; i++) {
	                XYChart.Series<String, Number> series = new XYChart.Series<>();
	                series.setName(grades[i]); // Custom column names as fruits
	                seriesList.add(series);
	            }
	            int[] columns = new int[10];

	            for (ExamStatistics exam : examStatistics_List) {
	                columns[0] += exam.getRange1Count();
	                columns[1] += exam.getRange2Count();
	                columns[2] += exam.getRange3Count();
	                columns[3] += exam.getRange4Count();
	                columns[4] += exam.getRange5Count();
	                columns[5] += exam.getRange6Count();
	                columns[6] += exam.getRange7Count();
	                columns[7] += exam.getRange8Count();
	                columns[8] += exam.getRange9Count();
	                columns[9] += exam.getRange10Count();
	            	sum+=exam.getAverage();
	    			counter++;
	            }
	            for (int i = 0; i < columns.length; i++) {
	            	seriesList.get(i).getData().add(new XYChart.Data<>("", columns[i]));
	            }
	            
	            graph.getData().addAll(seriesList);

	    		//----------------Stop-------------//

	               sum=sum/counter;
	               AVG_text.setText(String.format("%.2f", sum));
	               Median_text.setText(String.format("%.2f", cal_med()));
	    	}
	    	
	    }
	 }
	 
	 private double cal_med() {
		 temp.clear();
		 exresult_List.clear();
		 String selectedRole = choose_lec_combo.getValue();
		 String query = "SELECT * FROM examresult WHERE lecturerID= ?";
		 Object[] params = { selectedRole };
	     sqlmessage message = new sqlmessage("get", query, params);
		 chat.accept(message);
		 
 		for (Map<String, Object> row : resultList) {
 			examresult ex = examresult.convertToExamResult(row);
 			exresult_List.add(ex);
		}
 		for(examresult ex : exresult_List) {
 			temp.add(ex.getExamResult());
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
