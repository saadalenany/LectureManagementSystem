/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Main.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;
import static lecturemanagement.Client.Login.Control.LoginSocket.ChatController;
import static lecturemanagement.DataTransferProtocol.AskQuestionPort;
import static lecturemanagement.DataTransferProtocol.ServerAddress;
import lecturemanagement.Utility.LoadFXML;
import static lecturemanagement.ref.username;

/**
 *
 * @author Amr
 */
public class MessageSocket implements Runnable {

    private String SendQuestionPath = "/lecturemanagement/Client/DoctorAnsQuestion/DoctorAnsQuestionFXML.fxml";
    private DataOutputStream output;
    private DataInputStream input;
    private String Message;
    private final String startChatMarker = "|g12@%4d6%7#*%$3@";
    private boolean MarkerIsSent;

    public void StartListen(String Message) {
        this.Message = Message;
        Thread th = new Thread(this);
        th.start();
    }

    public void StartListen() {
        MarkerIsSent = true;
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        try {
            Socket client = new Socket(ServerAddress, AskQuestionPort);
            //send Start Chat Marker  
            output = new DataOutputStream(client.getOutputStream());
            if (MarkerIsSent) {
                output.writeUTF(username + "-" + startChatMarker);
            } else {
                output.writeUTF(username + "-" + Message);
            }
            while (true) {
                input = new DataInputStream(client.getInputStream());
                String msg = input.readUTF();
                Platform.runLater(() -> {
                ChatController.MakeMessage(msg, 'd');
                });
            }
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
           // Logger.getLogger(MessageSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
