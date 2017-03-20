package lecturemanagement.Client.Waiting.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import lecturemanagement.Client.Main.control.MessageSocket;
import lecturemanagement.Client.Waiting.control.ReceiveLectureListener;

/**
 *
 * @author Amr
 */
public class WaitingFXMLController implements Initializable {

    @FXML
    private BorderPane background;

    private static boolean isMessageSocketListen = false;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //  new LoadFXML().LoadFXML(MainButton.getScene(), ClientWatingPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReceiveLectureListener lec = new ReceiveLectureListener();
        lec.StartRecieveLecture();


    }

}
