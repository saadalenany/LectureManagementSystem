package lecturemanagement.Server.login_signUp.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static lecturemanagement.Server.Main_.MainFXMLController.RECT_WIDTH;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.ScreenTools;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;

public class LoginController implements Initializable {

    @FXML
    JFXButton btnLogin;

    @FXML
    JFXTextField txtusername;
    @FXML
    JFXPasswordField txtpassword;

    Socket socket;
    DataInputStream input;
    DataOutputStream outPut;
    private final String ServerMainPath = "/lecturemanagement/Server/Main/view/MainFXML.fxml";
    private final String ServerMainPath_ = "/lecturemanagement/Server/Main_/MainFXML.fxml";
    private final String ServerSignUpPath = "/lecturemanagement/Server/Signup/SignUpFXML.fxml";
    private final String OnlineStudentPath = "/lecturemanagement/Server/OnlineStudent/view/OnlineFXML.fxml";
    Parent root;
    private StorageManager stg;

    private Scene scene;

    @FXML
    private void LoginButtonAction(ActionEvent event) {
        scene = btnLogin.getScene();
        if (!stg.checkDoctorLogIn(txtusername.getText(), txtpassword.getText())) {
            new notifier("Warning", "please , check your user name and password or SignUp  ");
            return;
        }
        new notifier("Wellcome", "You are Logged in ");
        ref.username = txtusername.getText();
        // --------- main  
        LoadFXML loader = new LoadFXML();
        ref.rootStage = loader.LoadFXMLWithTransparentSrage(ServerMainPath_);
        relocateMainStage();
        Stage stage = (Stage) this.scene.getWindow();
        stage.close();
        // --------- onlineStudent  
        LoadOnlineStudent();
    }

    @FXML
    private void signUpAction(ActionEvent event) {
        scene = btnLogin.getScene();
        new LoadFXML().LoadFXML(scene, ServerSignUpPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stg = new StorageManager();
    }

    private void LoadOnlineStudent() {
        ScreenTools screen = new ScreenTools();
        Stage stage = new LoadFXML().LoadFXMLRootedStage(OnlineStudentPath);
        stage.setX(screen.getScreenWidth() - stage.getWidth());
        stage.setY(screen.getScreenHeight() - stage.getHeight() - screen.getTaskBarHeight());
    }

    private void relocateMainStage() {
        ScreenTools screen = new ScreenTools();
        int X = (int) (screen.getScreenWidth() / 2 - RECT_WIDTH / 2);
        ref.rootStage.setX(X);
        ref.rootStage.setY(-20);
    }

}
