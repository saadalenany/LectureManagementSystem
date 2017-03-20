/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.ChatRoom.StudentChat;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Shadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lecturemanagement.Client.ChatRoom.MessageBlock.MessageBlockFXMLController;
import lecturemanagement.Client.Main.control.MessageSocket;
import lecturemanagement.Utility.LoadFXML;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class StudentChatFXMLController implements Initializable {

    @FXML
    private Text usernameTxt;

    @FXML
    private TextField msgTxt;

    @FXML
    private VBox msgVBox;

    @FXML
    private BorderPane ChatRoot;

    @FXML
    private ScrollPane ScrollList;

    @FXML
    private HBox SendPane;

    @FXML
    private StackPane MsgPane;

    //----------------------------------------------
    private final int WIDTH = 245, HEIGHT = 290;
    private final int BAR_HEIGHT = 35;
    private final String MessageBlockPath = "/lecturemanagement/Client/ChatRoom/MessageBlock/MessageBlockFXML.fxml";
    private boolean isMinimize = false;

    //----------------------------------------------
    private Shadow ShadowEffect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ChatRoot.setPrefWidth(WIDTH);

    }

    @FXML
    void SendActionButton(ActionEvent event) throws IOException {
        SendAction();
    }

    @FXML
    void PressEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            SendAction();
        }
    }

    @FXML
    void TabBorderClick(MouseEvent event) {
        Stage stage = (Stage) ChatRoot.getScene().getWindow();
        if (isMinimize) {
            SendPane.setVisible(true);
            MsgPane.setVisible(true);
            ChatRoot.setPrefHeight(HEIGHT);
            stage.setHeight(HEIGHT);
            stage.setY(stage.getY() - HEIGHT + BAR_HEIGHT);
            isMinimize = false;
            return;
        }
        SendPane.setVisible(false);
        MsgPane.setVisible(false);
        ChatRoot.setPrefHeight(BAR_HEIGHT);
        stage.setHeight(BAR_HEIGHT);
        stage.setY(stage.getY() + HEIGHT - BAR_HEIGHT);
        isMinimize = true;
    }

    private void SendAction() throws IOException {
        if (msgTxt.getText().trim().length() == 0) {
            return;
        }
        new MessageSocket().StartListen(msgTxt.getText());
        MakeMessage(msgTxt.getText(), 's');
        msgTxt.setText("");
        ScrollList.setVvalue(2);   // to set bar to bottom 

    }

    @FXML
    void MouseEnterButtons(MouseEvent event) {
        ShadowEffect = new Shadow();
        ShadowEffect.setWidth(0);
        ShadowEffect.setHeight(0);
        ShadowEffect.setRadius(0);
        ShadowEffect.setColor(Color.web("#004cff"));
        JFXButton Btn = (JFXButton) event.getTarget();
        Btn.setEffect(ShadowEffect);
    }

    @FXML
    void MouseExitButtons(MouseEvent event) {
        ShadowEffect = new Shadow();
        ShadowEffect.setWidth(0);
        ShadowEffect.setHeight(0);
        ShadowEffect.setRadius(0);
        ShadowEffect.setColor(Color.web("#3F3F3F"));
        JFXButton Btn = (JFXButton) event.getTarget();
        Btn.setEffect(ShadowEffect);
    }

 

    public void MakeMessage(String msg, char s) {
        Platform.runLater(() -> {
            LoadFXML load = new LoadFXML();
            MessageBlockFXMLController control = (MessageBlockFXMLController) load.LoadFXML(msgVBox, MessageBlockPath);
            control.setMsg(msg, s);
            BorderPane Block = (BorderPane) load.getParent();
            VBox.setMargin(Block, new Insets(5, 5, 5, 5));

        });

    }

    public void setUsernameTxt(String usernameTxt) {
        this.usernameTxt.setText(usernameTxt);
    }


}
