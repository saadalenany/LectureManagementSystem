package lecturemanagement.Model;


public class student extends user {

    int student_id ;
    long academic_year ;

    public student(int student_id, long academic_year) {
        this.student_id = student_id;
        this.academic_year = academic_year;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public long getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(long academic_year) {
        this.academic_year = academic_year;
    }
/**
    public void setPresence(){}
    public void setQuizAnswers(quiz q){}
    public void addNote(note n){}
    public void askQuestion(question q){}
    public slides downloadSlides(){return null;}
**/
}
