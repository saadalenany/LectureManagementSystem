/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Main.SlidePane;

import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import lecturemanagement.Client.Main.control.RecieveSlideNumberAndQuiz;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class SlidePaneFXMLController implements Initializable {

    @FXML
    private JFXButton nextButton;

    @FXML
    private ImageView LectureView;

    //-----------------------------------
    private ArrayList<File> SlidesList;
    private int ImageIndex;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        RecieveSlideNumberAndQuiz slideNumber = new RecieveSlideNumberAndQuiz(LectureView);
        slideNumber.SendLoginInfo();
    }

    @FXML
    private void previousButtonAction(ActionEvent event) throws IOException {
        if (ImageIndex == 0) {
            return;
        }
        ImageIndex--;
        ChangeImage(SlidesList.get(ImageIndex));

    }

    @FXML
    private void fullScreenButtonAction(ActionEvent event) {
        new fullScreenMode(ImageIndex, SlidesList);
    }

    @FXML
    private void nextButtonAction(ActionEvent event) throws IOException {
        if (ImageIndex == SlidesList.size() - 1) {
            return;
        }
        ImageIndex++;
        ChangeImage(SlidesList.get(ImageIndex));

    }

    public ArrayList<File> getSlidesList() {
        return SlidesList;
    }

    public void setSlidesList(ArrayList<File> SlidesList) {
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
        LectureView.setImage(image);
    }

    public void FollowContainerBySize(Node node) {
        Pane box = (Pane) node;
        resizeImage(LectureView, box);
    }

    public void resizeImage(ImageView image, Pane pane) {
        Scene scene = pane.getScene();

        scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
            changeWithScene(pane, image);
        });

        scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
            changeWithScene(pane, image);
        });
    }

    private void changeWithScene(Pane pane, ImageView image) {
        double size = Math.min(pane.getWidth(), pane.getHeight());
        System.out.println(size);
        size += 50;
        image.setFitHeight(size);
        image.setFitWidth(size);

    }

}
