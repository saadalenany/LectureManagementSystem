/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.QuizMaker.control;

import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 *
 * @author Amr
 */
public class Question extends VBox {

    private final String DefaultMsg = "click to set choice";
    private int NumberOfChoices;
    private JFXTextField rightAnsField, QuestionDataField;
    private QuizMakerTools buildTool;
    private ArrayList<CustomRadioButton> RadioChoices;

    public Question(int NumberOfChoices) {
        buildTool = new QuizMakerTools();
        RadioChoices = new ArrayList<>();
        this.NumberOfChoices = NumberOfChoices;
        this.setPadding(new Insets(10, 0, 0, 10));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        CreateQuestion();
    }

    private void CreateQuestion() {

        // RightAns
        rightAnsField = buildTool.BulidRightAnswerfield();
        // Question Data
        QuestionDataField = buildTool.BulidQuestionDataField();
        VBox ChoicesButtonsBox = new VBox();
        ChoicesButtonsBox.setAlignment(Pos.CENTER_LEFT);
        ChoicesButtonsBox.setSpacing(10);
        // radio Button 
        ToggleGroup group = new ToggleGroup();
        for (int i = 0; i < NumberOfChoices; i++) {
            CustomRadioButton crb = new CustomRadioButton(DefaultMsg, group);
            ChoicesButtonsBox.getChildren().add(crb);
            RadioChoices.add(crb);
        }
        this.getChildren().addAll(rightAnsField, QuestionDataField, ChoicesButtonsBox);
    }

    public int getNumberOfChoices() {
        return NumberOfChoices;
    }

    public void setNumberOfChoices(int NumberOfChoices) {
        this.NumberOfChoices = NumberOfChoices;
    }

    public JFXTextField getRightAnsField() {
        return rightAnsField;
    }

    public void setRightAnsField(String rightAnsTxt) {
        this.rightAnsField.setText(rightAnsTxt);
    }

    public JFXTextField getQuestionDataField() {
        return QuestionDataField;
    }

    public void setQuestionDataField(String QuestionDataTxt) {
        this.QuestionDataField.setText(QuestionDataTxt);
    }

    public ArrayList<CustomRadioButton> getRadioChoices() {
        return RadioChoices;
    }

    public void setRadioChoices(ArrayList<String> RadioChoicesTxt) {
        for (int i = 0; i < NumberOfChoices; i++) {
            RadioChoices.get(i).setText(RadioChoicesTxt.get(i));
        }
    }

}
