/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Login.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lecturemanagement.Client.ChatRoom.StudentChat.StudentChatFXMLController;
import lecturemanagement.Client.Main.control.MessageSocket;
import lecturemanagement.DataTransferProtocol;
import lecturemanagement.Utility.LoadFXML;
import lecturemanagement.Utility.ScreenTools;
import lecturemanagement.Utility.notifier;
import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class LoginSocket implements Runnable {

    private Socket socket;
    private String username, password;
    private DataInputStream input;
    private DataOutputStream outPut;
    private Scene nextScene;
    private final String WatingPath = "/lecturemanagement/Client/Waiting/view/WaitingFXML.fxml";
    private final String ChatPath = "/lecturemanagement/Client/ChatRoom/StudentChat/StudentChatFXML.fxml";
    public static StudentChatFXMLController ChatController;

    public LoginSocket(String username, String password, Scene nextScene) {
        this.nextScene = nextScene;
        this.username = username;
        this.password = password;

    }

    public void SendLoginInfo() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        try {
            socket = new Socket(DataTransferProtocol.ServerAddress, DataTransferProtocol.SignInOutPort);
            input = new DataInputStream(socket.getInputStream());
            outPut = new DataOutputStream(socket.getOutputStream());
            outPut.writeUTF(username + "-" + password);
            String state = input.readUTF();
            moveToNextStage(state);
        } catch (IOException ex) {
            Logger.getLogger(LoginSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void moveToNextStage(String state) {
        Platform.runLater(() -> {
            if ("1".equals(state)) {
                ref.username = username;    // set username 
                new LoadFXML().LoadFXML(nextScene, WatingPath);
                LoadChat();
                new notifier("Wellcome", "You are Logged in ");
                //--------------------------------
                new MessageSocket().StartListen(); // Start Listen Socket
                //--------------------------------
                return;
            } else if ("2".equals(state)) {
                new notifier("error", "You already Logged  ");
                return;
            }
            new notifier("error", "Incorrect username or password  ");
        });
    }

    private void LoadChat() {
      
        LoadFXML load = new LoadFXML();
        load.LoadFXMLRootedStage((Stage) nextScene.getWindow(), ChatPath);
        ChatController = (StudentChatFXMLController) load.getController();
        Stage stage  = load.getStage();
        ScreenTools screen = new ScreenTools();  
        stage.setX(screen.getScreenWidth() - stage.getWidth());
        stage.setY(screen.getScreenHeight() - stage.getHeight() - screen.getTaskBarHeight());
    }

}
