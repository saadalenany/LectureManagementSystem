package lecturemanagement.Client.Main.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lecturemanagement.Client.Main.control.MessageSocket;
import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class MainFXMLController implements Initializable {

    @FXML
    private StackPane lecturePane;
    @FXML
    private BorderPane RootPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ref.RootPane = RootPane;
    }

    public StackPane getLecturePane() {
        return lecturePane;
    }

}
