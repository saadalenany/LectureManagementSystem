package lecturemanagement.Server.Main.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import lecturemanagement.StorageManager;

/**
 *
 * @author Saad
 */
public class AbsenceRate extends Tab{

    NumberAxis xAxis;
    NumberAxis yAxis;

    ObservableList<String> lecturesnames;
    ObservableList<Integer> nos, lectures_ids;
    StorageManager st;

    ListView listOfLectures;
    LineChart<Number, Number> lineChart;

    public AbsenceRate() {

        lectures_ids = FXCollections.observableArrayList();
        lecturesnames = FXCollections.observableArrayList();
        nos = FXCollections.observableArrayList();

        st = new StorageManager();
        ResultSet rs = st.performQuery("SELECT lecture_id , lecture_name FROM lecture");

        System.out.println("Lecture_id\tLecture_name");
        try {
            while(rs.next()){
                lectures_ids.add(rs.getInt(1));
                lecturesnames.add(rs.getString(2));
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbsenceRate.class.getName()).log(Level.SEVERE, null, ex);
        }

        listOfLectures = new ListView();
        listOfLectures.setStyle("-fx-background-color : #3a8abf ;-fx-text-fill : #fff;-fx-border-color : #494949;");

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Lecture Absence timeline");

        yAxis.setLabel("number of Students");

        System.out.print("number of students ==> ");
        for(int i=0 ; i<lectures_ids.size() ; i++){
            ResultSet rs1 = st.performQuery("SELECT student_id FROM absence WHERE lecture_id = "+lectures_ids.get(i));
            int counter = 0 ;
            try {
                while(rs1.next()){
                    counter++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AbsenceRate.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print(counter+"\t");
            nos.add(counter);
        }
        System.out.println();

        listOfLectures.setItems(lecturesnames);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(1);
        xAxis.setUpperBound(2);
//        xAxis.setMinorTickVisible(false);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(1);
//        yAxis.setMinorTickVisible(false);

        //when pushing Enter button
        listOfLectures.setOnKeyTyped(e -> {
            reloadChart();
        });

        //when moving up & down
        listOfLectures.setOnKeyReleased(e -> {
            reloadChart();
        });

        //when selecting items by Mouse
        listOfLectures.setOnMouseClicked(e -> {
            reloadChart();
        });

        HBox hbox = new HBox();

        hbox.setPadding(new Insets(20, 0, 0, 10));
        hbox.setSpacing(20);

        hbox.getChildren().addAll(lineChart,listOfLectures);

        setText("Absence Rate");
        setContent(hbox);
    }

    private void reloadChart() {
        lineChart.getData().clear();

        XYChart.Series series = new XYChart.Series();
        series.setName("Rate");

        //populating the series with data
        String str = listOfLectures.getSelectionModel().getSelectedItem().toString();

        int lecture_index = lecturesnames.indexOf(str);

        series.getData().add(new XYChart.Data(1, nos.get(lecture_index)));

        yAxis.setUpperBound(nos.get(lecture_index)+1);

        lineChart.getData().add(series);

    }

}
