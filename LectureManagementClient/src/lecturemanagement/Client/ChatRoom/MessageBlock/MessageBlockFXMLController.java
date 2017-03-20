/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.ChatRoom.MessageBlock;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class MessageBlockFXMLController implements Initializable {

    @FXML
    private ImageView imgView;

    @FXML
    private Text msgTxt;

    @FXML
    private BorderPane MsgRoot;
    //---------------------------------------
    private final String DOCTOR_IMG_PATH = "/resource/doctor.png";
    private final String STUDENT_IMG_PATH = "/resource/student.png";
    private final String initialStyle = "-fx-border-radius : 10;  -fx-background-radius : 10; ";
    private final String MSG_GROUND_DOCTOR_COLOR = "#424242";
    private final String MSG_GROUND_STUDENT_COLOR = "#004BA6";

    private final int WIDTH = 220;
    private final int WARP_TXT = 175;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MsgRoot.setPrefWidth(WIDTH);
        msgTxt.setWrappingWidth(WARP_TXT);
        imgView.setFitWidth(20);
        imgView.setFitHeight(20);
        msgTxt.setTextAlignment(TextAlignment.LEFT);
    }

    public void setMsg(String msg, char type) {
        msgTxt.setText(msg);
        MsgRoot.setLeft(null);
        MsgRoot.setRight(null);
        if (type == 'd') {
            isDoctorMsg();
        } else {
            isStudentMsg();
        }
    }

    private void isDoctorMsg() {
        imgView.setImage(new Image(DOCTOR_IMG_PATH));
        MsgRoot.setLeft(imgView);
        BorderPane.setAlignment(imgView, Pos.TOP_LEFT);
        BorderPane.setMargin(imgView, new Insets(5, 0, 5, 5));
        MsgRoot.setStyle(initialStyle + " -fx-background-color :" + MSG_GROUND_DOCTOR_COLOR + ";");
    }

    private void isStudentMsg() {
        imgView.setImage(new Image(STUDENT_IMG_PATH));
        MsgRoot.setRight(imgView);
        BorderPane.setMargin(imgView,new Insets(5, 5, 5, 0));
        BorderPane.setAlignment(imgView, Pos.TOP_RIGHT);
        MsgRoot.setStyle(initialStyle + " -fx-background-color :" + MSG_GROUND_STUDENT_COLOR + ";");
    }

}
