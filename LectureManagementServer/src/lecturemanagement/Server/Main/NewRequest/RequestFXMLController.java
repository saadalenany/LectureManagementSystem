/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.NewRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lecturemanagement.Utillity.notifier;
import SerClass.StudentTransfer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lecturemanagement.Server.Main.StudentInfoWindow.StudentInfoWindowController;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.undecoratedStage.undecoratedStageOptions;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class RequestFXMLController implements Initializable {

    @FXML
    private Text usernameText;
    private StudentTransfer student;
    private StorageManager sm;
    private String StudentIP;
    private Socket sendSocket;   // for replay 

    public final String StudentInfoWindowPath = "/lecturemanagement/Server/Main/StudentInfoWindow/StudentInfoWindow.fxml";

    @FXML
    void approveButtonAction(ActionEvent event) throws IOException {
        // send 1 to client
        // add to database  
        // refresh Menu if running else add to List
        // remove student from Vbox 
        try {
            DataOutputStream out = new DataOutputStream(sendSocket.getOutputStream());     // send answer to client 
            out.writeUTF("1");
        } catch (Exception e) {

        }

        sm.saveStudentData(student.getAcademic_id(), student.getUser_name(), student.getUser_password(),
                student.getUser_email(), student.getUser_phone(), student.getUser_gender(), student.getUser_department(), student.getAcademic_year());
        new notifier("status", "Approved");

        DeleteThisFromBoxStudent();
    }

    private void DeleteThisFromBoxStudent() {
        ObservableList<Node> children = ref.RequestSignUpBox.getChildren();

        for (int i = 0; i < children.size(); i++) {
            VBox subVbox = null;
            try {
                subVbox = (VBox) children.get(i);
            } catch (Exception x) {
                continue;
            }
            HBox subHbox = (HBox) subVbox.getChildren().get(0);
            Text text = (Text) subHbox.getChildren().get(0);
            if (text.getText().equals(usernameText.getText())) {
                children.remove(i);
                minusOneRequestCounter(); // -----> minus counter by 1 
            }
        }

    }

    @FXML
    void ignoreButtonAction(ActionEvent event) {
        // send 0 to client
        // remove student from Vbox 
        new notifier("status", "Ignored");
        try {
            DataOutputStream out = new DataOutputStream(sendSocket.getOutputStream());     // send answer to client 
            out.writeUTF("0");
        } catch (Exception e) {

        }
        DeleteThisFromBoxStudent();

    }

    @FXML
    void infoButtonAction(ActionEvent event) {
        // show new stage  with information
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);   
        LoadFXML loader = new LoadFXML();
        Parent root = loader.LoadFXML(StudentInfoWindowPath);
        StudentInfoWindowController control = (StudentInfoWindowController) loader.getController();
        HBox border = control.getBorderBox();
        control.setStudent(student);
        new undecoratedStageOptions(stage, border).DragStage();  
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sm = new StorageManager();
    }

    private void minusOneRequestCounter() {
        int minusOne = Integer.parseInt(ref.SignUpCounterText.getText()) - 1;
        if (minusOne == 0) {
            ref.SignUpCounterPane.setVisible(false);
            ref.SignUpCounterText.setText("0");
            return;
        }
        ref.SignUpCounterText.setText(minusOne + "");

        ref.SignUpCounterRect.setWidth(ref.SignUpCounterText.getText().length() * 4 - 4 + 14);
    }

    public StudentTransfer getStudent() {
        return student;
    }

    public void setStudent(StudentTransfer student) {
        this.student = student;
        usernameText.setText(student.getUser_name());

    }

    public String getStudentIP() {
        return StudentIP;
    }

    public void setStudentIP(String StudentIP) {
        this.StudentIP = StudentIP;
    }

    public Socket getSendSocket() {
        return sendSocket;
    }

    public void setSendSocket(Socket sendSocket) {
        this.sendSocket = sendSocket;
    }

}
