package lecturemanagement.Server.login_signUp.view;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sign_logn extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/lecturemanagement/Server/login_signUp/view/login_signUp.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        }
        );
        primaryStage.setWidth(600);
        primaryStage.setHeight(720);
        primaryStage.setTitle("Student Page");
        primaryStage.show();

    }

    public static void main(String[] args) {
        PlatformImpl.setTaskbarApplication(false);

        launch(args);
    }

}
