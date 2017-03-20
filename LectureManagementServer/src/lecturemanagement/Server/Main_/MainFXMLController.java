/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main_;

import SerClass.SlidesMetaData;
import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import lecturemanagement.Server.Login.Control.ReceiveClientLogin;
import lecturemanagement.Server.Main.Control.ImageRequestReplay;
import lecturemanagement.Server.Main.Control.LogoutStudent;
import lecturemanagement.Server.Main.Control.RefreshOnlineList;
import lecturemanagement.Server.Main.Control.SendLeactureToStudent;
import static lecturemanagement.Server.Main.Control.SendLeactureToStudent.LectureClientSocket;
import lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;
import lecturemanagement.Server.Main.DataBase.databasecontroller;
import lecturemanagement.Server.Main.ServerProfile.FXMLServerProfileController;
import lecturemanagement.Server.Main.SlidePane.SlidePaneFXMLController;
import lecturemanagement.Server.Main.StartLecture.StartLectureFXMLController;
import lecturemanagement.Server.Main.TakeAbsence.TakeAbsenceFXMLController;
import lecturemanagement.Server.QuizMaker.Messages;
import lecturemanagement.Server.QuizMaker.QuizMakerController;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.Utillity.undecoratedStage.undecoratedStageOptions;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class MainFXMLController implements Initializable {

    //saad task
    @FXML
    public JFXButton StartBtn;
    @FXML
    private Rectangle MainRect;
    //-----------------------------------
    public static int RECT_WIDTH = 500;
    public static File LectureFile;
    public final String QuizMakerPath = "/lecturemanagement/Server/QuizMaker/QuizMakerFXML.fxml";
    public final String WaitingScreenPath = "/lecturemanagement/Utility/Waiting/WaitingFXML.fxml";
    public final String AbsenceScreenPath = "/lecturemanagement/Server/Main/TakeAbsence/TakeAbsenceFXML.fxml";
    public final String ProfileServerPath = "/lecturemanagement/Server/Main/ServerProfile/FXMLServerProfile.fxml";
    public final String StartLecturePath = "/lecturemanagement/Server/Main/StartLecture/StartLectureFXML.fxml";
    private final String EndLectureMarker = "END_LECTURE";
    private final String StartImgPath = "/resource/start.png";
    private final String ClosePath = "/resource/close.png";
    private boolean isStart;

    private ArrayList<File> SlidesList;
    private SlidePaneFXMLController SlideController;
    private Thread SlideThread;

    @FXML
    public static JFXButton profileButton;

    @FXML
    public static JFXButton close;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainRect.setWidth(RECT_WIDTH);

        new SendQuizSlideNumberEndLecture().startListen(); // start listen to send Lecture number
        new SendLeactureToStudent().startListen(); // start listen lecture
        new ReceiveClientLogin().startListen(); // start Listen from login student 
    }

    @FXML
    void MouseEnteredRectAction(MouseEvent event) {

    }

    @FXML
    void MouseExitRectAction(MouseEvent event) {

    }

    //saad task
    @FXML
    void CloseAction(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();

        Platform.exit();
        System.exit(0);
    }

    @FXML
    void ShowDatabaseDataAction(ActionEvent event) {

        Stage stage = new Stage();
        BorderPane root = DatabaseWindow(stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        TabPane tab = new databasecontroller();
        root.setCenter(tab);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        tab.setStyle("-fx-background-color : #2A80B9 ;");
        String css = this.getClass().getResource("/resource/styleSheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        new undecoratedStageOptions(stage, (Pane) root.getTop()).fullTools();
        stage.show();

    }

    private BorderPane DatabaseWindow(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color : #2A80B9 ;"
                + " -fx-border-color : #000;");
        HBox BorderBox = new HBox();
        //---------
        JFXButton closeBtn = new JFXButton();
        closeBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        ImageView img = new ImageView(ClosePath);
        img.setFitHeight(22);
        img.setFitWidth(22);

        closeBtn.setGraphic(img);
        closeBtn.setOnAction(e -> {
            stage.close();
        });
        BorderBox.getChildren().add(closeBtn);
        HBox.setMargin(closeBtn, new Insets(-2, -3, 0, 0));
        //--------
        root.setTop(BorderBox);
        BorderBox.setAlignment(Pos.TOP_RIGHT);
        BorderBox.setPrefHeight(35);

        return root;
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

    @FXML
    public void StartAction(ActionEvent event) {

        if (isStart) {
            StartBtn.setGraphic(new ImageView(StartImgPath));
            sendEndLecture();
            ref.isLecRunning = false;
            return;
        }
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root;
        LoadFXML loader = new LoadFXML();
        root = loader.LoadFXML(StartLecturePath);
        StartLectureFXMLController control = (StartLectureFXMLController) loader.getController();
        stage.setScene(new Scene(root));
        control.setStartBtn(StartBtn);
        new undecoratedStageOptions(stage, control.getBoderBox()).fullTools();
        isStart = true;
        stage.setTitle("Start Lecture");
        stage.show();

    }

    private void sendEndLecture() {
        ref.isLecRunning = false;
        LectureClientSocket.clear();
        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            try {
                DataOutputStream out = new DataOutputStream(SendQuizSlideNumberEndLectureSocket.get(i).getOutputStream());
                out.writeUTF(EndLectureMarker);
            } catch (SocketException e) {
                // from start only because it first point 
                new LogoutStudent().removeLogoutStudent(SendQuizSlideNumberEndLectureSocket.get(i));
                //-----------------------------
            } catch (IOException ex) {
            }
        }

    }

    //saad task
    @FXML
    private void TakeAbsenceAction(ActionEvent event) {
        if (!ref.isLecRunning) {
            new notifier("Error!", "You must start a Lecture!");
            return;
        }
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        //  stage.initStyle(StageStyle.UNDECORATED);
        Parent root = null;
        LoadFXML loader = new LoadFXML();
        root = loader.LoadFXML(AbsenceScreenPath);
        TakeAbsenceFXMLController control = (TakeAbsenceFXMLController) loader.getController();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void makeQuizAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = null;
        LoadFXML loader = new LoadFXML();
        root = loader.LoadFXML(QuizMakerPath);
        QuizMakerController control = (QuizMakerController) loader.getController();
        stage.setScene(new Scene(root));
        HBox border = control.getBorderBox();
        new undecoratedStageOptions(stage, border).DragStage();
        stage.setTitle("Quiz Creator");
        stage.show();
    }

    //saad task
    @FXML
    private void viewUserProfile(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root;
        LoadFXML loader = new LoadFXML();

        root = loader.LoadFXML(ProfileServerPath);
        FXMLServerProfileController control = (FXMLServerProfileController) loader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle(ref.username + " Profile");
        stage.show();
    }

}
