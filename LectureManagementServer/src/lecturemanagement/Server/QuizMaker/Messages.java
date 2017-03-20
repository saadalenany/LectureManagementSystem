package lecturemanagement.Server.QuizMaker;

import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lecturemanagement.Utillity.undecoratedStage.undecoratedStageOptions;

public class Messages {

    JFXTextField input;
    int x;
    String choice;
    Stage stage;
    String str;

    public Messages(Stage stage, String str) {
        this.stage = stage;
        this.str = str;
        VBox bp = new VBox();
        bp.setPadding(new Insets(30, 30, 30, 30));
        bp.setSpacing(20);
        bp.setAlignment(Pos.CENTER);

        Label l = new Label(str);
        l.setFont(new Font(18));
        bp.getChildren().add(l);

        Button ok = new Button("OK");
        ok.setPrefSize(50, 25);
        ok.setOnAction(e -> {
            stage.close();
        });

        bp.getChildren().add(ok);

        Scene scene = new Scene(bp, 400, 100);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Warning!");
        stage.showAndWait();
    }

    public Messages(Stage stage) {
        this.stage = stage;

        VBox bp = new VBox();
        bp.setPadding(new Insets(30, 30, 30, 30));
        bp.setSpacing(20);
        bp.setAlignment(Pos.CENTER);

        input = new JFXTextField();
        input.setLabelFloat(true); 
        input.setPromptText("Lecture name!");

        bp.getChildren().add(input);

        Scene scene = new Scene(bp, 400, 100);

        bp.setStyle("-fx-background-color : #3a8abf ; -fx-text-fill : #fff; -fx-border-color : #494949; ");

        input.setOnAction(e -> {
            str = input.getText();
            stage.close();
        });

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Enter Lecture Name!");
        stage.showAndWait();
    }

    public Messages(Stage stage, int x) {
        this.x = x;
        this.stage = stage;

        VBox bp = new VBox();
        bp.setPadding(new Insets(30, 30, 30, 30));
        bp.setSpacing(20);
        bp.setAlignment(Pos.CENTER);

        input = new JFXTextField();
        input.setPromptText("choice " + (x + 1));

        bp.getChildren().add(input);

        Scene scene = new Scene(bp, 400, 100);

        input.setOnAction(e -> {
            choice = input.getText();
            stage.close();
        });

        stage.setScene(scene);
        stage.setTitle("Choice " + (x + 1));

        stage.showAndWait();
    }

    public String getFileName() {
        return str;
    }

    public String getChoice() {
        return choice;
    }
}
