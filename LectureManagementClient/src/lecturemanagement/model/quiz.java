package Server.model;

public class quiz {

    int quiz_id , doctor_id;
    String quiz_name , quiz_course ;

    public quiz(){
    }

    public quiz(int quiz_id, String quiz_name, String quiz_course, int doctor_id) {
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.quiz_course = quiz_course;
        this.doctor_id = doctor_id;
    }

    public quiz(int quiz_id, String quiz_name, String quiz_course) {
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.quiz_course = quiz_course;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_course() {
        return quiz_course;
    }

    public void setQuiz_course(String quiz_course) {
        this.quiz_course = quiz_course;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

}
