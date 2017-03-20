/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Utility;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 *
 * @author Amr
 */
public class ScreenTools {

    private int ScreenWidth, ScreenHeight;
    private int TaskBarHeight;

    public ScreenTools() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ScreenWidth = (int) screenSize.getWidth();
        ScreenHeight = (int) screenSize.getHeight();
        //------------------
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        TaskBarHeight = scrnSize.height - winSize.height;
    }

    public int getScreenWidth() {
        return ScreenWidth;
    }

    public void setScreenWidth(int ScreenWidth) {
        this.ScreenWidth = ScreenWidth;
    }

    public int getScreenHeight() {
        return ScreenHeight;
    }

    public void setScreenHeight(int ScreenHeight) {
        this.ScreenHeight = ScreenHeight;
    }

    public int getTaskBarHeight() {
        return TaskBarHeight;
    }

    public void setTaskBarHeight(int TaskBarHeight) {
        this.TaskBarHeight = TaskBarHeight;
    }

}
