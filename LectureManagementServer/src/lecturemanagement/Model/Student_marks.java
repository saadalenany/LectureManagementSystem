package lecturemanagement.Model;

public class Student_marks {

    String student_id;
    int quiz_id ;
    long student_mark;

    public Student_marks(String student_id, int quiz_id, long student_mark) {
        this.student_id = student_id;
        this.quiz_id = quiz_id;
        this.student_mark = student_mark;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public long getStudent_mark() {
        return student_mark;
    }

    public void setStudent_mark(long student_mark) {
        this.student_mark = student_mark;
    }

}
