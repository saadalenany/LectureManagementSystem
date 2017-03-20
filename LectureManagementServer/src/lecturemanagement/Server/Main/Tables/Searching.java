package lecturemanagement.Server.Main.Tables;

import lecturemanagement.Model.user;
import lecturemanagement.Model.student;
import lecturemanagement.Model.quizquestion;
import lecturemanagement.Model.question;
import lecturemanagement.Model.slides;
import lecturemanagement.Model.quiz;
import lecturemanagement.Model.doctor;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Searching {

    public void searchInUser(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<user>() {
                @Override
                public boolean test(user employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (str.equalsIgnoreCase("user_id")) {
                        if (String.valueOf(employee.getUser_id()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches id.
                        }
                    } else if (str.equalsIgnoreCase("user_name")) {
                        if (employee.getUser_name().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches name.
                        }
                    } else if (str.equalsIgnoreCase("user_email")) {
                        if (employee.getUser_email().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches email.
                        }
                    } else if (str.equalsIgnoreCase("user_phone")) {
                        if (employee.getUser_phone().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches phone.
                        }
                    } else if (str.equalsIgnoreCase("user_status")) {
                        if (String.valueOf(employee.getUser_status()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches age.
                        }
                    }
                    return false;
                }
            });
        });
        SortedList<user> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }

    public void searchInDoctor(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<doctor>() {
                @Override
                public boolean test(doctor employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (str.equalsIgnoreCase("doctor_course")) {
                        if (employee.getDoctor_course().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches course.
                        }
                    } else if (str.equalsIgnoreCase("numberofquizes")) {
                        if (String.valueOf(employee.getNumberofquizes()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches noq.
                        }
                    }
                    return false;
                }
            });
        });
        SortedList<doctor> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }

    public void searchInStudent(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<student>() {
                @Override
                public boolean test(student employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return String.valueOf(employee.getAcademic_year()).toLowerCase().contains(lowerCaseFilter);
                }
            });
        });
        SortedList<student> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }

    public void searchInSlides(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<slides>() {
                @Override
                public boolean test(slides employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (str.equalsIgnoreCase("slide_id")) {
                        if (String.valueOf(employee.getSlide_id()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches id.
                        }
                    } else if (str.equalsIgnoreCase("slide_name")) {
                        if (employee.getSlide_name().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches name.
                        }
                    } else if (str.equalsIgnoreCase("slide_path")) {
                        if (employee.getSlide_path().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches path.
                        }
                    } else if (str.equalsIgnoreCase("slide_course")) {
                        if (employee.getSlide_course().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches course.
                        }
                    } else if (str.equalsIgnoreCase("numberofwatchers")) {
                        if (String.valueOf(employee.getNumberofwatechers()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches noc.
                        }
                    }
                    return false;
                }
            });
        });
        SortedList<slides> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }

    public void searchInQuiz(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<quiz>() {
                @Override
                public boolean test(quiz employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (str.equalsIgnoreCase("quiz_id")) {
                        if (String.valueOf(employee.getQuiz_id()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches id.
                        }
                    } else if (str.equalsIgnoreCase("quiz_name")) {
                        if (employee.getQuiz_name().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches name.
                        }
                    } else if (str.equalsIgnoreCase("quiz_course")) {
                        if (employee.getQuiz_course().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches course.
                        }
                    }
                    return false;
                }
            });
        });
        SortedList<quiz> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }

    public void searchInQuizQuestion(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<quizquestion>() {
                @Override
                public boolean test(quizquestion employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (str.equalsIgnoreCase("quizquestion_id")) {
                        if (String.valueOf(employee.getQuizquestion_id()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches id.
                        }
                    } else if (str.equalsIgnoreCase("question_data")) {
                        if (employee.getQuestion_data().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches data.
                        }
                    } else if (str.equalsIgnoreCase("choice_data")) {
                        if (employee.getChoicedata().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches data.
                        }
                    } else if (str.equalsIgnoreCase("doctor_ans")) {
                        if (employee.getDoctor_ans().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches doctor_ans.
                        }
                    }
                    return false;
                }
            });
        });
        SortedList<quizquestion> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }

    public void searchInQuestion(TextField tf, FilteredList filtered, TableView table) {
        String str = tf.getPromptText();

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtered.setPredicate(new Predicate<question>() {
                @Override
                public boolean test(question employee) {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (str.equalsIgnoreCase("question_id")) {
                        if (String.valueOf(employee.getQuestion_id()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches id.
                        }
                    } else if (str.equalsIgnoreCase("question_data")) {
                        if (employee.getQuestion_data().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches data.
                        }
                    } else if (str.equalsIgnoreCase("questionownerid")) {
                        if (String.valueOf(employee.getQuestion_ownerid()).toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches owner_id.
                        }
                    } else if (str.equalsIgnoreCase("reply")) {
                        if (employee.getReply().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches reply.
                        }
                    }
                    return false;
                }
            });
        });
        SortedList<question> sortedlist = new SortedList<>(filtered);
        sortedlist.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedlist);
    }
}
