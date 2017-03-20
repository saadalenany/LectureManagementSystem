/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Login.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static lecturemanagement.DataTransferProtocol.ServerAddress;
import static lecturemanagement.DataTransferProtocol.SignInOutPort;
import static lecturemanagement.ref.username;

public class LectureManagement extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Client Version");
        stage.setOnCloseRequest(e -> {
            sendLogoutToServer();
            System.exit(0);
        }
        );
        stage.setScene(scene);
        stage.show();
    }

    private void sendLogoutToServer() {
        try {
            Socket client = new Socket(ServerAddress, SignInOutPort);
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF(username);
        } catch (IOException ex) {
            Logger.getLogger(LectureManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
