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
import logic.Student;
import logic.examresult;
import logic.sqlmessage;

public class ShowReport3Controller {
    @FXML
    private ComboBox<String> choose_std_combo;

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
    
    private List<Student> student_List = new ArrayList<>();
    private List<examresult> ex_List = new ArrayList<>();
    private List<String> studentID_List = new ArrayList<>();
	private List<String> temp = new ArrayList<>();

    
    public void initialize() {
	  	String department = null;
    	for (DepartmentHead departmentHead : DepartmentHeadinfo) {
    	    if (departmentHead.getUsername().equals(Username)) {
    	        department = departmentHead.getDepartment();
    	        break; // Exit the loop once a match is found
    	    }
    	}
		String query = "SELECT * FROM student WHERE Department= ?";
		Object[] params = {department};
		sqlmessage message = new sqlmessage("get", query, params);
		chat.accept(message);
		
		for (Map<String, Object> row : resultList) {
			Student student = Student.convertToStudent(row);
			student_List.add(student);
		}
		for(Student student : student_List) {
			studentID_List.add(student.getID());
		}
        Set<String> setWithoutDuplicates = new HashSet<>(studentID_List);
        studentID_List = new ArrayList<>(setWithoutDuplicates);
        
		for(String s : studentID_List) {
			choose_std_combo.getItems().addAll(s);
		}
    }
    
	 public void show(ActionEvent event) throws IOException {
		 	ex_List.clear();
		    graph.getData().clear(); // Clear previous data from the BarChart

			int counter=0;
			double sum=0;
	
	        String selectedRole = choose_std_combo.getValue();
	        if (selectedRole == null) {
	            status.setText("Please select a role.");
	            return;
	        }
	        else {

				String query = "SELECT * FROM examresult WHERE StudentID= ?";
				Object[] params = {selectedRole};
				sqlmessage message = new sqlmessage("get", query, params);
				chat.accept(message);
				
				for (Map<String, Object> row : resultList) {
					examresult ex = examresult.convertToExamResult(row);
					ex_List.add(ex);
				}
				if(ex_List.isEmpty()) {
					System.out.println("hhh");
				}
				else {
		            List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
		            String[] grades = {"0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-100"};
		            for (int i = 0; i < 10; i++) {
		                XYChart.Series<String, Number> series = new XYChart.Series<>();
		                series.setName(grades[i]); // Custom column names as fruits
		                seriesList.add(series);
		            }
				     int columns1=0,columns2=0,columns3=0,columns4=0,columns5=0,columns6=0,columns7=0,
					            columns8=0,columns9=0,columns10=0;
					for(examresult ex : ex_List) {
						sum+=Double.parseDouble(ex.getExamResult());
						counter++;
						if(Double.parseDouble(ex.getExamResult())>=0.0 && Double.parseDouble(ex.getExamResult())<=9.0){
							columns1++;
						}
						else if(Double.parseDouble(ex.getExamResult())>=10.0 && Double.parseDouble(ex.getExamResult())<=19.0){
							columns2++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=20.0 && Double.parseDouble(ex.getExamResult())<=29.0){
							columns3++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=30.0 && Double.parseDouble(ex.getExamResult())<=39.0){
							columns4++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=40.0 && Double.parseDouble(ex.getExamResult())<=49.0){
							columns5++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=50.0 && Double.parseDouble(ex.getExamResult())<=59.0){
							columns6++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=60.0 && Double.parseDouble(ex.getExamResult())<=69.0){
							columns7++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=70.0 && Double.parseDouble(ex.getExamResult())<=79.0){
							columns8++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=80.0 && Double.parseDouble(ex.getExamResult())<=89.0){
							columns9++;
	
						}
						else if(Double.parseDouble(ex.getExamResult())>=90.0 && Double.parseDouble(ex.getExamResult())<=100.0){
							columns10++;
	
						}
						
					}
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
	
		    		if(counter!=0)
		    			sum=sum/counter;
		    		AVG_text.setText(String.valueOf(sum));
			    	Median_text.setText(String.format("%.2f", cal_med()));
				}
	        }
		 
	 }
	 private double cal_med() {
		 temp.clear();
		 for(examresult ex : ex_List) {
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
