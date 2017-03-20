package lecturemanagement.Model;

import java.util.ArrayList;

public class quizquestion extends quiz {

    int quizquestion_id , quiz_id;
    String question_data , doctor_ans;
    String choicedata ;
    long  numberofchoices  ;

    public quizquestion(int question_id, int numberofchoices, String question_data, String choicedata, String doctor_ans, int quiz_id){
        this.quizquestion_id = question_id;
        this.numberofchoices = numberofchoices;
        this.question_data = question_data;
        this.choicedata = choicedata;
        this.doctor_ans = doctor_ans;
        this.quiz_id = quiz_id;
    }

    public int getQuizquestion_id() {
        return quizquestion_id;
    }

    public void setQuizquestion_id(int quizquestion_id) {
        this.quizquestion_id = quizquestion_id;
    }

    public long getNumberofchoices() {
        return numberofchoices;
    }

    public void setNumberofchoices(long numberofchoices) {
        this.numberofchoices = numberofchoices;
    }

    public String getQuestion_data() {
        return question_data;
    }

    public void setQuestion_data(String question_data) {
        this.question_data = question_data;
    }

    public String getDoctor_ans() {
        return doctor_ans;
    }

    public void setDoctor_ans(String doctor_ans) {
        this.doctor_ans = doctor_ans;
    }

    public String getChoicedata() {
        return choicedata;
    }

    public void setChoicedata(String choicedata) {
        this.choicedata = choicedata;
    }

    @Override
    public int getQuiz_id() {
        return quiz_id;
    }

    @Override
    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

}
