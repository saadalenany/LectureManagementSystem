/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Main.SlidePane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author Amr
 */
public class fullScreenMode {

    public static ImageView LectureViewFullScreen;
    private int ImageIndex;
    private ArrayList<File> SlidesList;
    private Scene scene;
    private Stage stage;

    fullScreenMode(int ImageIndex, ArrayList<File> SlidesList) {
        this.ImageIndex = ImageIndex;
        this.SlidesList = SlidesList;
        LectureViewFullScreen = new ImageView();
        LectureViewFullScreen.setEffect(new DropShadow());
        drawContent();
        ChangeImage(SlidesList.get(ImageIndex));
        Actions();

    }

    public void ChangeImage(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(fullScreenMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        LectureViewFullScreen.setImage(image);
    }

    private void drawContent() {
        stage = new Stage();
        scene = new Scene(new StackPane(LectureViewFullScreen));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.show();
    }

    private void Actions() {

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                if (ImageIndex == 0) {
                    return;
                }
                ImageIndex--;
                ChangeImage(SlidesList.get(ImageIndex));
                setWidthAndHeight(SlidesList.get(ImageIndex));

            } else if (e.getCode() == KeyCode.RIGHT) {
                if (ImageIndex == SlidesList.size() - 1) {
                    return;
                }
                ImageIndex++;
                ChangeImage(SlidesList.get(ImageIndex));
                setWidthAndHeight(SlidesList.get(ImageIndex));

            } else if (e.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
        scene.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            LectureViewFullScreen.setFitHeight(LectureViewFullScreen.getFitHeight() + deltaY);
            LectureViewFullScreen.setFitWidth(LectureViewFullScreen.getFitWidth() + deltaY);

            event.consume();
        });
    }

    private void setWidthAndHeight(File file) {
        try {
            BufferedImage bimg = ImageIO.read(file);
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            LectureViewFullScreen.setFitHeight(height);
            LectureViewFullScreen.setFitWidth(width);

        } catch (IOException ex) {
            Logger.getLogger(fullScreenMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
