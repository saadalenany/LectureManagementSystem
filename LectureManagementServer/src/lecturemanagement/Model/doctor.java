package lecturemanagement.Model;

public class doctor extends user {

    int doctor_id ;
    long numberofquizes;
    String doctor_course ;

    public doctor(int doctor_id, String doctor_course) {
        this.doctor_id = doctor_id;
        this.doctor_course = doctor_course;
    }

    public doctor(int doctor_id, String doctor_course, long numberofquizes) {
        this.doctor_id = doctor_id;
        this.numberofquizes = numberofquizes;
        this.doctor_course = doctor_course;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_course() {
        return doctor_course;
    }

    public void setDoctor_course(String doctor_course) {
        this.doctor_course = doctor_course;
    }

    public long getNumberofquizes() {
        return numberofquizes;
    }
/**
    public void shareSlide(slides slide){}
    public void setQuiz(quiz q){}
    public void setSlides(slides slide){}
    public ArrayList<quiz> getAllQuizesAnswers(){return null;}
    public question getQuestion(){return null;}
    public ArrayList<int> getAbsence(){return null;}
**/
}
