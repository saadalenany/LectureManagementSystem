package lecturemanagement.Server.Main.Tables;

import lecturemanagement.Model.user;
import lecturemanagement.Model.student;
import lecturemanagement.Model.quizquestion;
import lecturemanagement.Model.question;
import lecturemanagement.Model.slides;
import lecturemanagement.Model.quiz;
import lecturemanagement.Model.note;
import lecturemanagement.Model.doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import lecturemanagement.StorageManager;

public class Updates {

    public void update(String tableName, String[] columnsNames, TableView table, TableColumn[] colarray, ObservableList database) {
        for (int i = 0; i < colarray.length; i++) {
            final String column = columnsNames[i];
            if (tableName.equalsIgnoreCase("user")) {
                if (column.equals("user_id")) {
                } else {
                    colarray[i].setEditable(true);
                    if (columnsNames[i].contains("_gender")) {
                        colarray[i].setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("male", "female")));
                    } else if (columnsNames[i].contains("_department")) {
                        colarray[i].setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("General", "CS", "IT", "IS", "OR")));
                    } else {
                        colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    }
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<user, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<user, String> t) {
                            ((user) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setUser_name(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update((String) t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("doctor")) {
                if (column.equals("doctor_course")) {
                    colarray[i].setEditable(true);
                    colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<doctor, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<doctor, String> t) {
                            ((doctor) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setDoctor_course(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("student")) {
                if (column.equals("academic_year")) {
                    colarray[i].setEditable(true);
                    colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<student, Long>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<student, Long> t) {
                            ((student) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setAcademic_year(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue() + "", column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("slides")) {
                if (column.equals("slide_id") || column.equals("doctor_id")) {
                } else if (column.equals("numberofwatchers")) {
                    colarray[i].setEditable(true);
                    colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<slides, Long>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<slides, Long> t) {
                            ((slides) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setNumberofwatechers(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue() + "", column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                } else {
                    colarray[i].setEditable(true);
                    if (columnsNames[i].equalsIgnoreCase("doctor_id") || columnsNames[i].contains("student_id")) {
                        colarray[i].setCellFactory(ComboBoxTableCell.forTableColumn(new StorageManager().getIds()));
                    } else {
                        colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    }
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<slides, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<slides, String> t) {
                            ((slides) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setSlide_name(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("quiz")) {
                if (column.equals("quiz_id") || column.equals("doctor_id")) {
                } else {
                    colarray[i].setEditable(true);
                    if (columnsNames[i].equalsIgnoreCase("doctor_id") || columnsNames[i].contains("student_id")) {
                        colarray[i].setCellFactory(ComboBoxTableCell.forTableColumn(new StorageManager().getIds()));
                    } else {
                        colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    }
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<quiz, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<quiz, String> t) {
                            ((quiz) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setQuiz_name(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("quizquestion")) {
                if (column.equals("quizquestion_id") || column.equals("quiz_id")) {
                } else if (column.equals("numberofchoices")) {
                    colarray[i].setEditable(true);
                    colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<quizquestion, Long>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<quizquestion, Long> t) {
                            ((quizquestion) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setNumberofchoices(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue() + "", column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                } else {
                    colarray[i].setEditable(true);
                    if (columnsNames[i].equalsIgnoreCase("doctor_id") || columnsNames[i].contains("student_id")) {
                        colarray[i].setCellFactory(ComboBoxTableCell.forTableColumn(new StorageManager().getIds()));
                    } else {
                        colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    }
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<quizquestion, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<quizquestion, String> t) {
                            ((quizquestion) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setQuestion_data(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("question")) {
                if (column.equals("question_id") || column.equals("questionownerid")) {
                } else {
                    colarray[i].setEditable(true);
                    if (columnsNames[i].equalsIgnoreCase("doctor_id") || columnsNames[i].contains("student_id")) {
                        colarray[i].setCellFactory(ComboBoxTableCell.forTableColumn(new StorageManager().getIds()));
                    } else {
                        colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    }
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<question, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<question, String> t) {
                            ((question) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setQuestion_data(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            } else if (tableName.equalsIgnoreCase("note")) {
                if (column.equals("note_data")) {
                    colarray[i].setEditable(true);
                    colarray[i].setCellFactory(TextFieldTableCell.<user>forTableColumn());
                    colarray[i].setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<note, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<note, String> t) {
                            ((note) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setNote_data(t.getNewValue());
                            int row = table.getSelectionModel().getSelectedIndex();
                            int col = (int) colarray[0].getCellObservableValue(table.getItems().get(row)).getValue();

                            new StorageManager().Update(t.getNewValue(), column, col, tableName);
                            System.out.println(t.getTablePosition().getRow());
                            System.out.println(t.getNewValue());
                        }
                    });
                }
            }
        }
    }
}
