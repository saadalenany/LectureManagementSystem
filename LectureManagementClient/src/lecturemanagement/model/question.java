package Server.model;

public class question {

    int question_id , question_ownerid;
    String question_data , reply;

    public question(int question_id, String question_data,int question_ownerid) {
        this.question_id = question_id;
        this.question_ownerid = question_ownerid;
        this.question_data = question_data;
    }

    public question(int question_id, String question_data, int question_ownerid, String reply) {
        this.question_id = question_id;
        this.question_ownerid = question_ownerid;
        this.question_data = question_data;
        this.reply = reply;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuestion_ownerid() {
        return question_ownerid;
    }

    public void setQuestion_ownerid(int question_ownerid) {
        this.question_ownerid = question_ownerid;
    }

    public String getQuestion_data() {
        return question_data;
    }

    public void setQuestion_data(String question_data) {
        this.question_data = question_data;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    
}
