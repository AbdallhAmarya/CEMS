package logic;

import java.util.Map;

public class manual_exam {
    private static manual_exam instance;
    private String exam_id;
    private String sbuject;

    private manual_exam(String exam_id, String sbuject) {
        this.exam_id = exam_id;
        this.sbuject = sbuject;
    }

    public static synchronized manual_exam getInstance(String exam_id, String sbuject) {
        instance = new manual_exam(exam_id, sbuject);
        return instance;
    }

    public static manual_exam convertToManualExam(Map<String, Object> row) {
        String exam_id = (String) row.get("exam_id");
        String sbuject = (String) row.get("subject");
        return getInstance(exam_id, sbuject);
    }
	public String getExam_id() {
		return exam_id;
	}
	public void setExam_id(String exam_id) {
		this.exam_id = exam_id;
	}
	public String getSbuject() {
		return sbuject;
	}
	public void setSbuject(String sbuject) {
		this.sbuject = sbuject;
	}
	
	        
	        

}
