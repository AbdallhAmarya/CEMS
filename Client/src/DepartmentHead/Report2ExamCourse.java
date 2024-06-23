package DepartmentHead;

import static client.ChatClient.resultList;
import static client.ClientUI.chat;
import static gui.LogIn.DepartmentHeadinfo;

import java.io.IOException;
import java.util.Map;
import static client.ChatClient.isexsit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Course;
import logic.Exam;
import logic.ExamStatistics;
import logic.sqlmessage;

public class Report2ExamCourse {
	@FXML
    private AnchorPane anchorPane;

    @FXML
    private Text average1;

    @FXML
    private Text average2;

    @FXML
    private Text medin1;

    @FXML
    private Text medin2;

    @FXML
    private ComboBox<String> course;

    @FXML
    private ComboBox<String> exam_id1;

    @FXML
    private ComboBox<String> exam_id2;

    @FXML
    private BarChart<String, Number> graph1;

    @FXML
    private CategoryAxis graph1XAxis;

    @FXML
    private NumberAxis graph1YAxis;

    @FXML
    private BarChart<String, Number> graph2;

    @FXML
    private CategoryAxis graph2XAxis;

    @FXML
    private NumberAxis graph2YAxis;

    public void initialize() {
    	  String query = "SELECT * FROM course WHERE Department = ? ";
          Object[] pram = { DepartmentHeadinfo.get(0).getDepartment() };
          sqlmessage message = new sqlmessage("get", query, pram);
          chat.accept(message);
          for (Map<String, Object> row : resultList) {
              Course co = Course.convertCourse(row);
              course.getItems().add(co.getCourseName());
          }
          
       // Add event handler for lecId ComboBox
          course.setOnAction(event -> {
              String selectedLecId = course.getValue();
              if (selectedLecId != null) {
                  populateExamComboBoxes(selectedLecId);
              }
          });
          
          exam_id1.setOnAction(event -> {
          	   graph1.getData().clear();
         	   average1.setText("");
         	   medin1.setText("");
              String selectedExamId1 = exam_id1.getValue();
              if (selectedExamId1 != null) {
                  updateGraph(selectedExamId1, graph1 ,average1,medin1);
              }
          });

          exam_id2.setOnAction(event -> {
          	 graph2.getData().clear();
          	 average2.setText("");
          	 medin2.setText("");
              String selectedExamId2 = exam_id2.getValue();
              if (selectedExamId2 != null) {
                  updateGraph(selectedExamId2, graph2,average2,medin2);
              }
          });
    }
    
    
    private void populateExamComboBoxes(String selectedLecId) {
    	
        exam_id1.getItems().clear();
        exam_id2.getItems().clear();

        // Retrieve the exams for the selected lecturer ID
        String query = "SELECT * FROM exam WHERE Course = ?";
        Object[] pram = { selectedLecId };
        sqlmessage message = new sqlmessage("get", query, pram);
        chat.accept(message);

        ObservableList<Exam> exams = FXCollections.observableArrayList();
        for (Map<String, Object> row : resultList) {
        	 Exam exam = Exam.convertToExam(row);
        	 String query2 = "SELECT * FROM examstatistic WHERE ExamID = ?";
             Object[] pram2 = { exam.getID() };
             sqlmessage message2 = new sqlmessage("check", query2, pram2);
             chat.accept(message2);
             if (isexsit) {
            	 exams.add(exam);
             }
            
        }
        // Populate the exam_id1 and exam_id2 ComboBoxes with exam IDs
        for (Exam exam : exams) {
            exam_id1.getItems().add(exam.getID());
            exam_id2.getItems().add(exam.getID());
        }
        }
        
        private void updateGraph(String selectedExamId, BarChart<String, Number> graph,Text av,Text me) {
            // Clear previous data in the graph
            graph.getData().clear();
            av.setText("");
            me.setText("");
            String query = "SELECT * FROM examstatistic WHERE ExamID = ?";
            Object[] pram = { selectedExamId };
            sqlmessage message = new sqlmessage("get", query, pram);
            chat.accept(message);
            ExamStatistics examStats = ExamStatistics.convertToExamStatistics(resultList.get(0));
            av.setText(String.valueOf(examStats.getAverage()));
            me.setText(String.valueOf(examStats.getMedian()));
            ObservableList<XYChart.Series<String, Number>> seriesList = graph.getData();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Exam Statistics"); // Set a name for the series

            // Add data points for the different grade ranges
            series.getData().add(new XYChart.Data<>("0-9", examStats.getRange1Count()));
            series.getData().add(new XYChart.Data<>("10-19", examStats.getRange2Count()));
            series.getData().add(new XYChart.Data<>("20-29", examStats.getRange3Count()));
            series.getData().add(new XYChart.Data<>("30-39", examStats.getRange4Count()));
            series.getData().add(new XYChart.Data<>("40-49", examStats.getRange5Count()));
            series.getData().add(new XYChart.Data<>("50-59", examStats.getRange6Count()));
            series.getData().add(new XYChart.Data<>("60-69", examStats.getRange7Count()));
            series.getData().add(new XYChart.Data<>("70-79", examStats.getRange8Count()));
            series.getData().add(new XYChart.Data<>("80-89", examStats.getRange9Count()));
            series.getData().add(new XYChart.Data<>("90-100", examStats.getRange10Count()));

            // Add the series to the graph
            seriesList.add(series);
        }

   	 public void back(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/DepartmentHead/static report for 2 exam.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	 }
   

}
