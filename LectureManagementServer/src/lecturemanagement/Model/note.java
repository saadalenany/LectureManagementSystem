package lecturemanagement.Model;

public class note {

    int note_id , note_slide , note_ownerid ;
    String note_data ;

    public note(int note_id, String note_data, int note_slide) {
        this.note_id = note_id;
        this.note_slide = note_slide;
        this.note_data = note_data;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getNote_ownerid() {
        return note_ownerid;
    }

    public void setNote_ownerid(int note_ownerid) {
        this.note_ownerid = note_ownerid;
    }

    public String getNote_data() {
        return note_data;
    }

    public void setNote_data(String note_data) {
        this.note_data = note_data;
    }

    

}
