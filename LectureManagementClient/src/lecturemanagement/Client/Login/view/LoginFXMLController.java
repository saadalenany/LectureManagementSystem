/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Login.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import lecturemanagement.Client.Login.Control.LoginSocket;
import lecturemanagement.Utility.LoadFXML;
import lecturemanagement.ref;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXButton;

/**
 *
 * @author Amr
 */
public class LoginFXMLController implements Initializable {

    @FXML
    private JFXTextField usernameText;

    @FXML
    private JFXPasswordField passText;

    @FXML
    private JFXButton LoginButton;

    //----------------------------------------------
    private Scene scene;
    private final String SignUpPath = "/lecturemanagement/Client/Signup/view/SignUpFXML.fxml";

    @FXML
    private void LoginButtonAction(ActionEvent event) {
        scene = LoginButton.getScene();
        ref.mainScene = scene;   
        LoginSocket login = new LoginSocket(usernameText.getText().trim(), passText.getText(), scene);
        login.SendLoginInfo();  // Start login Socket

    }

    @FXML
    void SignupButtonAction(ActionEvent event) {
        scene = LoginButton.getScene();
        new LoadFXML().LoadFXML(scene, SignUpPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
