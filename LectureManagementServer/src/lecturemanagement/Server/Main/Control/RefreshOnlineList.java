/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.Control;

import java.net.Socket;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import lecturemanagement.Server.OnlineStudent.control.StudentRowControl;
import lecturemanagement.Model.StudentSentData;
import lecturemanagement.Server.OnlineStudent.control.ChatListenerSocket;
import lecturemanagement.ref;

/**
 *
 * @author Amr
 */
public class RefreshOnlineList {

    public static boolean isStartChatListener = false;

    public void RefershList() {
        ObservableList<Node> children = ref.onlineStudentVBox.getChildren();
        for (int i = 0; i < ref.StudentList.size(); i++) {
            int flag = 0;
            for (int j = 0; j < children.size(); j++) {
                BorderPane node = (BorderPane) ref.onlineStudentVBox.getChildren().get(j);
                Text username = (Text) node.getCenter();
                if (ref.StudentList.get(i).getUsername().equals(username.getText())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                final StudentSentData Student = ref.StudentList.get(i);
                System.out.println(Student.getUsername());
                Platform.runLater(() -> { // add to graphicList 
                    StudentRowControl row = new StudentRowControl(Student);
                    BorderPane StudentRow = row.getRowPane();
                    ref.OnlineListButton.add(row);
                    ref.onlineStudentVBox.getChildren().add(StudentRow);
                    // refresh online counter 
                    ref.OnlineCounterText.setText(ref.OnlineNumberString + " (" + ref.onlineStudentVBox.getChildren().size() + ")");
                    // this opened once 
                    if (!isStartChatListener) {
                        new ChatListenerSocket().startListen(); // start listen from student 
                        isStartChatListener = true;
                    }
                });
            }
        }

    }

    public boolean remove(StudentSentData Student) {
        boolean removedList = false;
        for (int i = 0; i < ref.StudentList.size(); i++) {        // delete from ArrayList 
            if (ref.StudentList.get(i).getUsername().equals(Student.getUsername())) {
                ref.StudentList.remove(ref.StudentList.get(i));
                removedList = true;
                break;
            }
        }
        //---------------------------

        Platform.runLater(() -> {
            for (int i = 0; i < ref.onlineStudentVBox.getChildren().size(); i++) {  // delete from graphicList 
                BorderPane node = (BorderPane) ref.onlineStudentVBox.getChildren().get(i);
                Text username = (Text) node.getCenter();
                if (username.getText().equals(Student.getUsername())) {
                    ref.OnlineListButton.remove(i);
                    ref.onlineStudentVBox.getChildren().remove(node);
                    ref.OnlineCounterText.setText(ref.OnlineNumberString + " (" + ref.onlineStudentVBox.getChildren().size() + ")");
                    break;
                }
            }
        });

        return removedList;
    }

    public boolean remove(Socket Student) {
        boolean removedList = false;
        String TempUsername = "";
        for (int i = 0; i < ref.StudentList.size(); i++) {        // delete from ArrayList 
            if (ref.StudentList.get(i).getStudentIP().equals(Student.getInetAddress().toString())) {
                TempUsername = ref.StudentList.get(i).getUsername();
                ref.StudentList.remove(ref.StudentList.get(i));
                removedList = true;
                break;
            }
        }
        //---------------------------
        final String FTempUsername = TempUsername;
        Platform.runLater(() -> {
            for (int i = 0; i < ref.onlineStudentVBox.getChildren().size(); i++) {  // delete from graphicList 
                BorderPane node = (BorderPane) ref.onlineStudentVBox.getChildren().get(i);
                Text username = (Text) node.getCenter();
                if (username.getText().equals(FTempUsername)) {
                    ref.OnlineListButton.remove(i);
                    ref.onlineStudentVBox.getChildren().remove(node);
                    break;
                }
            }
        });

        return removedList;
    }

    public int add(StudentSentData Student) {
        if (ifStudentExist(Student)) {
            return 2;
        }
        ref.StudentList.add(Student); // add to ArrayList
        return 1;
    }

    private boolean ifStudentExist(StudentSentData Student) {
        for (int i = 0; i < ref.StudentList.size(); i++) {
            if (ref.StudentList.get(i).getUsername().equals(Student.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public void DeleteDuplicateStudent() {
        //------------------------------------ from box
        for (int i = 0; i < ref.onlineStudentVBox.getChildren().size(); i++) {  // delete from graphicList 
            BorderPane node1 = (BorderPane) ref.onlineStudentVBox.getChildren().get(i);
            Text username1 = (Text) node1.getCenter();
            for (int j = 0; j < ref.onlineStudentVBox.getChildren().size(); j++) {
                BorderPane node2 = (BorderPane) ref.onlineStudentVBox.getChildren().get(j);
                Text username2 = (Text) node2.getCenter();
                if (username1.getText().equals(username2.getText()) && i != j) {
                    Platform.runLater(() -> { // add to graphicList 
                        ref.onlineStudentVBox.getChildren().remove(node2);
                    });

                }
            }
        }
        //------------------------------------ from List 
        for (int i = 0; i < ref.StudentList.size(); i++) {        // delete from ArrayList 
            for (int j = 0; j < ref.StudentList.size(); j++) {
                if (ref.StudentList.get(i).getUsername().equals(ref.StudentList.get(j).getUsername()) && i != j) {
                    ref.StudentList.remove(ref.StudentList.get(j));
                }
            }
        }

    }

}
