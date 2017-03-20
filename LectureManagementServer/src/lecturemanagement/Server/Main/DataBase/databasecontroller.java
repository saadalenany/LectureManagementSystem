package lecturemanagement.Server.Main.DataBase;

import com.jfoenix.controls.JFXTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lecturemanagement.StorageManager;

/**
 *
 * @author Saad
 */

public class databasecontroller extends TabPane{

    StorageManager st;
    TableView table;
    ObservableList<StudentAbsence> data ;
    JFXTextField filterfield;

    public databasecontroller(){
        table = new TableView();
        data = FXCollections.observableArrayList();
        st = new StorageManager();
        filterfield = new JFXTextField();

        try {
            ResultSet rs = st.performQuery("select user.user_name , user.user_department , lecture.lecture_name from user\n" +
                                            "inner join student on user.user_id = student.student_id\n" +
                                            "inner join absence on student.student_id = absence.student_id \n" +
                                            "inner join lecture on absence.lecture_id = lecture.lecture_id order by lecture.lecture_name");
            while(rs.next()){
                data.add(new StudentAbsence(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(databasecontroller.class.getName()).log(Level.SEVERE, null, ex);
        }

        TableColumn username = new TableColumn("Student Name");
        TableColumn dep = new TableColumn("Student Department");
        TableColumn lecture_name = new TableColumn("Lecture Name");

        username.setPrefWidth(150);
        dep.setPrefWidth(150);
        lecture_name.setPrefWidth(150);

        username.setCellValueFactory(new PropertyValueFactory("user_name"));
        dep.setCellValueFactory(new PropertyValueFactory("user_department"));
        lecture_name.setCellValueFactory(new PropertyValueFactory("lecture_name"));

        table.getColumns().addAll(username,dep,lecture_name);

        //saad task
        VBox filterAndtable = new VBox();
        filterAndtable.setAlignment(Pos.CENTER);
        filterAndtable.setSpacing(20);
        filterAndtable.setPadding(new Insets(20,4,5,4));

        filterAndtable.getChildren().addAll(filterfield,table);

        filterfield.setLabelFloat(true);
        filterfield.setPromptText("Filter Absence");

        FilteredList<StudentAbsence> filterdata = new FilteredList<>(data,p -> true);

        filterfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filterdata.setPredicate(new Predicate<StudentAbsence>() {
                @Override
                public boolean test(StudentAbsence sa) {
                    // If filter text is empty, display all data.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if(sa.getUser_name().toLowerCase().contains(lowerCaseFilter)){
                        return true; //filter matches student name
                    } else if(sa.getUser_department().toLowerCase().contains(lowerCaseFilter)){
                        return true; //filter matches student department
                    } else if (sa.getLecture_name().toLowerCase().contains(lowerCaseFilter)){
                        return true; //filter matches lecture name
                    }
                    return false;
                }
            });
        });

        //bind sorted list comparator to tableview comparator in order of sorting
        SortedList<StudentAbsence> sortedlist = new SortedList<>(filterdata);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);

        Tab tab1 = new Tab();
        tab1.setText("Absence Table");
        tab1.setContent(filterAndtable);

        Tab tab2 = new Tab();
        tab2.setText("Quiz Chart");
        tab2.setContent(new QuizChart());

        getTabs().addAll(tab1,tab2,new AbsenceRate());
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

}
