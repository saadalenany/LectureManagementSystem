/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Utillity.Waiting;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Amr
 */
public class ZoomInZoomOutText {

    private double value;
    private double scele = 0.05;
    private Timeline Time1, Time2;
    private String familyFont = "Arial";
    private FontWeight Weight = FontWeight.NORMAL;
    private KeyFrame frame1, frame2;

    public ZoomInZoomOutText(Text text, double fromSize, double toSize) {

        value = fromSize;
        Time1 = new Timeline();
        Time1.setCycleCount(Animation.INDEFINITE);
        Time2 = new Timeline();
        Time2.setCycleCount(Animation.INDEFINITE);

        frame1 = new KeyFrame(Duration.millis(1), (ActionEvent event) -> {
            setProperty(text);
            if (value >= toSize) {
                Time1.stop();
                Time2.play();
            }
            value += scele;
        });
        frame2 = new KeyFrame(Duration.millis(1), (ActionEvent event) -> {
            setProperty(text);
            if (value <= fromSize) {
                Time2.stop();
                Time1.play();
            }
            value -= scele;

        });
        Time1.getKeyFrames().add(frame1);
        Time2.getKeyFrames().add(frame2);

    }

    public ZoomInZoomOutText(Text text, Region region, double fromSize, double toSize) {

        value = fromSize;
        Time1 = new Timeline();
        Time1.setCycleCount(Animation.INDEFINITE);
        Time2 = new Timeline();
        Time2.setCycleCount(Animation.INDEFINITE);

        frame1 = new KeyFrame(Duration.millis(1), (ActionEvent event) -> {
            setProperty(text, region);
            if (value >= toSize) {
                Time1.stop();
                Time2.play();
            }
            value += scele;
        });
        frame2 = new KeyFrame(Duration.millis(1), (ActionEvent event1) -> {
            setProperty(text, region);
            if (value <= fromSize) {
                Time2.stop();
                Time1.play();
            }
            value -= scele;

        });
        Time1.getKeyFrames().add(frame1);
        Time2.getKeyFrames().add(frame2);

    }

    private void setProperty(Text text) {
        text.setFont(Font.font(familyFont, Weight, value));
        double WidthLength = text.getText().length() * (value / 2.0);
        text.setWrappingWidth(WidthLength);
    }

    private void setProperty(Text text, Region region) {
        text.setFont(Font.font(familyFont, Weight, value));
        double WidthLength = text.getText().length() * (value / 2.0);
        text.setWrappingWidth(WidthLength);
        Color color = (Color) region.getBackground().getFills().get(0).getFill();
        Color newColor = ColorReverse(color);
        text.setFill(newColor);
    }

    private Color ColorReverse(Color ColorRegion) {

        int red = (int) (255 - ColorRegion.getRed() * 255);
        int green = (int) (255 - ColorRegion.getGreen() * 255);
        int blue = (int) (255 - ColorRegion.getBlue() * 255);
        return Color.rgb(red, green, blue);
    }

    public double getScele() {
        return scele;
    }

    public void setScele(double scele) {
        this.scele = scele;
    }

    public String getFamilyFont() {
        return familyFont;
    }

    public void setFamilyFont(String familyFont) {
        this.familyFont = familyFont;
    }

    public void play() {
        Time1.play();
    }

    public void stop() {
        try {
            Time1.stop();
            Time2.stop();
            Time1 = null;
            Time2 = null;
            frame1 = null;
            frame2 = null;
        } catch (Exception e) {

        }
    }

    public FontWeight getWeight() {
        return Weight;
    }

    public void setWeight(FontWeight Weight) {
        this.Weight = Weight;
    }

}
