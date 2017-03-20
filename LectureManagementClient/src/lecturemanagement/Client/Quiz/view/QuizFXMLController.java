/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Quiz.view;

import SerClass.SavedQuizquestion;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lecturemanagement.Client.Quiz.control.QuestionPane;
import lecturemanagement.Utility.notifier;
import lecturemanagement.ref;
import com.jfoenix.controls.JFXButton;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static lecturemanagement.Client.Main.control.RecieveSlideNumberAndQuiz.SlideNumberAndQuizSocket;
import static lecturemanagement.ref.SlidePane;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class QuizFXMLController implements Initializable {

    @FXML
    private VBox QuizBox;
    @FXML
    private Label timeTxt;
    @FXML
    private StackPane MainCenterPane;
    @FXML
    private JFXButton ReturnButton;

    //----------------------------------
    private ArrayList<SavedQuizquestion> Quiz;
    private ArrayList<QuestionPane> QuizQuestionListPane;
    private Timeline time;
    private int AllowToShowResult;
    private int second, minute;
    private int numberOfRightAns = 0;
    private final String gradeMarker = "-";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        QuizQuestionListPane = new ArrayList<>();
        ReturnButton.setDisable(true);
    }

    @FXML
    void submitButtonAction(ActionEvent event) {
        QuizTimeOut();
    }

    @FXML
    void ReturnButtonAction(ActionEvent event) {
        System.out.println(SlidePane);
        ref.RootPane.setCenter(ref.SlidePane);
    }

    public void setQuiz(ArrayList<SavedQuizquestion> Quiz) {
        this.Quiz = Quiz;
        drawQuetions();

    }

    public void setTime(String Time) {
        minute = Integer.parseInt(Time);
        TimeTransition();

    }

    public void setAllowToShowResult(int AllowToShowResult) {
        this.AllowToShowResult = AllowToShowResult;
    }

    private void TimeTransition() {
        time = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            if (second == 0 && minute == 0) {
                QuizTimeOut();
            }
            if (second == 0) {
                minute--;
                second = 60;
            }
            second--;
            timeTxt.setText(minute + " : " + second);
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
    }

    private void QuizTimeOut() {
        new notifier("info", " Quiz Time out ");
        time.stop();
        calculateGrade();
        SendGrade();
        ref.QuizRunning = false;
    }

    public void QuizTerminatorFromDoctor(String status) {
        Platform.runLater(() -> {
            new notifier("info", " Quiz is Stopped from doctor ");
            time.stop();
            if (status.equals("1")) {
                calculateGrade();
                SendGrade();
            }
             MainCenterPane.getChildren().clear();
            ReturnButton.setDisable(false);
            timeTxt.setVisible(false);
            ref.QuizRunning = false;
        });
    }

    private void SendGrade() {
        try {
            DataOutputStream out = new DataOutputStream(SlideNumberAndQuizSocket.getOutputStream());
            out.writeUTF(ref.username + gradeMarker + numberOfRightAns);
        } catch (IOException ex) {
            Logger.getLogger(QuizFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculateGrade() {
        for (int i = 0; i < QuizQuestionListPane.size(); i++) {
            if (QuizQuestionListPane.get(i).getSelectedAnswer() == Integer.parseInt(Quiz.get(i).getRightans()) - 1) {
                numberOfRightAns++;
            }
        }
        MainCenterPane.getChildren().clear();
        ReturnButton.setDisable(false);
        timeTxt.setVisible(false);
        if (AllowToShowResult == 0) {
            return;
        }
        Text result = new Text("Your correct Questions is " + numberOfRightAns + " from " + QuizQuestionListPane.size() + " Questions");
        result.setFont(Font.font("Arial", 20));
        result.setFill(Color.RED);
        MainCenterPane.getChildren().add(result);

    }

    private void drawQuetions() {
        for (int i = 0; i < Quiz.size(); i++) {
            QuestionPane question = new QuestionPane(Quiz.get(i).getQuestion(), Quiz.get(i).getChoicesData());
            QuizBox.getChildren().add(question);
            QuizQuestionListPane.add(question);
        }

    }

}
