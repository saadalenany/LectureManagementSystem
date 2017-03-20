/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Utillity.Waiting;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author Amr
 */
public class ColorTransLoop implements Runnable {

    private Region Region;
    private ArrayList<Color> colorList;
    private int duration_millis;
    private ArrayList<FromToColorChanging> ColorChangingList;
    private Thread thisThread;

    public ColorTransLoop(ArrayList<Color> colorList, int duration_millis, Region Region) {
        this.duration_millis = duration_millis;
        this.Region = Region;
        this.colorList = colorList;
        ColorChangingList = new ArrayList<>();

    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < colorList.size(); i++) {
                Color nextColor;
                if (i == colorList.size() - 1) {
                    nextColor = colorList.get(0);
                } else {
                    nextColor = colorList.get(i + 1);
                }
                FromToColorChanging temp = new FromToColorChanging(Region, duration_millis, colorList.get(i), nextColor);
                this.ColorChangingList.add(temp);
            }
            ColorChangingList.get(0).PlayTrans();
            for (int i = 0; i < ColorChangingList.size(); i++) {
                FromToColorChanging t1 = ColorChangingList.get(i);
                FromToColorChanging t2;
                if (i == ColorChangingList.size() - 1) {
                    t2 = ColorChangingList.get(0);
                } else {
                    t2 = ColorChangingList.get(i + 1);
                }
                t1.setPlayAfter(t2);
            }
            ColorChangingList.clear();

            try {
                Thread.sleep(duration_millis * colorList.size());
            } catch (InterruptedException ex) {
                Logger.getLogger(WatingFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void play() {
        thisThread = new Thread(this);
        thisThread.start();
    }

    public void stop() {
        try {
            for (int i = 0; i < ColorChangingList.size(); i++) {
                ColorChangingList.get(i).stop();

                thisThread.join();

            }
            thisThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ColorTransLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
