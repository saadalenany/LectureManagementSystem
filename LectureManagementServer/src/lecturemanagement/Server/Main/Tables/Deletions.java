package lecturemanagement.Server.Main.Tables;

import lecturemanagement.Model.user;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lecturemanagement.Server.QuizMaker.Messages;
import lecturemanagement.StorageManager;

public class Deletions {

    public void delete(String tableName, String[] columnsNames, TableView table, TableColumn[] colarray, ObservableList database){
        ObservableList<user> selectedrow;
        selectedrow = table.getSelectionModel().getSelectedItems();

        int row = table.getSelectionModel().getSelectedIndex();
        System.out.println(row);
        int col;

        if (row == -1) {
            new Messages(new Stage(),"Please choose a record to delete!");
            return;
        }

        col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();
        System.out.println(col);

        new StorageManager().DeleteRow(tableName, columnsNames[0], col + "");
        selectedrow.forEach(database::remove);
        
    }

}
