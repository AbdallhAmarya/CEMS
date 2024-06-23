package logic;

import java.util.Map;

public class sms {
    private static sms instance;
    private String department;
    private String lecturer;
    private String examId;
    private int newDuration;
    private String reason;
    private String response;

    private sms(String department, String lecturer, String examId, int newDuration, String reason, String response) {
        this.department = department;
        this.lecturer = lecturer;
        this.examId = examId;
        this.newDuration = newDuration;
        this.reason = reason;
        this.response = response;
    }

    public static synchronized sms getInstance(String department, String lecturer, String examId, int newDuration,
            String reason, String response) {
        instance = new sms(department, lecturer, examId, newDuration, reason, response);
        return instance;
    }

    public String getDepartment() {
        return department;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getExamId() {
        return examId;
    }

    public int getNewDuration() {
        return newDuration;
    }

    public String getReason() {
        return reason;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static sms convertToSMS(Map<String, Object> row) {
        String department = (String) row.get("Department");
        String lecturer = (String) row.get("lecturer");
        String examId = (String) row.get("exam_id");
        Integer newDurationValue = (Integer) row.get("new duration");
        int newDuration = newDurationValue != null ? newDurationValue.intValue() : 0;
        String reason = (String) row.get("reason");
        String response = (String) row.get("response");
        return getInstance(department, lecturer, examId, newDuration, reason, response);
    }
}
