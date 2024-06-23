package logic;

import java.util.Map;

public class CurrentExam {
    private static CurrentExam instance;
    private String examid;
    private int count;

    private CurrentExam(String examid, int count) {
        this.examid = examid;
        this.count = count;
    }

    public static CurrentExam getInstance(String examid, int count) {
        instance = new CurrentExam(examid, count);
        return instance;
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static CurrentExam convertToCurrentExam(Map<String, Object> row) {
        String examid = (String) row.get("examid");
        int count = row.get("count") != null ? (int) row.get("count") : 0;
        return getInstance(examid, count);
    }
}
