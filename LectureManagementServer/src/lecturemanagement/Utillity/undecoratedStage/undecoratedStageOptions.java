/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Utillity.undecoratedStage;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Amr
 */
public class undecoratedStageOptions {

    private Stage stage;
    private Pane root;

    public undecoratedStageOptions(Stage stage, Pane root) {
        this.stage = stage;
        this.root = root;
    }

    public void DragStage() {
        new DragStage(root, stage);
    }

    public void ReSizeHelper() {
        new DragStage(root, stage);
        ResizeHelper.addResizeListener(stage);
    }

    public void fullTools() {
        new DragStage(root, stage);
        new DragStage(root, stage);
        ResizeHelper.addResizeListener(stage);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

}
