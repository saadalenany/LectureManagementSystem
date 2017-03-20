/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Waiting.control;

import SerClass.SlidesMetaData;
import java.awt.Desktop;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import lecturemanagement.Client.Main.SlidePane.SlidePaneFXMLController;
import lecturemanagement.Client.Main.view.MainFXMLController;
import static lecturemanagement.DataTransferProtocol.ServerAddress;
import static lecturemanagement.DataTransferProtocol.StartLecturePort;
import lecturemanagement.Utility.LoadFXML;
import lecturemanagement.Utility.notifier;

import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class ReceiveLectureListener implements Runnable {

    private String CreatorPath = "ImageCreator.jar";
    private Socket socket;
    static ArrayList<File> SlidesList;
    public static String fileName;
    public final static int FILE_SIZE = 999_999_99; // file size temporary hard coded
    private final String MainPath = "/lecturemanagement/Client/Main/view/MainFXML.fxml";
    private final String SlidePanePath = "/lecturemanagement/Client/Main/SlidePane/SlidePaneFXML.fxml";

    public void StartRecieveLecture() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {

        InputStream inFromServer = null;
        try {
            socket = new Socket(ServerAddress, StartLecturePort);
            // Slidename
            inFromServer = socket.getInputStream();
            DataInputStream inStream = new DataInputStream(inFromServer);
            fileName = inStream.readUTF();
            // file receiving 
            inFromServer = socket.getInputStream();
            OutputStream out = new FileOutputStream(fileName);
            copy(inFromServer, out);
            // Convert lecture 
            ConvertLectureToImage(new File(fileName));
            out.close();
            inFromServer.close();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveLectureListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[FILE_SIZE];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    private void ConvertLectureToImage(File lecture) {
        if (!OpenCreator()) {
            Platform.runLater(() -> {
                new notifier(" Error", " not found Creator.jar please set it ");
                System.exit(0);
            });
        }
        ImageRequestReplay irr = new ImageRequestReplay();
        irr.SavePathSlideFile(lecture);
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReceiveLectureListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!irr.OpenSlideFile()) {
                continue;
            }
            SlidesMetaData slide = irr.getOpenedOpject();
            if (slide.getNumberOfSlides() == 0) {
                continue;
            }
            SlidesList = slide.getImageList();
            //----------------------------------------------------- loaded   
            MoveToMainScreen(); // move to main 
            break;
        }

    }

    private boolean OpenCreator() {
        try {
            Desktop.getDesktop().open(new File(CreatorPath));
        } catch (Exception ex) {
            Platform.runLater(() -> {
                new notifier(" Error", " " + ex.getMessage());
            });
            return false;
        }
        return true;
    }

    private void MoveToMainScreen() {
        Platform.runLater(() -> {
            MainFXMLController MainControl = (MainFXMLController) new LoadFXML().LoadFXMLAndGetController(ref.mainScene, MainPath);
            Pane pane = MainControl.getLecturePane();
            SlidePaneFXMLController slideControl = (SlidePaneFXMLController) new LoadFXML().LoadFXMLAndGetRootModified(pane, SlidePanePath);
            slideControl.setSlidesList(SlidesList);
            slideControl.FollowContainerBySize(pane);
            slideControl.ChangeImage(SlidesList.get(0));
            //-------------------------------------------
            new notifier("info", " The Lecture has been started");
        });
    }
//
}
