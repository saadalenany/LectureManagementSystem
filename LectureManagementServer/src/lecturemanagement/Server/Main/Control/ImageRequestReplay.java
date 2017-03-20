/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.Control;

import SerClass.SlidesMetaData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import lecturemanagement.Server.Main.view.MainFXMLController;


/**
 *
 * @author Amr
 */
public class ImageRequestReplay {

    private final String slideMetaPath = "SlidesMeta.ser";
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SlidesMetaData slide;

    public void SavePathSlideFile(File LectureFile) {
        try {
            out = new ObjectOutputStream(new FileOutputStream(slideMetaPath));
            SlidesMetaData newObject = new SlidesMetaData();
            newObject.setSlidesPath(LectureFile);
            out.writeObject(newObject);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            out = null;
            System.gc();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            out = null;
            System.gc();
        }
    }

    public boolean OpenSlideFile() {
        try {
            in = new ObjectInputStream(new FileInputStream(slideMetaPath));
            slide = (SlidesMetaData) in.readObject();
            in.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            in = null;
            System.gc();
            return false;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            in = null;
            System.gc();
            return false;
        }
        return true;
    }

    public SlidesMetaData getOpenedOpject() {
        return slide;
    }

    private void Sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
