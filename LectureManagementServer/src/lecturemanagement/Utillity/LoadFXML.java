/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Utillity;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class LoadFXML {

    private Stage stage;
    private Scene scene;
    private Parent parent;
    private Object controller;

    public Parent LoadFXML(Scene scene, String FXMLPath) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(FXMLPath));
            scene.setRoot(root);
            this.parent = root;
            this.scene = scene;
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return root;
    }

    public Object LoadFXML(Pane pane, String FXMLPath) {

        FXMLLoader loader = null;
        Parent root = null;
        try {
            loader = new FXMLLoader(getClass().getResource(FXMLPath));
            root = (Parent) loader.load();
            pane.getChildren().add(root);
            this.parent = root;
            this.controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return loader.getController();
    }

    public Object LoadFXMLAndRemovePrevious(Pane pane, String FXMLPath) {
        FXMLLoader loader = null;
        Parent root = null;
        try {
            loader = new FXMLLoader(getClass().getResource(FXMLPath));
            root = (Parent) loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(root);
            this.parent = root;
            this.controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loader.getController();
    }

    public Object LoadFXMLWithStage(String FXMLPath) {
        FXMLLoader loader = null;
        Parent root = null;
        try {
            loader = new FXMLLoader(getClass().getResource(FXMLPath));
            root = (Parent) loader.load();
            stage = new Stage();
            stage.setScene(new Scene(new StackPane(root)));
            stage.show();
            this.parent = root;
            this.controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loader.getController();
    }

    public Stage LoadFXMLRootedStage(String FXMLPath) {
        FXMLLoader loader = null;
        Parent root = null;
        stage = null;
        try {
            loader = new FXMLLoader(getClass().getResource(FXMLPath));
            root = (Parent) loader.load();
            this.parent = root;
            this.controller = loader.getController();
            stage = new Stage(StageStyle.UNDECORATED);
            stage.initOwner(ref.rootStage);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stage;
    }

    public Stage LoadFXMLUtilityStage(String FXMLPath) {
        FXMLLoader loader = null;
        Parent root = null;
        stage = null;
        try {
            loader = new FXMLLoader(getClass().getResource(FXMLPath));
            root = (Parent) loader.load();
            this.parent = root;
            this.controller = loader.getController();
            stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stage;
    }

    public Parent LoadFXML(String FXMLPath) {
        FXMLLoader loader = null;
        Parent root = null;
        try {
            loader = new FXMLLoader(getClass().getResource(FXMLPath));
            root = (Parent) loader.load();
            this.parent = root;
            this.controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return root;
    }

    public Stage LoadFXMLWithTransparentSrage(String FXMLPath) {

        Parent root = null;
        Stage InStage = new Stage();
        InStage.initStyle(StageStyle.TRANSPARENT);
        try {
            root = FXMLLoader.load(getClass().getResource(FXMLPath));
            Scene InScene = new Scene(root);
            InScene.setFill(Color.TRANSPARENT);
            InStage.setScene(InScene);
            InStage.show();
            this.parent = root;
            this.stage = InStage;
            this.scene = InScene;
        } catch (IOException ex) {
            Logger.getLogger(LoadFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return InStage;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getParent() {
        return parent;
    }

    public Object getController() {
        return controller;
    }

}
