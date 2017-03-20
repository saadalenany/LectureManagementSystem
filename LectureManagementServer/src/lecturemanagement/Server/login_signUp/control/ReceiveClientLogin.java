/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.login_signUp.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lecturemanagement.DataTransferProtocol.SignInOutPort;
import lecturemanagement.Server.Main.Control.RefreshOnlineList;
import lecturemanagement.Model.StudentSentData;
import lecturemanagement.Server.Main.Control.LogoutStudent;
import lecturemanagement.Server.Main.view.MainFXMLController;
import lecturemanagement.StorageManager;

/**
 *
 * @author Amr
 */
public class ReceiveClientLogin implements Runnable {

    /*
    Sending Pattern 
    pattern : "username-password"
    and server will answer with 1 if correct data  , 0 if incorrect data , 
    if 2 already data exsit  
     */
    private ServerSocket serverSocket;
    public static ArrayList<Socket> OnlineSocketList = new ArrayList<>();


    public void startListen() {
        try {
            serverSocket = new ServerSocket(SignInOutPort);
        } catch (IOException ex) {
            Logger.getLogger(ReceiveClientLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread th = new Thread(this);
        th.start();
        System.out.println("Start Accept login student...");
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket server = serverSocket.accept();
                if (!ifIPExist(server)) {
                    OnlineSocketList.add(server);
                }
                String IP = server.getInetAddress().toString();
                DataInputStream in = new DataInputStream(server.getInputStream());
                String loginData = in.readUTF();  // recieve data
                String[] data = DecapsulationReceived(loginData);  // Decapsulate data 
                //-------------------------------------------------------   
                if (data.length == 1) {  //if student sign out 
                    SignOut(server, data[0], IP);
                    continue;  // go to listen again 
                }
                boolean status = StudentAuthorized(data[0], data[1]);             // check if data is crrect from database   
                DataOutputStream out = new DataOutputStream(server.getOutputStream());     // send answer to client 
                if (status) {
                    RefreshOnlineList refersh = new RefreshOnlineList();
                    int state = refersh.add(new StudentSentData(data[0], IP));
                    out.writeUTF(state + "");// 1 or 2      1 if added , 2 if already added
                    refersh.RefershList();

                } else {
                    out.writeUTF("0");
                }
                server.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SignOut(Socket server, String username, String IP) throws IOException {
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        RefreshOnlineList refersh = new RefreshOnlineList();
        boolean removed = refersh.remove(new StudentSentData(username, IP));
        new LogoutStudent().removeLogoutStudent(server); // remove him from the rest of service
        if (removed) {
            out.writeUTF("1");
        } else {
            out.writeUTF("0");
        }
    }

    private String[] DecapsulationReceived(String rec) {
        String[] split = rec.split("-"); //  -----------> split[0] for username  , split[1] for password 
        //  System.out.println(split[0] + " " + split[1]);        
        return split;
    }

    public boolean StudentAuthorized(String username, String password) { // from database
        StorageManager sm = new StorageManager();
        return sm.checkStudentLogIn(username, password);
    }

    private boolean ifIPExist(Socket socket) {
        for (int i = 0; i < OnlineSocketList.size(); i++) {
            if (OnlineSocketList.get(i).getInetAddress().toString().equals(socket.getInetAddress().toString())) {
                return true;
            }
        }
        return false;
    }

}
