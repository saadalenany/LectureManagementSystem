/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.OnlineStudent.control.ChatRoom;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.stage.Stage;
import lecturemanagement.Model.StudentSentData;
import static lecturemanagement.Server.OnlineStudent.view.OnlineFXMLController.MAIN_CHAT_WIDTH;
import lecturemanagement.Utillity.ScreenTools;
import static lecturemanagement.ref.OpenedChatsMap;

/**
 *
 * @author Amr
 */
public class FixChatLocation {

    private final int DiffBetweenTwoWindow = 260;
    private StudentSentData Student;
    private Stage chatStage;
    private int taskHeight;
    private int ScreenWidth;
    private int ScreenHeight;
    private double chatWidth;
    private final int gap = 15;

    public FixChatLocation(StudentSentData Student, Stage chatStage) {
        this.Student = Student;
        this.chatStage = chatStage;
        LocateStage();
    }

    private void LocateStage() {
        ScreenTools screen = new ScreenTools();
        taskHeight = screen.getTaskBarHeight();
        ScreenWidth = screen.getScreenWidth();
        ScreenHeight = screen.getScreenHeight();
        setWindowLocation();
    }

    private void setWindowLocation() {

        int numberOfChats = OpenedChatsMap.size();
        chatWidth = chatStage.getWidth();
        //---------------------------------------------------
        //    numberOfChats = calculateLocation();
        System.out.println(numberOfChats);
        double X = ScreenWidth - MAIN_CHAT_WIDTH - gap - (numberOfChats * chatWidth) - (numberOfChats * gap) - chatWidth;
        if (X <= 20) {  // if  no space for new stage 
            CreateNoSpaceChatWindow();

        } else {
            chatStage.setX(X);
            double Y = ScreenHeight - chatStage.getHeight() - taskHeight;
            chatStage.setY(Y);
        }

    }

    private void RemoveFirstWindowChat(int pos) {
        Set mapValues = OpenedChatsMap.entrySet();
        Iterator it = mapValues.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Stage> entry = (Map.Entry) it.next();
            if (pos == entry.getValue().getX()) {
                Stage stage = entry.getValue();
                OpenedChatsMap.remove(entry.getKey());
                stage.close();
                return;
            }
        }
    }

    private void CreateNoSpaceChatWindow() {
        // Calculate X for first window
        int X = (int) (ScreenWidth - MAIN_CHAT_WIDTH - gap - chatWidth);
        double Y = ScreenHeight - chatStage.getHeight() - taskHeight;
        RemoveFirstWindowChat(X);
        chatStage.setX(X);
        chatStage.setY(Y);

    }

}
