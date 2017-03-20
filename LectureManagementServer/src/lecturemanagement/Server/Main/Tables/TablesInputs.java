package lecturemanagement.Server.Main.Tables;

import lecturemanagement.Model.user;
import lecturemanagement.Model.student;
import lecturemanagement.Model.quizquestion;
import lecturemanagement.Model.question;
import lecturemanagement.Model.slides;
import lecturemanagement.Model.quiz;
import lecturemanagement.Model.doctor;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lecturemanagement.StorageManager;
public class TablesInputs {

    ArrayList<Control> inputs = new ArrayList<>();
    Button insert, delete;
    String tableName;
    String[] columnsNames;
    TableView table;
    TableColumn[] colarray;
    ObservableList database;
    ArrayList<String> values;

    public TablesInputs(String tableName, String[] columnsNames, TableView table, TableColumn[] colarray, ObservableList database) {
        this.tableName = tableName;
        this.columnsNames = columnsNames;
        this.table = table;
        this.colarray = colarray;
        this.database = database;
    }

    public VBox getInputs() {
        for (String columnsName : columnsNames) {
            if (columnsName.contains("_gender")) {
                ComboBox cb = new ComboBox(FXCollections.observableArrayList("male", "female"));
                cb.setPromptText(columnsName);
                cb.setPrefSize(150, 25);
                inputs.add(cb);
                System.out.println("\ngender input added ==> " + inputs.size());
            } else if (columnsName.contains("_department")) {
                ComboBox cb = new ComboBox(FXCollections.observableArrayList("General", "CS", "IT", "IS", "OR"));
                cb.setPromptText(columnsName);
                cb.setPrefSize(150, 25);
                inputs.add(cb);
                System.out.println("\ndepartment input added ==> " + inputs.size());
            } else if (columnsName.contains("_password")) {
                PasswordField pf = new PasswordField();
                pf.setPromptText(columnsName);
                pf.setPrefSize(150, 25);
                inputs.add(pf);
                System.out.println("\npassword input added ==> " + inputs.size());
            } else if (columnsName.equalsIgnoreCase("doctor_id") || columnsName.equalsIgnoreCase("student_id")) {
                ComboBox cb = new ComboBox(new StorageManager().getIds());
                cb.setPromptText(columnsName);
                cb.setPrefSize(150, 25);
                inputs.add(cb);
                System.out.println("\nusers_id input added ==> " + inputs.size());
            } else {
                TextField tf = new TextField();
                tf.setPromptText(columnsName);
                tf.setPrefSize(150, 25);
                // if input was salary or age "number" validate it so the user enters only a number
                if (columnsName.contains("number") || columnsName.contains("id")) {
                    new Validations().ValidateNumber(tf);
                }
                if (columnsName.contains("_name")) {
                    new Validations().ValidateName(tf);
                }
                if (columnsName.contains("phone")) {
                    new Validations().ValidatePhone(tf);
                }
                if (columnsName.contains("_email")) {
                    new Validations().ValidateEmail(tf);
                }
                inputs.add(tf);
                System.out.println("\nregular textfield input added ==> " + inputs.size());
            }
        }

        HBox controllerButtons = new HBox();
        controllerButtons.setPadding(new Insets(10, 20, 0, 20));
        controllerButtons.setSpacing(30);

        insert = new Button("Insert");
        delete = new Button("Delete");

        insert.setPrefSize(150, 30);
        delete.setPrefSize(150, 30);

        controllerButtons.getChildren().addAll(insert, delete);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(0, 0, 20, 20));

        //hbox which contains the input fields
        HBox inputsBox = new HBox();
        inputsBox.setPadding(new Insets(0, 20, 0, 20));
        inputsBox.setSpacing(10);

        for (int i = 0; i < inputs.size(); i++) {
            inputsBox.getChildren().add(inputs.get(i));
        }

        vbox.getChildren().addAll(inputsBox, controllerButtons);

        insert.setOnAction(e -> {
            new Insertions().insert(tableName, columnsNames, inputs, values);
            new ShowTables();
        });

        delete.setOnAction(e -> {
            new Deletions().delete(tableName, columnsNames, table, colarray, database);
        });

//        new Updates().update(tableName, columnsNames, table, colarray, database);

        if (tableName.equals("user")) {
            FilteredList<user> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInUser((TextField) inputs.get(i), filtered, table);
                }
            }
        } else if (tableName.equals("doctor")) {
            FilteredList<doctor> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInDoctor((TextField) inputs.get(i), filtered, table);
                }
            }
        } else if (tableName.equals("student")) {
            FilteredList<student> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInStudent((TextField) inputs.get(i), filtered, table);
                }
            }
        } else if (tableName.equals("slides")) {
            FilteredList<slides> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInSlides((TextField) inputs.get(i), filtered, table);
                }
            }
        } else if (tableName.equals("quiz")) {
            FilteredList<quiz> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInQuiz((TextField) inputs.get(i), filtered, table);
                }
            }
        } else if (tableName.equals("quizquestion")) {
            FilteredList<quizquestion> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInQuizQuestion((TextField) inputs.get(i), filtered, table);
                }
            }
        } else if (tableName.equals("question")) {
            FilteredList<question> filtered = new FilteredList(database);
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) instanceof TextField) {
                    new Searching().searchInQuestion((TextField) inputs.get(i), filtered, table);
                }
            }
        }

        return vbox;
    }
}
