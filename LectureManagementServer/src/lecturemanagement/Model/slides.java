package lecturemanagement.Model;

public class slides {

    int slide_id, doctor_id;
    String slide_name, slide_path, slide_course;
    long numberofwatechers ;

    public slides(int slide_id, String slide_name, String slide_path, String slide_course, long numberofwatechers) {
        this.slide_id = slide_id;
        this.numberofwatechers = numberofwatechers;
        this.slide_name = slide_name;
        this.slide_path = slide_path;
        this.slide_course = slide_course;
    }

    public slides(int slide_id, String slide_name, String slide_path, String slide_course, long numberofwatechers, int doctor_id) {
        this.slide_id = slide_id;
        this.numberofwatechers = numberofwatechers;
        this.slide_name = slide_name;
        this.slide_path = slide_path;
        this.slide_course = slide_course;
        this.doctor_id = doctor_id;
    }

    public int getSlide_id() {
        return slide_id;
    }

    public void setSlide_id(int slide_id) {
        this.slide_id = slide_id;
    }

    public long getNumberofwatechers() {
        return numberofwatechers;
    }

    public void setNumberofwatechers(long numberofwatechers) {
        this.numberofwatechers = numberofwatechers;
    }

    public String getSlide_name() {
        return slide_name;
    }

    public void setSlide_name(String slide_name) {
        this.slide_name = slide_name;
    }

    public String getSlide_path() {
        return slide_path;
    }

    public void setSlide_path(String slide_path) {
        this.slide_path = slide_path;
    }

    public String getSlide_course() {
        return slide_course;
    }

    public void setSlide_course(String slide_course) {
        this.slide_course = slide_course;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

}
