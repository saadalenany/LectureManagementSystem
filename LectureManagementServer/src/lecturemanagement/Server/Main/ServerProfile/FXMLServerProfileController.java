/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.ServerProfile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lecturemanagement.Server.Login.View.LectureManagement;
import lecturemanagement.Server.Login.View.LoginFXMLController;
import lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture;
import lecturemanagement.Server.Main.SlidePane.SlidePaneFXMLController;
import lecturemanagement.Server.Main_.MainFXMLController;
import lecturemanagement.Server.Main_.Test;
import lecturemanagement.Server.login_signUp.view.Sign_logn;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.Utillity.undecoratedStage.undecoratedStageOptions;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Saad
 */
public class FXMLServerProfileController implements Initializable {

    @FXML
    Label username, academic_id, email, phone, dep;

    @FXML
    JFXTextField newacademic_id, newusername, newemail, newphone;

    @FXML
    JFXComboBox newdep;

    @FXML
    JFXButton close;

    @FXML
    JFXPasswordField currentpassword, newpassword, repeatpassword;

    @FXML
    JFXButton update, logout;

    public String pass = null;

    StorageManager sm = new StorageManager();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText(ref.username);
        ResultSet rs = sm.performQuery("SELECT `user_id`, `user_email` , `user_phone` ,`user_password` , `user_department` FROM `user` WHERE `user_name` = '" + ref.username + "' AND `user_status` = 'Server'");
        try {
            while (rs.next()) {
                ref.userID = rs.getString(1);
                academic_id.setText(ref.userID);
                email.setText(rs.getString(2));
                phone.setText(rs.getString(3) + "");
                pass = rs.getString(4);
                dep.setText(rs.getString(5));
                System.out.println(ref.userID + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        newdep.setItems(FXCollections.observableArrayList("CS", "IT", "IS", "OR"));
        close.setOnAction(e -> {
            ClosePaneAction();
        });
    }

    @FXML
    void onUpdateData(ActionEvent event) {
        if (newacademic_id.getText().equals("") || newusername.getText().equals(null)
                || newphone.getText().equals(null) || newemail.getText().equals(null)
                || currentpassword.getText().equals(null) || newdep.getValue().equals(null)
                || newpassword.getText().equals(null) || repeatpassword.getText().equals(null)) {
            new notifier("Error!", "Please fill out all the fields!");
            return;
        }

        boolean flag = false;

        if (newpassword.getText().equals(repeatpassword.getText())) {
            flag = true;
        } else {
            System.out.println(newpassword.getText() + " != " + repeatpassword);
            new notifier("Error!", "Passwords don't match !");
            flag = false;
        }

        if (currentpassword.getText().equals(pass)) {
            flag = true;
        } else {
            System.out.println(currentpassword.getText() + " != " + pass);
            new notifier("Error!", "Old Password not correct !");
            flag = false;
        }

        if (flag == true) {
            String SQL = "UPDATE `user` SET `user_id` = '" + newacademic_id.getText() + "' ,"
                    + " `user_name` = '" + newusername.getText() + "' ,"
                    + " `user_email` = '" + newemail.getText() + "' ,"
                    + " `user_password` = '" + newpassword.getText() + "' ,"
                    + " `user_department` = '" + newdep.getValue().toString() + "' ,"
                    + " `user_phone` = " + newphone.getText() + " WHERE `user_id` = '" + ref.userID + "'";

            sm.executeStatement(SQL);

            newacademic_id.setText("");
            newusername.setText("");
            newemail.setText("");
            newpassword.setText("");
            newphone.setText("");
        }
//        }

    }

    @FXML
    private void onLogOut(ActionEvent event) {
        ref.QuizID = null;
        ref.lecID = null;
        ref.username = null;
        ref.userID = null;
        ref.isLecRunning = false;
        ref.QuizRunning = false;
        ref.QuizID = null;
        ref.StudentList = null;

        try {
            Stage profilestage = (Stage) logout.getScene().getWindow();
            profilestage.close();

            if(ref.rootStage.isShowing()){
                ref.rootStage.close();
            }

            new Sign_logn().start(new Stage());

        } catch (Exception ex) {
            Logger.getLogger(FXMLServerProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void MouseEnteredBtnAction(MouseEvent event) {
        Glow aff = new Glow();
        aff.setLevel(0.5);
        JFXButton btn = (JFXButton) event.getSource();
        btn.setEffect(aff);
    }

    @FXML
    void MouseExitBtnAction(MouseEvent event) {
        Glow aff = new Glow();
        aff.setLevel(0);
        JFXButton btn = (JFXButton) event.getSource();
        btn.setEffect(aff);
    }

    void ClosePaneAction() {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }

}
