/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.OnlineStudent.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lecturemanagement.Server.Main.Control.ReceiveClientSignup;
import lecturemanagement.Utillity.ScreenTools;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class OnlineFXMLController implements Initializable {

    @FXML
    private BorderPane onlineInfoBox;

    @FXML
    private VBox OnlineStudentPane;

    @FXML
    private Rectangle SignUpCounterRect;

    @FXML
    private ScrollPane OnlineScrollPane;

    @FXML
    private StackPane numOfRequestPane;

    @FXML
    private Line line;

    @FXML
    private MenuBar AddMenuBar;

    @FXML
    private BorderPane OnlineListRoot;

    @FXML
    private VBox requestBox;

    @FXML
    private Text SignUpCounterText;

    @FXML
    private Text OnlineCounterTxt;

    //----------------------------------------
    private final String AddImgPath = "/resource/add.png";
    public static final int MAIN_CHAT_WIDTH = 260;
    private final int HEIGHT = 40;
    private boolean isLong = false;
    private double longHeight = 0;
    private ScreenTools screen;

    @FXML
    void onlineInfoBoxAction(MouseEvent event) {
        if (isLong) {     // if long , make it small 
            makeItSmall();
        } else {
            makeItLong();
        }
        isLong = !isLong;
    }

    private void makeItLong() {
        Stage stage = (Stage) onlineInfoBox.getScene().getWindow();
        stage.setY(screen.getScreenHeight() - longHeight - screen.getTaskBarHeight());
        stage.setHeight(longHeight);
        //------------------------------------------------------------
        OnlineListRoot.setPrefHeight(longHeight);
        OnlineStudentPane.setPrefHeight(longHeight - screen.getTaskBarHeight());
        OnlineScrollPane.setVisible(true);
        line.setVisible(true);
    }

    private void makeItSmall() {
        Stage stage = (Stage) onlineInfoBox.getScene().getWindow();
        stage.setHeight(HEIGHT);
        stage.setY(screen.getScreenHeight() - stage.getHeight() - screen.getTaskBarHeight());
        //--------------------------------------------------------------
        OnlineListRoot.setPrefHeight(HEIGHT);
        OnlineScrollPane.setVisible(false);
        line.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //iniliaze variables 
        ref.RequestSignUpBox = requestBox;
        ref.SignUpCounterPane = numOfRequestPane;
        ref.SignUpCounterText = SignUpCounterText;
        ref.SignUpCounterRect = SignUpCounterRect;
        // start listen to Sign up student 
        new ReceiveClientSignup().startListen();  // listen for signUp student
        numOfRequestPane.setVisible(false);
        // set add Student Image 
        ImageInput img = new ImageInput(new Image(AddImgPath));
        AddMenuBar.setEffect(img);
        //-------------------------------------
        OnlineListRoot.setPrefSize(MAIN_CHAT_WIDTH, HEIGHT);
        OnlineScrollPane.setVisible(false);
        line.setVisible(false);
        //-------------------------------------
        ref.onlineStudentVBox = OnlineStudentPane;
        ref.OnlineCounterText = this.OnlineCounterTxt;
        //----------------------------------
        screen = new ScreenTools();
        longHeight = (int) (screen.getScreenHeight() / 2.0);
    }

}
