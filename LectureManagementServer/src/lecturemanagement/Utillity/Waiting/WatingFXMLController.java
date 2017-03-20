package lecturemanagement.Utillity.Waiting;

import com.jfoenix.transitions.JFXFillTransition;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 *
 * @author Amr
 */
public class WatingFXMLController implements Initializable {

    @FXML
    private Text WaitingText;

    //---------------------------------------------------------------------
    private ArrayList<JFXFillTransition> transList = new ArrayList<>();
    private ColorTransLoop colorTransLoop;
    private ZoomInZoomOutText zoom;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        ArrayList<Color> colorList = new ArrayList<>();
//        colorList.add(Color.web("#8A2908"));
//        colorList.add(Color.web("#610B4B"));
//        colorList.add(Color.web("#210B61"));
//        colorList.add(Color.web("#0B610B"));
//        colorList.add(Color.web("#B18904"));
//        colorTransLoop = new ColorTransLoop(colorList, 7000, waitingPane);
//        colorTransLoop.play();
        zoom = new ZoomInZoomOutText(WaitingText, 20, 60);
        zoom.setFamilyFont("Bauhaus 93");
        zoom.play();
    }

    public void stop() {
        zoom.stop();
    }

}
