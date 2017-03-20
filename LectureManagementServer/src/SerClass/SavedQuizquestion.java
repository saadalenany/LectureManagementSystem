/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SerClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Saad
 */
public class SavedQuizquestion implements Serializable {

    private static final long serialVersionUID = -5399605111499562999L;

   private String question;
   private ArrayList<String> ChoicesData;
   private String rightans;
   private String QuizName ;

    public SavedQuizquestion(String question, ArrayList<String> data, String rightans) {
        this.question = question;
        this.ChoicesData = data;
        this.rightans = rightans;
    }

    public String getRightans() {
        return rightans;
    }

    public void setRightans(String rightans) {
        this.rightans = rightans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getChoicesData() {
        return ChoicesData;
    }

    public void setChoicesData(ArrayList<String> ChoicesData) {
        this.ChoicesData = ChoicesData;
    }


    public String getQuizName() {
        return QuizName;
    }

    public void setQuizName(String QuizName) {
        this.QuizName = QuizName;
    }

    
    
    @Override
    public String toString() {
        return "SavedQuizquestion{" + "question=" + question + ", data=" + ChoicesData + ", rightans=" + rightans + '}';
    }

}
