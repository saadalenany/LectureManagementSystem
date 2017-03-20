
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lecturemanagement.Model.StudentSentData;
import lecturemanagement.Server.OnlineStudent.control.StudentRowControl;

/**
 *
 * @author Amr
 */
public class ref {
    //------------------------ online Student 
    public static ArrayList<StudentSentData> StudentList = new ArrayList<>();
    public static ArrayList<StudentRowControl> OnlineListButton = new ArrayList<>();
    public static VBox onlineStudentVBox;
    public static Text OnlineCounterText;
    public static String OnlineNumberString = "Online student";
    public static LinkedHashMap<String, Stage> OpenedChatsMap = new LinkedHashMap<>();
    //------------------------ Main & lecture 
    public static boolean isLecRunning;
    public static Stage rootStage;
    public static String lecID;
    public static String lecName;
    //------------------------ Quiz
    public static boolean QuizRunning;
    public static String QuizID;
    //------------------------ Signup 
    public static VBox RequestSignUpBox;
    public static Rectangle SignUpCounterRect;
    public static Text SignUpCounterText;
    public static StackPane SignUpCounterPane;
    public static String username;
    //saad
    public static String userID;

}
