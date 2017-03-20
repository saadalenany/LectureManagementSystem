package lecturemanagement.Server.OnlineStudent.control;

import java.net.Socket;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lecturemanagement.Model.StudentSentData;
import lecturemanagement.Server.OnlineStudent.control.ChatRoom.FixChatLocation;
import lecturemanagement.Server.OnlineStudent.control.ChatRoom.StudentChat.StudentChatFXMLController;
import lecturemanagement.Server.OnlineStudent.control.ChatRoom.model.Message;
import lecturemanagement.Server.OnlineStudent.control.StudentRow.StudentRowController;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.ref;
import static lecturemanagement.ref.OpenedChatsMap;

/**
 *
 * @author Amr
 */
public class StudentRowControl {

    private final String MSG_BUTTON_COLOR_DEFAULT = "#3f3f3f";
    private final String MSG_BUTTON_COLOR_MESSAGE = "#0071C2";
    private final String StudentRowPath = "/lecturemanagement/Server/OnlineStudent/control/StudentRow/StudentRow.fxml";
    private final String studentChatPath = "/lecturemanagement/Server/OnlineStudent/control/ChatRoom/StudentChat/StudentChatFXML.fxml";
    //-------------------------------------------------- 
    private int isMsg;
    private BorderPane RowPane;
    private Circle MsgNotifierCircle;
    private StudentSentData Student;
    private Timeline time;
    private String message;
    private Socket StudentSocket;
    private ArrayList<Message> msgList;
    private StudentChatFXMLController ChatControl;

    public StudentRowControl(StudentSentData Student) {
        msgList = new ArrayList<>();
        this.Student = Student;
        inialSet();
        Actions();

    }

    private void inialSet() {
        LoadFXML loed = new LoadFXML();
        RowPane = (BorderPane) loed.LoadFXML(StudentRowPath);
        StudentRowController control = (StudentRowController) loed.getController();
        control.getUsernameTxt().setText(Student.getUsername());
        MsgNotifierCircle = control.getMsgNotifierCircle();
    }

    private void Actions() {
        RowPane.setOnMouseClicked(e -> {
            if (time != null) {
                seen();
            }
            if (isChatAlreadyOpened()) {
                return;
            }
            // create chat window when make click Action
            LoadFXML loader = new LoadFXML();
            Stage chatStage = loader.LoadFXMLRootedStage(studentChatPath);
            ChatControl = (StudentChatFXMLController) loader.getController();
            ChatControl.setStudentSocket(StudentSocket);
            ChatControl.setUsernameTxt(Student.getUsername());
            ChatControl.setMsgList(msgList);
            new FixChatLocation(Student, chatStage);
            //---------------------------------------------
            System.out.println(chatStage);
            ref.OpenedChatsMap.put(Student.getUsername(), chatStage); // add stage to list 
        });
    }

    private boolean isChatAlreadyOpened() {
        return ref.OpenedChatsMap.get(Student.getUsername()) != null;
    }

    public void NotifyNewMessage() {
        // update style for msg button to ensure that has style  
        time = new Timeline(new KeyFrame(Duration.seconds(0.66667), (ActionEvent event) -> {
            if (isMsg == 0) {
                MsgNotifierCircle.setFill(Color.web(MSG_BUTTON_COLOR_DEFAULT));
            } else {
                MsgNotifierCircle.setFill(Color.web(MSG_BUTTON_COLOR_MESSAGE));
            }
            isMsg = 1 - isMsg;
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
    }

    public void seen() {
        time.stop();
        MsgNotifierCircle.setFill(Color.web(MSG_BUTTON_COLOR_DEFAULT));
    }

    public StudentSentData getStudent() {
        return Student;
    }

    public void setStudent(StudentSentData Student) {
        this.Student = Student;
    }

    public String getMessage() {
        return message;
    }

    public void AddMessage(String message) {
        if (ChatControl != null) {
            ChatControl.MakeMessage(message, 's');
        }
        msgList.add(new Message(message, 's')); // message from student
        // closed window notify him else ignore notify 
        Stage window = OpenedChatsMap.get(Student.getUsername());
        if (window == null) {
            NotifyNewMessage();
        } else if (!window.isShowing()) {
            NotifyNewMessage();
        }
        this.message = message;
    }

    public Socket getStudentSocket() {
        return StudentSocket;
    }

    public void setStudentSocket(Socket StudentSocket) {
        this.StudentSocket = StudentSocket;
    }

    public BorderPane getRowPane() {
        return RowPane;
    }

}
