package logic;

public class ExamQuestion {
    private static ExamQuestion instance;
    private Question question;
    private String point;

    private ExamQuestion(Question question, String point) {
        this.question = question;
        this.point = point;
    }

    public static ExamQuestion getInstance(Question question, String point) {
        instance = new ExamQuestion(question, point);
        return instance;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getQuestionNum() {
        return question.getQuestionNum();
    }

    public String getInfo() {
        return question.getInfo();
    }

    public String getSubject() {
        return question.getSubject();
    }

    public String getCourseName() {
        return question.getCourseName();
    }

    public String getQuestionText() {
        return question.getQuestionText();
    }

    public String getLecturerID() {
        return question.getLecturerID();
    }

    public String getAnswer1() {
        return question.getAnswer1();
    }

    public String getAnswer2() {
        return question.getAnswer2();
    }

    public String getAnswer3() {
        return question.getAnswer3();
    }

    public String getAnswerCorrect() {
        return question.getAnswerCorrect();
    }
}
