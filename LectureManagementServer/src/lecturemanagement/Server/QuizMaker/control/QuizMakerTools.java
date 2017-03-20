/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.QuizMaker.control;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lecturemanagement.Server.QuizMaker.QuizDuration.QuizDurationFXMLController;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.ScreenTools;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;

/**
 *
 * @author Amr
 */
public class QuizMakerTools {
    
    private final String QuizDurationPath = "/lecturemanagement/Server/QuizMaker/QuizDuration/QuizDurationFXML.fxml";
    private QuizDurationFXMLController QuizDurationController;
    
    public JFXTextField BulidRightAnswerfield() {
        //right answer text field section
        JFXTextField rightAnsTxt = new JFXTextField();
        rightAnsTxt.setStyle("-fx-text-fill : #fff");
        rightAnsTxt.setFocusColor(Color.web("fff"));
        rightAnsTxt.setUnFocusColor(Color.web("#89f4eb"));
        rightAnsTxt.setPromptText("Correct answer number");
        rightAnsTxt.setMaxWidth(Region.USE_PREF_SIZE);
        return rightAnsTxt;
    }
    
    public JFXTextField BulidQuestionDataField() {
        JFXTextField questionData = new JFXTextField();
        questionData.setPrefSize(150, 30);
        questionData.setStyle("-fx-text-fill : #fff");
        questionData.setPromptText("Enter Question");
        return questionData;
    }
    
    public void LoadQuizDurationStage(String Time) {
        ScreenTools screen = new ScreenTools();
        LoadFXML loader = new LoadFXML();
        Stage stage = loader.LoadFXMLRootedStage(QuizDurationPath);
        QuizDurationController = (QuizDurationFXMLController) loader.getController();
        QuizDurationController.setNumberOfStudent(SendQuizSlideNumberEndLectureSocket.size());
        QuizDurationController.setTimeTxt(Time);
        stage.setX(screen.getScreenWidth() - stage.getWidth());
        stage.setY(0);
        
    }
    
    public QuizDurationFXMLController getQuizDurationController() {
        return QuizDurationController;
    }
    
}
