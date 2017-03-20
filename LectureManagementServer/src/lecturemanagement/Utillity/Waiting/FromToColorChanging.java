/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Utillity.Waiting;

import com.jfoenix.transitions.JFXFillTransition;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class FromToColorChanging {

    private Region Region;
    private JFXFillTransition trans;
    private Color from, to;
    private FromToColorChanging PlayAfter;
    private Thread th;
    private boolean isStart;
    private int duration_millis;

    public FromToColorChanging(Region Region, int duration_millis, Color from, Color to) {
        this.duration_millis = duration_millis;
        this.Region = Region;
        this.from = from;
        this.to = to;
        FromToColorTrans(from, to);
    }

    public void setPlayAfter(FromToColorChanging PlayAfter) {
        this.PlayAfter = PlayAfter;
    }

    private void FromToColorTrans(Color from, Color to) {
        trans = new JFXFillTransition();
        trans.setDuration(Duration.millis(duration_millis));
        th = new Thread(() -> {
            try {
                Thread.sleep(duration_millis);
                trans.stop();
                PlayAfter.PlayTrans();
                th.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(WatingFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        trans.setRegion(Region);
        trans.setFromValue(from);
        trans.setToValue(to);
    }

    public void PlayTrans() {
        if (!isStart) {
            th.start();
        }
        trans.play();
        isStart = true;
    }

    public void stop() {
        try {
            th.join();
            trans.stop();
        } catch (Exception ex) {
        }

    }

}
