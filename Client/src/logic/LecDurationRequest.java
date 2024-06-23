package logic;

import java.util.Date;
import java.util.Map;

public class LecDurationRequest {
    private int requestNum;
    private String examCode;
    private Date date;
    private int additionalDuration;
    private String lecDurationRequest;
    private int status;

    private static LecDurationRequest instance;

    
    public static synchronized LecDurationRequest getInstance(int requestNum, String examCode, Date date, int additionalDuration, String lecDurationRequestCol, int status) {
        instance = new LecDurationRequest(requestNum, examCode, date, additionalDuration, lecDurationRequestCol, status);
        return instance;
    }
    private LecDurationRequest(int requestNum, String examCode, Date date, int additionalDuration,
                               String lecDurationRequest, int status) {
        this.requestNum = requestNum;
        this.examCode = examCode;
        this.date = date;
        this.additionalDuration = additionalDuration;
        this.lecDurationRequest = lecDurationRequest;
        this.status = status;
    }

    public static LecDurationRequest convertToLecDurationRequest(Map<String, Object> row) {
        int requestNum = (int) row.get("requestNum");
        String examCode = (String) row.get("examCode");
        Date date = (Date) row.get("date");
        int additionalDuration = (int) row.get("additionalDuration");
        String lecDurationRequest = (String) row.get("lecDurationRequestCol");
        int status = (int) row.get("status");

        instance.setRequestNum(requestNum);
        instance.setExamCode(examCode);
        instance.setDate(date);
        instance.setAdditionalDuration(additionalDuration);
        instance.setLecDurationRequest(lecDurationRequest);
        instance.setStatus(status);

        return instance;
    }

    // Getters and setters

    public int getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAdditionalDuration() {
        return additionalDuration;
    }

    public void setAdditionalDuration(int additionalDuration) {
        this.additionalDuration = additionalDuration;
    }

    public String getLecDurationRequest() {
        return lecDurationRequest;
    }

    public void setLecDurationRequest(String lecDurationRequest) {
        this.lecDurationRequest = lecDurationRequest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LecDurationRequest [requestNum=" + requestNum + ", examCode=" + examCode + ", date=" + date
                + ", additionalDuration=" + additionalDuration + ", lecDurationRequest=" + lecDurationRequest
                + ", status=" + status + "]";
    }
}
