/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.Control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lecturemanagement.DataTransferProtocol.SignUpPort;
import SerClass.StudentTransfer;
import java.io.DataOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lecturemanagement.Server.Main.ApproveIgnoreAll.ApproveIgnoreAllFXMLController;
import lecturemanagement.Server.Main.NewRequest.RequestFXMLController;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class ReceiveClientSignup implements Runnable {

    /*
    port : 7001 SignUpPort 
    when client click sign up   
    pattern : "StudentTransfer" object --->  and server will answer with 1 if the doctor accepted him 
    and answer 0 if refused .
     */
    public final String RequestCompPath = "/lecturemanagement/Server/Main/NewRequest/RequestFXML.fxml";
    public final String ApproveIgnoreAllPath = "/lecturemanagement/Server/Main/ApproveIgnoreAll/ApproveIgnoreAllFXML.fxml";

    public static ArrayList<StudentTransfer> StudentSignUpRequestList = new ArrayList<>();  // for Approve/Ignore All only 
    public static ArrayList<Socket> StudentSignUpRequestSocketList = new ArrayList<>();  // for Approve/Ignore All only 

    private Socket server;
    private ApproveIgnoreAllFXMLController ApproveIgnoreAll;
    private ServerSocket serverSocket;
    private StorageManager st ;

    public void startListen() {

        st = new StorageManager();

        //--------------------------------------       
        ApproveIgnoreAll = (ApproveIgnoreAllFXMLController) new LoadFXML().LoadFXML(ref.RequestSignUpBox, ApproveIgnoreAllPath);
        try {
            serverSocket = new ServerSocket(SignUpPort);
        } catch (IOException ex) {
            Logger.getLogger(ReceiveClientSignup.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread th = new Thread(this);
        th.start();
        System.out.println("Start Accept Sign Up student...");
    }

    @Override
    public void run() {
        try {
            while (true) {
                server = serverSocket.accept();

                StudentSignUpRequestSocketList.add(server); // add socket
                ApproveIgnoreAll.setSendSocket(server); //  pass Socket to ApproveIgnoreAllController
                String IP = server.getInetAddress().toString();
                ObjectInputStream in = new ObjectInputStream(server.getInputStream()); // read an object
                StudentTransfer newStudent = (StudentTransfer) in.readObject();

                StudentSignUpRequestList.add(newStudent);     // add student to request   

                System.out.println(newStudent.getUser_name());

                if (isAlreadyDataUsed(newStudent)) {
                    SendIdDataUsed();
                    continue;
                }

                NewRequest(newStudent, IP); // make new request 

                plusOneRequestCounter();  // plus one to request 
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceiveClientSignup.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void NewRequest(StudentTransfer newStudent, String IP) {
        RequestFXMLController newRequest = (RequestFXMLController) new LoadFXML().LoadFXML(ref.RequestSignUpBox, RequestCompPath);
        newRequest.setStudent(newStudent);
        newRequest.setStudentIP(IP);
        newRequest.setSendSocket(server);
    }

    private void plusOneRequestCounter() {
        ref.SignUpCounterPane.setVisible(true);
        int plusOne = Integer.parseInt(ref.SignUpCounterText.getText()) + 1;
        ref.SignUpCounterText.setText(plusOne + "");
        System.out.println(ref.SignUpCounterText.getText());
        ref.SignUpCounterRect.setWidth(ref.SignUpCounterText.getText().length() * 4 - 4 + 14);
    }

    private boolean isAlreadyDataUsed(StudentTransfer newStudent) {
        retrieveData();
        String user_name = newStudent.getUser_name();
        String acadimic_id = newStudent.getAcademic_id();
        if (isUsernameAlreadyUsed(user_name)) {
            
            return true;
        }
        if (isAcadimicIdAlreadyUsed(acadimic_id)) {
            
            return true;
        }

        return false;
    }

    private void SendIdDataUsed() {
        try {
            DataOutputStream out = new DataOutputStream(server.getOutputStream());     // send answer to client 
            out.writeUTF("2");
        } catch (Exception e) {

        }
    }

    private ArrayList<String> academicids;
    private ArrayList<String> usernames;

    private void retrieveData() {
        academicids = new ArrayList();
        usernames = new ArrayList();

        ResultSet rs = st.performQuery("SELECT `user_id` , `user_name` FROM `user` WHERE `user_status` = 'Client'");

        try {
            while (rs.next()) {
                academicids.add(rs.getString(1));
                usernames.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println("Exception in Academic Ids , Usernames Retrieval !");
        }

    }

    private boolean isUsernameAlreadyUsed(String user_name) {
        // saad task
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println("username ==> "+usernames.get(i));
            if (user_name.equals(usernames.get(i))) {
                System.out.println("username false");
                return true;
            }
        }
        return false;
    }

    private boolean isAcadimicIdAlreadyUsed(String academic_id) {
        // saad task
        for (int i = 0; i < academicids.size(); i++) {
            System.out.println("ID ==> "+academicids.get(i));
            if (academic_id.equals(academicids.get(i))) {
                System.out.println("ID false");
                return true;
            }
        }
        return false;
    }

}
