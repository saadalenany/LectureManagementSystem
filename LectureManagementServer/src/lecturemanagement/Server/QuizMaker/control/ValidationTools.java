/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.QuizMaker.control;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import lecturemanagement.Utillity.notifier;

/**
 *
 * @author Amr
 */
public class ValidationTools {

    private final String ErrorColor = "#B40404";

    public boolean rightAnsIsvalid(String rightAns, int AnswerSize) {
        if (!rightAns.matches("\\d+")) {
            new notifier("error", "check your right answer field ");
            return false;
        }
        if (rightAns.trim().length() == 0 || Integer.parseInt(rightAns) > AnswerSize) {
            new notifier("error", "check your right answer field ");
            return false;
        }
        return true;

    }

    public boolean isQuestionValid(ArrayList<Question> QuestionList) {
        boolean flag = true;
        for (int i = 0; i < QuestionList.size(); i++) {
            JFXTextField questionData = QuestionList.get(i).getQuestionDataField();
            JFXTextField RightAns = QuestionList.get(i).getRightAnsField();
            ArrayList<CustomRadioButton> choicesList = QuestionList.get(i).getRadioChoices();
            //--------------------------------------------------
            RightAns.setUnFocusColor(Color.web("#000"));
            if (!RightAns.getText().matches("\\d+") || (Integer.parseInt(RightAns.getText()) > choicesList.size())) {
                RightAns.setUnFocusColor(Color.web(ErrorColor));
                flag = false;

            }

            //----------------------------------------------------
            questionData.setUnFocusColor(Color.web("#000"));
            if (questionData.getText().trim().equals("")) {
                questionData.setUnFocusColor(Color.web(ErrorColor));
                flag = false;
            }
            //----------------------------------------------------
            for (int j = 0; j < choicesList.size(); j++) {
                JFXRadioButton rb = choicesList.get(j).getRadioButton();
                rb.setStyle("-fx-text-fill : #fff");
                if (choicesList.get(j).getText().trim().equals("")) {
                    rb.setStyle("-fx-text-fill : " + ErrorColor);
                    flag = false;
                }
            }
        }
        return flag;
    }

}
