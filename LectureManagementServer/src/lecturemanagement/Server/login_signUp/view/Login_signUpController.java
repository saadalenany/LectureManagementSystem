
package lecturemanagement.Server.login_signUp.view;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Login_signUpController implements Initializable {

    private String currentPane = "login";

    @FXML
    Pane sl_Pane;

    @FXML
    BorderPane sign_Pane;

    @FXML
    JFXButton btnSignIn, btnSignUp;

    @FXML
    HBox log_sign_btns;

    public void signUp() {

        if (currentPane.equals("login")) {
            TranslateTransition anime1 = new TranslateTransition(Duration.seconds(1.5), sl_Pane);
            anime1.setToX(-500);
            anime1.setToY(-546);
            anime1.play();

            ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), sl_Pane);
            scale.setToX(0);
            scale.setToY(0);
            scale.play();
            RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), sl_Pane);
            rotate.setFromAngle(0);
            rotate.setToAngle(360);
            rotate.play();

            anime1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Parent pane;
                    try {
                        pane = FXMLLoader.load(getClass().getResource("signUp.fxml"));
                        sl_Pane.getChildren().clear();

                        sl_Pane.getChildren().add(pane);
                    } catch (IOException ex) {
                        Logger.getLogger(Login_signUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    TranslateTransition anime2 = new TranslateTransition(Duration.seconds(1.5), sl_Pane);
                    anime2.setFromX(-500);
                    anime2.setFromY(-546);

                    anime2.setToX(0);
                    anime2.setToY(0);
                    anime2.play();

                    ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), sl_Pane);
                    scale.setToX(1);
                    scale.setToY(1);
                    scale.play();

                    RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), sl_Pane);
                    rotate.setFromAngle(360);
                    rotate.setToAngle(0);
                    rotate.play();

                }
            });
        }
        currentPane = "signup";

    }

    public void login() {

        if (currentPane.equals("signup")) {
            TranslateTransition anime1 = new TranslateTransition(Duration.seconds(1.5), sl_Pane);
            anime1.setToX(500);
            anime1.setToY(546);
            anime1.play();

            ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), sl_Pane);
            scale.setToX(0);
            scale.setToY(0);
            scale.play();
            RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), sl_Pane);
            rotate.setFromAngle(0);
            rotate.setToAngle(360);
            rotate.play();

            anime1.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Parent pane;
                    try {
                        pane = FXMLLoader.load(getClass().getResource("login.fxml"));
                        sl_Pane.getChildren().clear();

                        sl_Pane.getChildren().add(pane);
                    } catch (IOException ex) {
                        Logger.getLogger(Login_signUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    TranslateTransition anime2 = new TranslateTransition(Duration.seconds(1.5), sl_Pane);
                    anime2.setFromX(500);
                    anime2.setFromY(546);

                    anime2.setToX(0);
                    anime2.setToY(0);
                    anime2.play();

                    ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), sl_Pane);
                    scale.setToX(1);
                    scale.setToY(1);
                    scale.play();

                    RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), sl_Pane);
                    rotate.setFromAngle(360);
                    rotate.setToAngle(0);
                    rotate.play();

                }
            });
        }

        currentPane = "login";

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        log_sign_btns.prefWidthProperty().bind(sign_Pane.widthProperty());
        btnSignUp.prefWidthProperty().bind(log_sign_btns.widthProperty().divide(2));
        btnSignIn.prefWidthProperty().bind(log_sign_btns.widthProperty().divide(2));
        btnSignIn.prefHeightProperty().bind(log_sign_btns.heightProperty());
        btnSignUp.prefHeightProperty().bind(log_sign_btns.heightProperty());

        sl_Pane.prefHeightProperty().bind(sign_Pane.heightProperty().subtract(log_sign_btns.heightProperty()));
        sl_Pane.prefWidthProperty().bind(sign_Pane.widthProperty());

        try {
            Parent pane = FXMLLoader.load(getClass().getResource("login.fxml"));
            sl_Pane.getChildren().add(pane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
