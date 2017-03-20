/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Signup.control;

import SerClass.StudentTransfer;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.Scene;
import static lecturemanagement.DataTransferProtocol.ServerAddress;
import static lecturemanagement.DataTransferProtocol.SignUpPort;
import lecturemanagement.Utility.LoadFXML;
import lecturemanagement.Utility.notifier;

/**
 *
 * @author Amr
 */
public class SignUpSocket implements Runnable {

    private Socket socket;
    private Scene nextScene;
    private StudentTransfer student;
    private final String SignInPath = "/lecturemanagement/Client/Login/view/LoginFXML.fxml";

    public SignUpSocket(StudentTransfer student, Scene nextScene) {
        this.nextScene = nextScene;
        this.student = student;
    }

    public void SendSignUpInfo() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {

        InputStream inFromServer = null;
        try {
            socket = new Socket(ServerAddress, SignUpPort);
            OutputStream outToServer = socket.getOutputStream();
            //--------------------------------------------------------------------       
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            out.writeObject(student);
            System.out.println("sent...");
            //---------------------------------
            inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            moveToNextStage(in.readUTF());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveToNextStage(String state) {
        System.out.println(state);
        new LoadFXML().LoadFXML(nextScene, SignInPath);
        Platform.runLater(() -> {
            if ("1".equals(state)) {
                new notifier("Wellcome", "You are Logged in ");
                return;
            } else if ("2".equals(state)) {
                new notifier("Error", " maybe username or academic id already used ");
                return;
            }
            new notifier("Error", " Doctor didn't accept you ");
        });
    }
}
