/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.OnlineStudent.control;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lecturemanagement.Server.Main.Control.ReceiveClientSignup;
import lecturemanagement.Server.Main.Control.SendLeactureToStudent;
import lecturemanagement.ref;
import static lecturemanagement.DataTransferProtocol.ChatPort;

/**
 *
 * @author Amr
 */
public class ChatListenerSocket implements Runnable {

    private Socket server;
    private ServerSocket serverSocket;

    private final String startChatMarker = "|g12@%4d6%7#*%$3@"; // for know when socket 
    private boolean isTheStartMarker;

    //------------------------------------
    public void startListen() {
        try {
            serverSocket = new ServerSocket(ChatPort);
        } catch (IOException ex) {
            Logger.getLogger(ReceiveClientSignup.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread th = new Thread(this);
        th.start();
        System.out.println("Start Chat Service ...");
    }

    @Override
    public void run() {
        String username = null;
        String message = null;
        try {
            while (true) {
                server = serverSocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());
                String ReadMsg = in.readUTF();
                String split[] = DecapsulationReceived(ReadMsg);
                username = split[0];
                message = split[1];
                if (isStartMarker(message)) {
                    isTheStartMarker = true;
                }
                SearchForUserNameInButtonList(username, message);
                isTheStartMarker = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(SendLeactureToStudent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isStartMarker(String marker) {
        return marker.equals(startChatMarker);
    }

    private String[] DecapsulationReceived(String rec) {
        String[] split = rec.split("-"); //  -----------> split[0] for username  , split[1] for question 
        return split;
    }

    private void SearchForUserNameInButtonList(String username, String Messeage) {
        for (int i = 0; i < ref.OnlineListButton.size(); i++) {
            String btnUsername = ref.OnlineListButton.get(i).getStudent().getUsername();
            if (btnUsername.equals(username)) {
                System.out.println("Entered -> " + username);
                if (!isTheStartMarker) {        // if not start , Don't send this message 
                    ref.OnlineListButton.get(i).AddMessage(Messeage);
                }
                ref.OnlineListButton.get(i).setStudentSocket(server);
            }
        }
    }

}
