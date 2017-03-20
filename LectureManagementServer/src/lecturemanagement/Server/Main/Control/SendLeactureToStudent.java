/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.Control;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lecturemanagement.DataTransferProtocol.StartLecturePort;
import static lecturemanagement.Server.Main_.MainFXMLController.LectureFile;
import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class SendLeactureToStudent implements Runnable {

    private Socket server;
    private ServerSocket serverSocket;
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private OutputStream os = null;
    public final int MaxMB = 40;

    //------------------------------------
    public static ArrayList<Socket> LectureClientSocket = new ArrayList<>(); // Using in Main 

    public void startListen() {
        try {
            serverSocket = new ServerSocket(StartLecturePort);
        } catch (IOException ex) {
            Logger.getLogger(ReceiveClientSignup.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread th = new Thread(this);
        th.start();
        System.out.println("Start Lecture student...");
    }

    @Override
    public void run() {
        while (true) {
            try {
                server = serverSocket.accept();
                if (!ref.isLecRunning) {
                    LectureClientSocket.add(server);
                    continue;
                }
                SendLecture(server);
                System.out.println("lecture is sent ....");
            } catch (IOException ex) {
                Logger.getLogger(SendLeactureToStudent.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void SendLecture(Socket server) throws IOException {
        SendLectureName(server);
        SendLectureWithoutName(server);

    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        int bytes = 1024 * 1024 * MaxMB;
        byte[] buf = new byte[bytes];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }

    private void SendLectureWithoutName(Socket server) throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(LectureFile.getPath());
        OutputStream out = server.getOutputStream();
        copy(in, out);
        in.close();
        out.close();

    }

    private void SendLectureName(Socket server) throws IOException {
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        out.writeUTF(LectureFile.getName());

    }
}
