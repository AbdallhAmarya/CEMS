package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class ExamPoint {
    private String questionNum;
    private Integer point;

    public ExamPoint(String questionNum, Integer point) {
        this.questionNum = questionNum;
        this.point = point;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    // Property getters for the cell value factories
    public ObservableValue<String> questionNumProperty() {
        return new SimpleStringProperty(questionNum);
    }

    public ObservableValue<Integer> pointProperty() {
        return new SimpleIntegerProperty(point).asObject();
    }
}
