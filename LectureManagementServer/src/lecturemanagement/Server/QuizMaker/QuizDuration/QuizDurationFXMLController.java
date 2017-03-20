/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.QuizMaker.QuizDuration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lecturemanagement.Server.Main.Control.LogoutStudent;
import lecturemanagement.Utillity.FxDialogs;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class QuizDurationFXMLController implements Initializable {

    @FXML
    private Text StudentCounterTxt;

    @FXML
    private Text TimeTxt;

    @FXML
    private BorderPane RootPane;

    //-------------------------------
    private Timeline time;
    private int minute, second;
    private int numberOfStudent;
    private int submitedStudent;
    private int QuizDuration_WIDTH = 115;
    private int QuizDuration_HEIGHT = 65;
    private String QuizTerminator_markSending = "QUIZ_TERMINATE";
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitedStudent = 0;
        RootPane.setPrefSize(QuizDuration_WIDTH, QuizDuration_HEIGHT);
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
            TimeTxt.setText(minute + " : " + second);
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
    }

    public String getStudentCounterTxt() {
        return StudentCounterTxt.getText();
    }

    public void setStudentCounterTxt(String value) {
        this.StudentCounterTxt.setText(value);
    }

    public String getTimeTxt() {
        return TimeTxt.getText();
    }

    public void setTimeTxt(String TimeTxt) {
        TimeTransition();
        minute = Integer.parseInt(TimeTxt);
        this.TimeTxt.setText(TimeTxt);

    }

    private void QuizTimeOut() {
        new notifier("info", "Quiz time is over");
        ref.QuizRunning = false;
        stage = (Stage) RootPane.getScene().getWindow();
        stage.close();
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setNumberOfStudent(int numberOfStudent) {

        StudentCounterTxt.setText(numberOfStudent + "/" + submitedStudent);
        this.numberOfStudent = numberOfStudent;
    }

    public void newSubmitedStudent() {
        submitedStudent++;
        StudentCounterTxt.setText(numberOfStudent + "/" + submitedStudent);
        if (submitedStudent == numberOfStudent) {
            new notifier("info", "student submited there Answer " + submitedStudent);
            ref.QuizRunning = false;
            stage.close();
        }
    }

    @FXML
    void StopQuizAction(ActionEvent event) {
        if (FxDialogs.showConfirm(" info ", " Do you want get grades ?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
            SendTerminate(1);
        } else {
            SendTerminate(0);
        }
        QuizTimeOut();
    }

    private void SendTerminate(int status) {
        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            try {
                DataOutputStream out = new DataOutputStream(SendQuizSlideNumberEndLectureSocket.get(i).getOutputStream());
                out.writeUTF(QuizTerminator_markSending + "-" + status);
            } catch (SocketException e) {
                // from start only because it first point 
                new LogoutStudent().removeLogoutStudent(SendQuizSlideNumberEndLectureSocket.get(i));
                //-----------------------------
            } catch (IOException ex) {

            }

        }

    }

}
