/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.SlidePane;

import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import lecturemanagement.Server.Main.Control.LogoutStudent;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class SlidePaneFXMLController implements Initializable {

    @FXML
    private JFXButton nextButton;

    @FXML
    private BorderPane borderRoot;

    @FXML
    private JFXButton prevButton;

    @FXML
    private TextField FastInputTxt;
    @FXML
    private Text locationTxt;
    @FXML
    private HBox BorderBox;

    //-----------------------------------------
    private ArrayList<File> SlidesList;
    private boolean isFullScreen = true;
    private int ImageIndex;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BorderBox.setVisible(true);
    }

    @FXML
    void CloseAction(ActionEvent event) {
        Stage stage = (Stage) borderRoot.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void previousButtonAction(ActionEvent event) throws IOException {
        previousAction();
    }

    @FXML
    private void fullScreenButtonAction(ActionEvent event) {
        Stage stage = (Stage) borderRoot.getScene().getWindow();
        if (isFullScreen) {
            KeyBoardMove();
            BorderBox.setVisible(false);
            stage.setMaximized(true);
            isFullScreen = false;
            return;
        }

        stage.setWidth(800);
        stage.setHeight(600);
        BorderBox.setVisible(true);
        stage.setMaximized(false);
        isFullScreen = true;

    }

    @FXML
    void fastLocAction(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            String loc = FastInputTxt.getText();
            if (!loc.matches("\\d+") || Integer.parseInt(loc) >= SlidesList.size() || Integer.parseInt(loc) < 0) {
                FastInputTxt.setText("");
                return;
            }
            locationTxt.setText(Integer.parseInt(loc) + " /" + (SlidesList.size() - 1));
            ChangeImage(SlidesList.get(Integer.parseInt(loc)));
            SendLectureNumber(Integer.parseInt(loc));
            
        } else if (event.getCode() == KeyCode.LEFT) {
            Stage stage = (Stage) borderRoot.getScene().getWindow();
            if (stage == null) {
                return;
            }
            previousAction();

        } else if (event.getCode() == KeyCode.RIGHT) {
            Stage stage = (Stage) borderRoot.getScene().getWindow();
            if (stage == null) {
                return;
            }
            nextAction();
        }
    }

    @FXML
    private void nextButtonAction(ActionEvent event) throws IOException {
        nextAction();
    }

    public ArrayList<File> getSlidesList() {
        return SlidesList;
    }

    public void setSlidesList(ArrayList<File> SlidesList) {
        locationTxt.setText(ImageIndex + " /" + (SlidesList.size() - 1));
        this.SlidesList = SlidesList;
    }

    public void ChangeImage(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(SlidePaneFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        //-------------------------------
        BackgroundImage bgImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        borderRoot.setBackground(new Background(bgImg));
    }

    private void SendLectureNumber(int curLec) {
        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            try {
                System.out.println("sent to " + i + " Client and size " + SendQuizSlideNumberEndLectureSocket.size());
                DataOutputStream out = new DataOutputStream(SendQuizSlideNumberEndLectureSocket.get(i).getOutputStream());
                out.writeUTF(curLec + "");
            } catch (SocketException ex) {
                // here we will remove logout client
                // here beacuse it is start point (high perior)
                new LogoutStudent().removeLogoutStudent(SendQuizSlideNumberEndLectureSocket.get(i));
            } catch (IOException ex) {
                Logger.getLogger(SlidePaneFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void KeyBoardMove() {
        Stage stage = (Stage) borderRoot.getScene().getWindow();
        borderRoot.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                previousAction();
            } else if (e.getCode() == KeyCode.RIGHT) {
                nextAction();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                stage.setWidth(800);
                stage.setHeight(600);
                BorderBox.setVisible(true);
                stage.setMaximized(false);
                isFullScreen = true;
            }
        });
    }

    private void previousAction() {
        if (ImageIndex == 0) {
            return;
        }
        ImageIndex--;
        locationTxt.setText(ImageIndex + " /" + (SlidesList.size() - 1));
        ChangeImage(SlidesList.get(ImageIndex));
        SendLectureNumber(ImageIndex);
    }

    private void nextAction() {
        if (ImageIndex == SlidesList.size() - 1) {
            return;
        }
        ImageIndex++;
        locationTxt.setText(ImageIndex + " /" + (SlidesList.size() - 1));
        ChangeImage(SlidesList.get(ImageIndex));
        SendLectureNumber(ImageIndex);
    }

    public HBox getBorderBox() {
        return BorderBox;
    }

}
