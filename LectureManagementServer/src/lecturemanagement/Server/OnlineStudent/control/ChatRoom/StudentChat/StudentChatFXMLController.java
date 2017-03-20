/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.OnlineStudent.control.ChatRoom.StudentChat;

import com.jfoenix.controls.JFXButton;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import lecturemanagement.Server.OnlineStudent.control.ChatRoom.MessageBlock.MessageBlockFXMLController;
import lecturemanagement.Server.OnlineStudent.control.ChatRoom.model.Message;
import static lecturemanagement.Server.OnlineStudent.view.OnlineFXMLController.MAIN_CHAT_WIDTH;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.ScreenTools;
import lecturemanagement.ref;
import static lecturemanagement.ref.OpenedChatsMap;

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
    private final String MessageBlockPath = "/lecturemanagement/Server/OnlineStudent/control/ChatRoom/MessageBlock/MessageBlockFXML.fxml";
    private boolean isMinimize = false;

    //----------------------------------------------
    private Socket StudentSocket;
    private ArrayList<Message> msgList;
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
        DataOutputStream out = new DataOutputStream(StudentSocket.getOutputStream());
        out.writeUTF(msgTxt.getText());
        msgList.add(new Message(msgTxt.getText(), 'd')); // msg from doctor
        MakeMessage(msgTxt.getText(), 'd');
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

    @FXML
    void CloseAction(ActionEvent event) {
        Stage stage = (Stage) ChatRoot.getScene().getWindow();
        stage.close();
        ref.OpenedChatsMap.remove(usernameTxt.getText());
        //shift all charts 
        shiftAll();
    }

    private void shiftAll() {
        ArrayList<Stage> locations = new ArrayList<>();
        Set mapValues = OpenedChatsMap.entrySet();
        Iterator it = mapValues.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Stage> entry = (Map.Entry) it.next();
            Stage stage = entry.getValue();
            locations.add(stage);
        }
        //----------------------------------------------------------------------------------------
        Collections.sort(locations, (Stage s1, Stage s2) -> Integer.compare((int) s1.getX(), (int) s2.getX()));
        Collections.reverse(locations);
        //---------------------------------------------------------------------------------------- 
        int gap = 15;
        ScreenTools screen = new ScreenTools();
        int numberOfChats = 0;

        int lastChatX = screen.getScreenWidth() - MAIN_CHAT_WIDTH - gap - (numberOfChats * WIDTH) - (numberOfChats * gap) - WIDTH;
        if (locations.size() == 1) {
            locations.get(0).setX(lastChatX);
            return;
        }
        for (int i = 0; i < locations.size(); i++) {
            int X = screen.getScreenWidth() - MAIN_CHAT_WIDTH - gap - (numberOfChats * WIDTH) - (numberOfChats * gap) - WIDTH;
            Stage tstage = locations.get(i);
            if (X == tstage.getX()) {
                lastChatX = X;
            } else {
                int newPos = X;
                for (int j = i; j < locations.size(); j++) {
                    tstage = locations.get(j);
                    tstage.setX(newPos);
                    X += WIDTH;
                }
            }
            numberOfChats++;
        }
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

    private void LoadPreviousMessage() {
        for (int i = 0; i < msgList.size(); i++) {
            Message msg = msgList.get(i);
            MakeMessage(msg.getMessage(), msg.getType());
        }
    }

    public ArrayList<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(ArrayList<Message> msgList) {
        this.msgList = msgList;
        LoadPreviousMessage();
    }

    public void setUsernameTxt(String usernameTxt) {
        this.usernameTxt.setText(usernameTxt);
    }

    public void setStudentSocket(Socket StudentSocket) {
        this.StudentSocket = StudentSocket;
    }

}
