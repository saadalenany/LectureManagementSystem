/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.Control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import lecturemanagement.Server.QuizMaker.Messages;
import static lecturemanagement.DataTransferProtocol.QuizSlideNumberPort;

/**
 *
 * @author Amr
 */
public class SendQuizSlideNumberEndLecture implements Runnable {

    private Socket server;
    public ServerSocket serverSocket;
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private OutputStream os = null;
    //------------------------------------
    public static ArrayList<Socket> SendQuizSlideNumberEndLectureSocket = new ArrayList<>(); // Using also in slide Pane
    public void startListen() {
        try {
            serverSocket = new ServerSocket(QuizSlideNumberPort);
        } catch (IOException ex) {
            Logger.getLogger(ReceiveClientSignup.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread th = new Thread(this);
        th.start();
        System.out.println("Start Slide number ...");
    }

    @Override
    public void run() {
        while (true) {
            try {
                server = serverSocket.accept();
                SendQuizSlideNumberEndLectureSocket.add(server);
            } catch (IOException ex) {
                Logger.getLogger(SendLeactureToStudent.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void getUsersAbsence(ArrayList<String> usernames, String lecture) {
        PrintWriter pw = null;
        try {
            String str = new Messages(new Stage()).getFileName();
            File file = new File("/lectureAbsence/" + str + ".txt");
            pw = new PrintWriter(file);
            pw.println(lecture);
            for (String username : usernames) {
                pw.println(username);
            }
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } finally {
            pw.close();
        }
    }


}
