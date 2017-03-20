/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.ApproveIgnoreAll;

import SerClass.StudentTransfer;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lecturemanagement.Server.Main.Control.ReceiveClientSignup;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class ApproveIgnoreAllFXMLController implements Initializable {

    private Socket sendSocket;   // for replay 
    private StorageManager sm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sm = new StorageManager();
    }

    @FXML
    void approveAllAction(ActionEvent event) throws IOException {
     // send response 
     // delete from list 
     // set counter to zero 
     // add students to database 
     // notify
     
        SendRequsetStatus('1');
        DeleteAllStudent();
        setRequestCounterToZero();
        addToDb();
        new notifier("status", "Approved");
    }

    @FXML
    void IgnoreAllAction(ActionEvent event) throws IOException {

        SendRequsetStatus('0');
        DeleteAllStudent();
        setRequestCounterToZero();
        new notifier("status", "Ignorded");
    }

    public Socket getSendSocket() {
        return sendSocket;
    }

    public void setSendSocket(Socket sendSocket) {
        this.sendSocket = sendSocket;
    }

    private void DeleteAllStudent() {
        ObservableList<Node> children = ref.RequestSignUpBox.getChildren();
        HBox ApproveIgnoreAllBox = (HBox) children.get(0);
        children.clear();
        children.add(ApproveIgnoreAllBox);
    }

    private void setRequestCounterToZero() {
        ref.SignUpCounterPane.setVisible(false);
        ref.SignUpCounterText.setText("0");
    }

    private void SendRequsetStatus(char status)  {
        for (int i = 0; i < ReceiveClientSignup.StudentSignUpRequestSocketList.size(); i++) {
            Socket socket = ReceiveClientSignup.StudentSignUpRequestSocketList.get(i);
            try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());     // send answer to client 
            out.writeUTF(status + "");
            }catch(Exception e){
                
            }
        }
        ReceiveClientSignup.StudentSignUpRequestSocketList.clear();

    }

    private void addToDb() {
        for (int i = 0; i < ReceiveClientSignup.StudentSignUpRequestList.size(); i++) {
            StudentTransfer student = ReceiveClientSignup.StudentSignUpRequestList.get(i);
            sm.saveStudentData(student.getAcademic_id(), student.getUser_name(), student.getUser_password(),
                    student.getUser_email(), student.getUser_phone(), student.getUser_gender(), student.getUser_department(), student.getAcademic_year());
        }


    }

}
