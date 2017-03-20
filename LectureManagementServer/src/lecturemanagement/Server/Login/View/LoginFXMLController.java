/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Login.View;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import static lecturemanagement.Server.Main_.MainFXMLController.RECT_WIDTH;
import lecturemanagement.Utillity.ScreenTools;

/**
 *
 * @author Amr
 */
public class LoginFXMLController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private BorderPane root;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton LoginButton;
    //---------------------------------------------- 
    private final String ServerMainPath = "/lecturemanagement/Server/Main/view/MainFXML.fxml";
    private final String ServerMainPath_ = "/lecturemanagement/Server/Main_/MainFXML.fxml";
    private final String ServerSignUpPath = "/lecturemanagement/Server/Signup/SignUpFXML.fxml";
    private final String OnlineStudentPath = "/lecturemanagement/Server/OnlineStudent/view/OnlineFXML.fxml";
    private StorageManager stg;
    private Scene scene;

    @FXML
    private void LoginButtonAction(ActionEvent event) {
        scene = LoginButton.getScene();
        if (!stg.checkDoctorLogIn(usernameField.getText(), passwordField.getText())) {
            new notifier("Warning", "please , check your user name and password or SignUp  ");
            return;
        }
        new notifier("Wellcome", "You are Logged in ");
        ref.username = usernameField.getText();
        // --------- main  
        LoadFXML loader = new LoadFXML();
        ref.rootStage = loader.LoadFXMLWithTransparentSrage(ServerMainPath_);
        relocateMainStage();
        Stage stage = (Stage) this.scene.getWindow();
        stage.close();
        // --------- onlineStudent  
        LoadOnlineStudent();
    }

    @FXML
    private void signUpAction(ActionEvent event) {
        scene = LoginButton.getScene();
        new LoadFXML().LoadFXML(scene, ServerSignUpPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stg = new StorageManager();
    }

    private void LoadOnlineStudent() {
        ScreenTools screen = new ScreenTools();
        Stage stage = new LoadFXML().LoadFXMLRootedStage(OnlineStudentPath);
        stage.setX(screen.getScreenWidth() - stage.getWidth());
        stage.setY(screen.getScreenHeight() - stage.getHeight() - screen.getTaskBarHeight());
    }

    private void relocateMainStage() {
        ScreenTools screen = new ScreenTools();
        int X = (int) (screen.getScreenWidth() / 2 - RECT_WIDTH / 2);
        ref.rootStage.setX(X);
        ref.rootStage.setY(-20);
    }

}
