package logic;

import java.util.Map;

public class ExamStatistics {
    private static ExamStatistics instance;

    private String examId;
    private String lecturerID;
    private double average;
    private double median;
    private int lowestGrade;
    private int highestGrade;
    private int range1Count;
    private int range2Count;
    private int range3Count;
    private int range4Count;
    private int range5Count;
    private int range6Count;
    private int range7Count;
    private int range8Count;
    private int range9Count;
    private int range10Count;

    private ExamStatistics(String examId, String lecturerID, double average, double median, int lowestGrade, int highestGrade,
                           int range1Count, int range2Count, int range3Count, int range4Count, int range5Count,
                           int range6Count, int range7Count, int range8Count, int range9Count, int range10Count) {
        this.examId = examId;
        this.lecturerID = lecturerID;
        this.average = average;
        this.median = median;
        this.lowestGrade = lowestGrade;
        this.highestGrade = highestGrade;
        this.range1Count = range1Count;
        this.range2Count = range2Count;
        this.range3Count = range3Count;
        this.range4Count = range4Count;
        this.range5Count = range5Count;
        this.range6Count = range6Count;
        this.range7Count = range7Count;
        this.range8Count = range8Count;
        this.range9Count = range9Count;
        this.range10Count = range10Count;
    }

  

    public static ExamStatistics getInstance(String examId, String lecturerID, double average, double median, int lowestGrade,
                                            int highestGrade, int range1Count, int range2Count, int range3Count,
                                            int range4Count, int range5Count, int range6Count, int range7Count,
                                            int range8Count, int range9Count, int range10Count) {
        instance = new ExamStatistics(examId, lecturerID, average, median, lowestGrade, highestGrade, range1Count,
                range2Count, range3Count, range4Count, range5Count, range6Count, range7Count, range8Count,
                range9Count, range10Count);
        return instance;
    }

    public static ExamStatistics convertToExamStatistics(Map<String, Object> row) {
        String examId = (String) row.get("ExamID");
        String lecturerID = (String) row.get("LecturerID");
        double average = (row.get("Average") != null) ? (Double) row.get("Average") : 0.0;
        double median = (row.get("Median") != null) ? (Double) row.get("Median") : 0.0;
        int lowestGrade = (row.get("Lowest Grade") != null) ? (Integer) row.get("Lowest Grade") : 0;
        int highestGrade = (row.get("Highest Grade") != null) ? (Integer) row.get("Highest Grade") : 0;
        int range1Count = (row.get("0-9") != null) ? (int) row.get("0-9") : 0;
        int range2Count = (row.get("10-19") != null) ? (int) row.get("10-19") : 0;
        int range3Count = (row.get("20-29") != null) ? (int) row.get("20-29") : 0;
        int range4Count = (row.get("30-39") != null) ? (int) row.get("30-39") : 0;
        int range5Count = (row.get("40-49") != null) ? (int) row.get("40-49") : 0;
        int range6Count = (row.get("50-59") != null) ? (int) row.get("50-59") : 0;
        int range7Count = (row.get("60-69") != null) ? (int) row.get("60-69") : 0;
        int range8Count = (row.get("70-79") != null) ? (int) row.get("70-79") : 0;
        int range9Count = (row.get("80-89") != null) ? (int) row.get("80-89") : 0;
        int range10Count = (row.get("90-100") != null) ? (int) row.get("90-100") : 0;

        return new ExamStatistics(examId, lecturerID, average, median, lowestGrade, highestGrade, range1Count,
                range2Count, range3Count, range4Count, range5Count, range6Count, range7Count, range8Count,
                range9Count, range10Count);
    }

    // Getters and setters

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public int getLowestGrade() {
        return lowestGrade;
    }

    public void setLowestGrade(int lowestGrade) {
        this.lowestGrade = lowestGrade;
    }

    public int getHighestGrade() {
        return highestGrade;
    }

    public void setHighestGrade(int highestGrade) {
        this.highestGrade = highestGrade;
    }

    public int getRange1Count() {
        return range1Count;
    }

    public void setRange1Count(int range1Count) {
        this.range1Count = range1Count;
    }

    public int getRange2Count() {
        return range2Count;
    }

    public void setRange2Count(int range2Count) {
        this.range2Count = range2Count;
    }

    public int getRange3Count() {
        return range3Count;
    }

    public void setRange3Count(int range3Count) {
        this.range3Count = range3Count;
    }

    public int getRange4Count() {
        return range4Count;
    }

    public void setRange4Count(int range4Count) {
        this.range4Count = range4Count;
    }

    public int getRange5Count() {
        return range5Count;
    }

    public void setRange5Count(int range5Count) {
        this.range5Count = range5Count;
    }

    public int getRange6Count() {
        return range6Count;
    }

    public void setRange6Count(int range6Count) {
        this.range6Count = range6Count;
    }

    public int getRange7Count() {
        return range7Count;
    }

    public void setRange7Count(int range7Count) {
        this.range7Count = range7Count;
    }

    public int getRange8Count() {
        return range8Count;
    }

    public void setRange8Count(int range8Count) {
        this.range8Count = range8Count;
    }

    public int getRange9Count() {
        return range9Count;
    }

    public void setRange9Count(int range9Count) {
        this.range9Count = range9Count;
    }

    public int getRange10Count() {
        return range10Count;
    }

    public void setRange10Count(int range10Count) {
        this.range10Count = range10Count;
    }
}
