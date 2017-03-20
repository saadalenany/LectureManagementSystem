package lecturemanagement.Server.OnlineStudent.control.StudentRow;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class StudentRowController implements Initializable {

    @FXML
    private BorderPane StudentRowPane;

    @FXML
    private Circle MsgNotifierCircle;
    @FXML
    private Text usernameTxt;

    public static final int WIDTH = 260, HEIGHT = 45;
    private final String STANDARD_PANE_COLOR = "#E6E6E6";
    private final String HOVERED_PANE_COLOR = "#F2F2F2";

    @FXML
    void EnteredRow(MouseEvent event) {
        StudentRowPane.setStyle("-fx-background-color :" + HOVERED_PANE_COLOR + " ;");
    }

    @FXML
    void ExitRow(MouseEvent event) {
        StudentRowPane.setStyle("-fx-background-color:" + STANDARD_PANE_COLOR + " ;");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StudentRowPane.setPrefSize(WIDTH, HEIGHT);
    }

    public BorderPane getStudentRowPane() {
        return StudentRowPane;
    }

    public Circle getMsgNotifierCircle() {
        return MsgNotifierCircle;
    }

    public Text getUsernameTxt() {
        return usernameTxt;
    }

}
