package lecturemanagement.Utility;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 *
 * @author Amr
 */
public class Actions {

    public void fixRightPostion(Node node, Scene scene) {

        scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
            node.setTranslateX((double) newSceneWidth - 20);
        });
//        scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
//            node.setTranslateY((double) newSceneHeight / 2);
//        });
    }

}
