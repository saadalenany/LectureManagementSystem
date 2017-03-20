/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.Control;

import java.net.Socket;
import static lecturemanagement.Server.Login.Control.ReceiveClientLogin.OnlineSocketList;
import static lecturemanagement.Server.Main.Control.SendLeactureToStudent.LectureClientSocket;
import lecturemanagement.ref;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;

/**
 *
 * @author Amr
 */
public class LogoutStudent {

    public void removeLogoutStudent(Socket socket) {
        // from this socket from list
        for (int i = 0; i < OnlineSocketList.size(); i++) {
            if (OnlineSocketList.get(i).getInetAddress().toString().equals(socket.getInetAddress().toString())) {
                // remove from online list 
                new RefreshOnlineList().remove(OnlineSocketList.get(i));
                // remove from this list
                OnlineSocketList.remove(i);
                // refresh online counter 
                i--;
            }
        }
        for (int i = 0; i < LectureClientSocket.size(); i++) {
            if (LectureClientSocket.get(i).getInetAddress().toString().equals(socket.getInetAddress().toString())) {
                LectureClientSocket.remove(i);
                i--;
            }
        }
        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            if (SendQuizSlideNumberEndLectureSocket.get(i).getInetAddress().toString().equals(socket.getInetAddress().toString())) {
                SendQuizSlideNumberEndLectureSocket.remove(i);
                i--;
            }
        }
        ref.OnlineCounterText.setText(ref.OnlineNumberString + " (" + ref.onlineStudentVBox.getChildren().size() + ")");
    }
}
