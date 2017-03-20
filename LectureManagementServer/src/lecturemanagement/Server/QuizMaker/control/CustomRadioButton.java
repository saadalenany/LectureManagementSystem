/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.QuizMaker.control;

import com.jfoenix.controls.JFXRadioButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Amr
 */
public class CustomRadioButton extends HBox {

    private JFXRadioButton rb;
    private TextField ChangedField;

    public CustomRadioButton(String data, ToggleGroup group) {

        this.setAlignment(Pos.CENTER_LEFT);
        CreateRadioButton(data, group);
        ClickAction();
    }

    private void CreateRadioButton(String data, ToggleGroup group) {
        rb = new JFXRadioButton(data);
        rb.setFont(Font.font(14));
        rb.setStyle("-fx-text-fill : #fff");
        rb.setToggleGroup(group);
        rb.setSelectedColor(Color.web("fff"));
        this.getChildren().add(rb);
    }

    private TextField CreateTextField(String txt) {
        ChangedField = new TextField();
        ChangedField.setPromptText(txt);
        ChangedField.setFont(Font.font(13));
        VBox.setMargin(ChangedField, new Insets(0, 0, 0, 50));
        ChangedField.setStyle("-fx-background-color :  #5BA6D7 ; "
                + "-fx-border-color : #000; "
                + "  -fx-prompt-text-fill: #2E2E2E;");
        ChangedField.setOnMouseClicked(e -> {
        });
        return ChangedField;
    }

    private void ClickAction() {
        rb.setOnMouseClicked(e -> {
            String preTxt = rb.getText();
            this.getChildren().clear();
            this.getChildren().add(CreateTextField(preTxt));
        });

    }

    public void transformTextFieldToRadio() {
        String preTxt = ChangedField.getText();
        if (!preTxt.equals("")) {
            rb.setText(preTxt);
        }
        this.getChildren().clear();
        this.getChildren().add(rb);

    }

    public String getText() {
        return rb.getText();
    }

    public JFXRadioButton getRadioButton() {
        return rb;
    }

    public void setText(String txt) {
        rb.setText(txt);
    }
}
