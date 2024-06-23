package logic;

import java.io.Serializable;
import java.util.Map;

public class CheckingCopies implements Serializable {
	private Integer autoincrement;
	private Integer ExamResultID;
	private Integer startnum;
	private String ExamID;
	private String Student1ID;
	private String Student2ID;
	private String SimilarPrecent;
	
	public CheckingCopies(Integer autoincrement, Integer examResultID, Integer startnum, String examID,
			String student1id, String student2id, String similarPrecent) {
		super();
		this.autoincrement = autoincrement;
		ExamResultID = examResultID;
		this.startnum = startnum;
		ExamID = examID;
		Student1ID = student1id;
		Student2ID = student2id;
		SimilarPrecent = similarPrecent;
	}

	public static CheckingCopies convertToCheckingCopies(Map<String, Object> row) {
        Integer autoincrement = (Integer) row.get("autoincrement");
        Integer examresultID = (Integer) row.get("ExamResultID");
        Integer startnum = (Integer) row.get("startnum");
        String examID = (String) row.get("ExamID");
        String student1ID = (String) row.get("Student1ID");
        String student2ID = (String) row.get("Student2ID");
        String similarPercent = (String) row.get("SimilarPrecent");
        return new CheckingCopies(autoincrement, examresultID, startnum, examID, student1ID, student2ID, similarPercent);
    }

	public Integer getAutoincrement() {
		return autoincrement;
	}

	public void setAutoincrement(Integer autoincrement) {
		this.autoincrement = autoincrement;
	}

	public Integer getExamResultID() {
		return ExamResultID;
	}

	public void setExamResultID(Integer examResultID) {
		ExamResultID = examResultID;
	}

	public Integer getStartnum() {
		return startnum;
	}

	public void setStartnum(Integer startnum) {
		this.startnum = startnum;
	}

	public String getExamID() {
		return ExamID;
	}

	public void setExamID(String examID) {
		ExamID = examID;
	}

	public String getStudent1ID() {
		return Student1ID;
	}

	public void setStudent1ID(String student1id) {
		Student1ID = student1id;
	}

	public String getStudent2ID() {
		return Student2ID;
	}

	public void setStudent2ID(String student2id) {
		Student2ID = student2id;
	}

	public String getSimilarPrecent() {
		return SimilarPrecent;
	}

	public void setSimilarPrecent(String similarPrecent) {
		SimilarPrecent = similarPrecent;
	}

	@Override
	public String toString() {
		return "CheckingCopies [autoincrement=" + autoincrement + ", ExamResultID=" + ExamResultID + ", startnum="
				+ startnum + ", ExamID=" + ExamID + ", Student1ID=" + Student1ID + ", Student2ID=" + Student2ID
				+ ", SimilarPrecent=" + SimilarPrecent + "]";
	}
}
