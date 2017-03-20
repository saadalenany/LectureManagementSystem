/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import static javafx.scene.layout.BorderPane.setAlignment;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import lecturemanagement.Server.Main.Control.ReceiveClientSignup;
import lecturemanagement.Server.Main.Control.RefreshOnlineList;
import lecturemanagement.Server.Main.Control.SendLeactureToStudent;
import static lecturemanagement.Server.Main.Control.SendLeactureToStudent.LectureClientSocket;
import lecturemanagement.Server.Main.SlidePane.SlidePaneFXMLController;
import lecturemanagement.Server.Main.Tables.ShowTables;
import lecturemanagement.Utillity.LoadFXML;
import SerClass.SlidesMetaData;
import java.awt.Desktop;
import javafx.application.Platform;
import lecturemanagement.Server.Login.Control.ReceiveClientLogin;
import lecturemanagement.Server.Main.Control.ImageRequestReplay;
import lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;
import com.jfoenix.controls.JFXButton;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Amr
 */
public class MainFXMLController implements Initializable {

    @FXML
    private Rectangle SignUpCounterRect;

    @FXML
    private JFXButton StartOnlineButton;

    @FXML
    private StackPane numOfRequestPane;

    @FXML
    private VBox requestBox;

    @FXML
    private Text SignUpCounterText;

    @FXML
    private JFXButton openSlideBtn;

    @FXML
    private Text lecID;

    //--------------------------------------------------------------
    public final String QuizMakerPath = "/lecturemanagement/Server/QuizMaker/QuizMakerFXML.fxml";
    public final String SlidePanePath = "/lecturemanagement/Server/Main/SlidePane/SlidePaneFXML.fxml";
    public final String WaitingScreenPath = "/lecturemanagement/Utility/Waiting/WaitingFXML.fxml";
    public final int WIDTH = 500, HEIGHT = 120;
    private String CreatorPath = "ImageCreator.jar";
    public static File LectureFile;

    private ArrayList<File> SlidesList;
    private SlidePaneFXMLController SlideController;
    private Thread SlideThread;

    private void setRandomID() {
        String ID = "";
        for (int i = 0; i < 10; i++) {
            ID += (int) (Math.random() * 10) + "";
        }
        lecID.setText(ID);
        ref.lecID = ID;
    }

    @FXML
    private void onClosing(ActionEvent event) {
        Platform.exit();

    }

    //--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // get Stage 

        ref.rootStage.setWidth(WIDTH);
        ref.rootStage.setHeight(HEIGHT);
        openSlideBtn.setDisable(true);
        // set lecture id 
        setRandomID();
        ref.RequestSignUpBox = requestBox;
        ref.SignUpCounterPane = numOfRequestPane;
        ref.SignUpCounterText = SignUpCounterText;
        ref.SignUpCounterRect = SignUpCounterRect;
        numOfRequestPane.setVisible(false);

        new SendQuizSlideNumberEndLecture().startListen(); // start listen to send Lecture number
        new ReceiveClientSignup().startListen();  // listen for signUp student
        new SendLeactureToStudent().startListen(); // start listen lecture
        new ReceiveClientLogin().startListen(); // start Listen from login student   

    }

    @FXML
    private void StartOnlineButtonAction(ActionEvent event) throws IOException, Exception {
        // make leacture is running
        // open converter.jar
        // refresh online list 
        // assert not have dublicate
        // send Lecture to all student
        // open waiting Screen 
        // Request and replay from this program to creator
        // open slide pane
        // make button is disable 
        if (LectureFile == null) {
            new notifier("Error", " please select your lecture ");
            return;
        }
        if (!OpenCreator()) {
            new notifier("Error", " not found creator.jar ");
            return;
        }
        ref.isLecRunning = true;
        RefreshOnlineList rol = new RefreshOnlineList();
        rol.RefershList();
        rol.DeleteDuplicateStudent();
        SendLectureToAllClient();
        // new LoadFXML().LoadFXML(MainHBox, WaitingScreenPath);
        //-----------------------------------------------
        ImageRequestReplay irr = new ImageRequestReplay();
        irr.SavePathSlideFile(LectureFile);
        SlideThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                    LoadFXML loader = new LoadFXML();
                    loader.LoadFXMLUtilityStage(SlidePanePath);
                    SlideController = (SlidePaneFXMLController) loader.getController();
                    SlideController.setSlidesList(SlidesList);
                    SlideController.ChangeImage(SlidesList.get(0));
                    openSlideBtn.setDisable(false);
                });
                SlideThread.interrupt();
                SlideThread = null;
                break;
            }
        });
        SlideThread.start();
        StartOnlineButton.setDisable(true);
    }

    @FXML
    void OpenSlideAction(ActionEvent event) {
        LoadFXML loader = new LoadFXML();
        loader.LoadFXMLUtilityStage(SlidePanePath);
        SlideController = (SlidePaneFXMLController) loader.getController();
        SlideController.setSlidesList(SlidesList);
        SlideController.ChangeImage(SlidesList.get(0));
    }

    @FXML
    void uploadLectureAction(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("powerPoint file ", "pptx", "ppt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            LectureFile = chooser.getSelectedFile();
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getPath());
        } else {
            LectureFile = null;
        }
        if (LectureFile != null) {
            new notifier("info", " you select : " + LectureFile.getName());
        }

    }

    public void SendLectureToAllClient() {
        for (int i = 0; i < LectureClientSocket.size(); i++) {
            try {
                new SendLeactureToStudent().SendLecture(LectureClientSocket.get(i));
            } catch (IOException ex) {
                Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void handleQuizAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(QuizMakerPath));
            stage.setScene(new Scene(root));
            
        } catch (IOException ex) {
            ex.getMessage();
        }
        stage.setTitle("Quiz Creator");
        stage.showAndWait();
    }

    @FXML
    private void handleTablesAction(ActionEvent event) {
        ScrollPane scroller = new ScrollPane();
        //   MainHBox.getChildren().clear();

        //     MainHBox.getChildren().add(scroller);
        StackPane stackp = new StackPane();
        stackp.getChildren().add(new ShowTables());

        setAlignment(scroller, Pos.CENTER);

        stackp.minWidthProperty().bind(Bindings.createDoubleBinding(()
                -> scroller.getViewportBounds().getWidth(), scroller.viewportBoundsProperty()));

        scroller.setContent(stackp);

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

    @FXML
    void TakeAbsanceButtonAction(ActionEvent event) {
        if (ref.StudentList.isEmpty()) {
            new notifier("info", "no one in lecture ");
            return;
        }
        SaveAbsanceToFile();
        new notifier("info", "Quiz taken ");
    }

    private void SaveAbsanceToFile() {
        File dir = new File("studentAbsence");
        if (!dir.exists()) {
            dir.mkdir();
        }
        try (FileWriter fw = new FileWriter("studentAbsence/" + lecID.getText() + ".txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println("Lecture ID : " + lecID.getText());
            for (int i = 0; i < ref.StudentList.size(); i++) {
                out.println("username :  " + ref.StudentList.get(i).getUsername());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
