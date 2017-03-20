package lecturemanagement.Server.Main.Tables;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lecturemanagement.Server.QuizMaker.Messages;
import lecturemanagement.StorageManager;
public class Insertions {

    public void insert(String tableName, String[] columnsNames, ArrayList<Control> inputs, ArrayList<String> values) {
        boolean flag = false;
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i) instanceof ComboBox) {
                ComboBox cb = (ComboBox) inputs.get(i);
                if (cb.getValue()== null) {
                    flag = false;
                } else {
                    flag = true;
                    System.out.println("values ==> " + cb.getValue().toString());
                }
            } else if (inputs.get(i) instanceof PasswordField) {
                PasswordField pf = (PasswordField) inputs.get(i);
                if (pf.getText().equals("")) {
                    flag = false;
                } else {
                    flag = true;
                    System.out.println("values ==> " + pf.getText());
                }
            } else if (inputs.get(i) instanceof TextField) {
                TextField tf = (TextField) inputs.get(i);
                if (tf.getText().equals("")) {
                    flag = false;
                } else {
                    flag = true;
                    System.out.println("values ==> " + tf.getText());
                }
            }
        }

        values = new ArrayList();
        if (!flag) {
            new Messages(new Stage(),"Please fill out all the fields!");
            return;
        } else {
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof ComboBox) {
                    ComboBox cb = (ComboBox) inputs.get(i);
                    values.add(cb.getValue().toString());
                    System.out.println(cb.getValue().toString());
                } else if (inputs.get(i) instanceof PasswordField) {
                    PasswordField pf = (PasswordField) inputs.get(i);
                    values.add(pf.getText());
                    System.out.println(pf.getText());
                } else if (inputs.get(i) instanceof TextField) {
                    TextField tf = (TextField) inputs.get(i);
                    values.add(tf.getText());
                    System.out.println(tf.getText());
                }
            }
            new StorageManager().insertInto(tableName, columnsNames, values);
            new ShowTables();
        }
    }
}
