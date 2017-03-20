package lecturemanagement.Server.Main.DataBase;

import com.jfoenix.controls.JFXTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lecturemanagement.Model.user;
import lecturemanagement.StorageManager;

/**
 *
 * @author Saad
 */
public class QuizChart extends HBox {

    NumberAxis xAxis;
    NumberAxis yAxis;

    ObservableList studentsnames, studentsmarks, quizesids;
    StorageManager st;

    FilteredList<String> filterdata;
    ListView listOfStudents;
    LineChart<Number, Number> lineChart;

    JFXTextField filterfield;

    int studid = 0;

    String str = "";

    public QuizChart() {

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        studentsnames = FXCollections.observableArrayList();
        studentsmarks = FXCollections.observableArrayList();
        quizesids = FXCollections.observableArrayList();

        filterfield = new JFXTextField();

        setAlignment(Pos.CENTER);

        st = new StorageManager();
        ResultSet rs = st.performQuery("SELECT user_name FROM user WHERE user_status = 'Client'");

        try {
            while (rs.next()) {
                studentsnames.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizChart.class.getName()).log(Level.SEVERE, null, ex);
        }

        listOfStudents = new ListView();
        listOfStudents.setStyle("-fx-background-color : #3a8abf ; -fx-text-fill : #fff; -fx-border-color : #494949; ");

        xAxis.setLabel("#Quiz ID");
        yAxis.setLabel("#Quiz Mark");

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle(str + " Quiz degree timeline");

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(1);
//        xAxis.setMinorTickVisible(false);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(1);
        yAxis.setUpperBound(10);
//        yAxis.setMinorTickVisible(false);

        //when pushing Enter button
        listOfStudents.setOnKeyTyped(e -> {
            reloadChart();
        });

        //when moving up & down
        listOfStudents.setOnKeyReleased(e -> {
            reloadChart();
        });

        //when selecting items by Mouse
        listOfStudents.setOnMouseClicked(e -> {
            reloadChart();
        });

        setPadding(new Insets(20, 0, 0, 10));
        setSpacing(20);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);

        filterfield.setLabelFloat(true);
        filterfield.setPromptText("Filter Students");

        vbox.getChildren().addAll(filterfield, listOfStudents);

        getChildren().addAll(lineChart, vbox);

        filterdata = new FilteredList<>(studentsnames, p -> true);

        listOfStudents.setItems(filterdata);

        filterfield.textProperty().addListener(obs -> {
            String filter = filterfield.getText();
            if (filter == null || filter.length() == 0) {
                filterdata.setPredicate(s -> true);
            } else {
                filterdata.setPredicate(s -> s.contains(filter));
            }
        });

    }

    private int getNumberOfAllQuizes() {
        int counter = 0;
        StorageManager st = new StorageManager();
        ResultSet rs = st.performQuery("SELECT `quiz_id` FROM `quiz`");
        try {
            while (rs.next()) {
                counter = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Exception in retrieving number of quizes!");
        }
        return counter;
    }

    private void reloadChart() {
        lineChart.getData().clear();

        XYChart.Series series = new XYChart.Series();
        series.setName("degree");

        studentsmarks.clear();
        quizesids.clear();

        str = listOfStudents.getSelectionModel().getSelectedItem().toString();
        ResultSet rs1 = st.performQuery("SELECT user_id FROM user where user_name = '" + str + "'");
        try {
            while (rs1.next()) {
                studid = rs1.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("ids exception");
        }

        ResultSet rs2 = st.performQuery("SELECT `quiz_id` , `student_mark` FROM `student_marks` WHERE `student_id`= " + studid);
        try {
            while (rs2.next()) {
                quizesids.add(rs2.getInt(1));
                studentsmarks.add(rs2.getInt(2));
            }
        } catch (SQLException ex) {
            System.out.println("marks exception");
        }

        lineChart.setTitle(str + " Quiz degree timeline");

        //populating the series with data
        for (int i = 0; i < quizesids.size(); i++) {
            series.getData().add(new XYChart.Data(quizesids.get(i), studentsmarks.get(i)));
        }

        xAxis.setUpperBound(getNumberOfAllQuizes() + 1);
        lineChart.getData().add(series);

    }

}
