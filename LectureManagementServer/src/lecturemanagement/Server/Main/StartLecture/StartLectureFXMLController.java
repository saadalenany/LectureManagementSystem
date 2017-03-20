/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.StartLecture;

import SerClass.SlidesMetaData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
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
import javafx.application.Platform;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lecturemanagement.Server.Main.Control.ImageRequestReplay;
import lecturemanagement.Server.Main.Control.LogoutStudent;
import lecturemanagement.Server.Main.Control.RefreshOnlineList;
import lecturemanagement.Server.Main.Control.SendLeactureToStudent;
import static lecturemanagement.Server.Main.Control.SendLeactureToStudent.LectureClientSocket;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;
import lecturemanagement.Server.Main.DataBase.AbsenceRate;
import lecturemanagement.Server.Main.SlidePane.SlidePaneFXMLController;
import lecturemanagement.Server.Main_.MainFXMLController;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.undecoratedStage.undecoratedStageOptions;
import static lecturemanagement.Server.Main_.MainFXMLController.LectureFile;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class StartLectureFXMLController implements Initializable {

    @FXML
    private Text LectureTxt;

    @FXML
    private Text LectureVisibleTxt;

    @FXML
    private JFXListView<String> PreLectureList;

    @FXML
    private JFXTextField newLectureField;

    @FXML
    private HBox BoderBox;

    //---------------------------------------------
    private JFXButton StartBtn;
    private Thread SlideThread;
    StorageManager st;
    private ArrayList<File> SlidesList;
    ObservableList<String> lecturesnames;
    ObservableList<Integer> lectures_ids;
    private SlidePaneFXMLController SlideController;

    private final String ConvertingImgPath = "/resource/convert.png";
    private final String CreatorPath = "ImageCreator.jar";
    private final String StopImgPath = "/resource/stop.png";
    public final String SlidePanePath = "/lecturemanagement/Server/Main/SlidePane/SlidePaneFXML.fxml";

    public boolean selectFile() {
        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PowerPoint files (*.pptx)", new String[]{"*.pptx", "*.ppt"});
        filechooser.getExtensionFilters().add(extFilter);
        File openedFile = filechooser.showOpenDialog(new Stage());
        if (openedFile == null) {
            LectureFile = null;
            return false;
        }
        LectureFile = openedFile;
        return true;
    }

    @FXML
    void SelectLectureaction(ActionEvent event) {

        selectFile();
        if (LectureFile == null) {
            return;
        }
        LectureVisibleTxt.setVisible(true);
        LectureTxt.setVisible(true);

    }

    @FXML
    void StartWithNewLectureAction(ActionEvent event) {
        String str = newLectureField.getText();
        if (LectureFile == null) {
            new notifier("Error!", "Please select lecture");
            return;
        }
        if (str.trim().equals("")) {
            new notifier("Error!", "Please write down Lecture name!");
            return;
        }
        ref.lecName = str;
        newLectureField.setDisable(true);
        ref.isLecRunning = true;
        insertNewLecture();//create lecture but not opening up slides
        startAction();

    }

    @FXML
    void StartWithPreviousAction(ActionEvent event) {
        String str = PreLectureList.getSelectionModel().getSelectedItem().toString();
        if (str.equals("")) {
            new notifier("Error!", "Please choose a lecture!");
            return;
        }
        newLectureField.setDisable(true);
        CloseAction(event);
        System.out.println("lecture " + str + " has resumed!");
        System.out.println("lecture_id ==> " + lectures_ids.get(lecturesnames.indexOf(str)));
        new notifier("Note!", "lecture " + str + " has resumed!");

    }

    @FXML
    void CloseAction(ActionEvent event) {
        Stage stage = (Stage) PreLectureList.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LectureVisibleTxt.setVisible(false);
        LectureTxt.setVisible(false);
        lectures_ids = FXCollections.observableArrayList();
        lecturesnames = FXCollections.observableArrayList();

        st = new StorageManager();
        ResultSet rs = st.performQuery("SELECT lecture_id , lecture_name FROM lecture");

        System.out.println("Lecture_id\tLecture_name");
        try {
            while (rs.next()) {
                lectures_ids.add(rs.getInt(1));
                lecturesnames.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbsenceRate.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreLectureList.setItems(lecturesnames);
    }

    //--------------------------------------------------------------------------------------------
    public void startAction() {
        // make leacture is running
        // open converter.jar
        // refresh online list 
        // assert not have dublicate
        // send Lecture to all student
        // open waiting Screen 
        // Request and replay from this program to creator
        // open slide pane
        // make button is disable 

        if (!OpenCreator()) {
            new notifier("Error", " not found creator.jar ");
            return;
        }
        StartBtn.setGraphic(new ImageView(ConvertingImgPath));
        StartBtn.setDisable(true);

        ref.isLecRunning = true;
        RefreshOnlineList rol = new RefreshOnlineList();
        rol.RefershList();
        rol.DeleteDuplicateStudent();
        SendLectureToAllClient();
        //-----------------------------------------------
        ImageRequestReplay irr = new ImageRequestReplay();
        irr.SavePathSlideFile(LectureFile);
        SlideThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(lecturemanagement.Server.Main.view.MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!irr.OpenSlideFile()) {
                    continue;
                }
                SlidesMetaData slide = irr.getOpenedOpject();
                if (slide.getNumberOfSlides() == 0) {
                    continue;
                }
                System.out.println("Loaded...");

                SlidesList = slide.getImageList();
                //----------------------------------------------------- loaded        
                Platform.runLater(() -> {
                    StartBtn.setGraphic(new ImageView(StopImgPath));
                    StartBtn.setDisable(false);
                    LoadSlidePane();
                    SlideController.setSlidesList(SlidesList);
                    SlideController.ChangeImage(SlidesList.get(0));

                });
                SlideThread.interrupt();
                SlideThread = null;
                break;
            }
        });
        SlideThread.start();

    }

    public void SendLectureToAllClient() {
        for (int i = 0; i < LectureClientSocket.size(); i++) {
            try {
                new SendLeactureToStudent().SendLecture(LectureClientSocket.get(i));
            } catch (IOException ex) {
                Logger.getLogger(lecturemanagement.Server.Main.view.MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean OpenCreator() {
        try {
            Desktop.getDesktop().open(new File(CreatorPath));
        } catch (Exception ex) {
            new notifier("error", " " + ex.getMessage());
            return false;
        }
        return true;
    }

    private void LoadSlidePane() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = null;
        LoadFXML loader = new LoadFXML();
        root = loader.LoadFXML(SlidePanePath);
        SlideController = (SlidePaneFXMLController) loader.getController();
        stage.setScene(new Scene(root));
        HBox border = SlideController.getBorderBox();
        SlideController.setSlidesList(SlidesList);
        SlideController.ChangeImage(SlidesList.get(0));
        new undecoratedStageOptions(stage, border).fullTools();
        stage.show();
    }

    public void insertNewLecture() {
        System.out.println("Inserting a new lecture !");
        StorageManager st = new StorageManager();
        System.out.println("doctor_name ==> " + ref.username);
        ResultSet rs = st.performQuery("SELECT `user_id` FROM `user` WHERE `user_name` ='" + ref.username + "'");
        String docid = "0";
        try {
            while (rs.next()) {
                System.out.println("doctor_id ==> " + rs.getString(1));
                docid = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(lecturemanagement.Server.Main.view.MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] cols = {"lecture_name", "doctor_id"};
        ArrayList vals = new ArrayList();
        vals.add(ref.lecName);
        vals.add(docid);
        st.insertInto("lecture", cols, vals);

        StorageManager st2 = new StorageManager();
        ResultSet rs2 = st2.performQuery("SELECT `lecture_id` FROM `lecture` WHERE `doctor_id` = " + docid);
        try {
            while (rs2.next()) {
                System.out.println(rs2.getString(1));
                ref.lecID = rs2.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setStartBtn(JFXButton StartBtn) {
        this.StartBtn = StartBtn;
    }

    public HBox getBoderBox() {
        return this.BoderBox;
    }

}
