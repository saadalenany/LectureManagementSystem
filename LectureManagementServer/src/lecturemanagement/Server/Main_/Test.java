/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main_;

/**
 *
 * @author Amr
 */
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Test extends Application {

    Double intx, inty;
    public static Stage MainStage;

    @Override
    public void start(final Stage stage) throws IOException {
        this.MainStage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        Group root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
        final Scene scene = new Scene(root, 600, 400);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
