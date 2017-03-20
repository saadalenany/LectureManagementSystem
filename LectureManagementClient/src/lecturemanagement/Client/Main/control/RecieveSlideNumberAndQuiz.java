/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Client.Main.control;

import SerClass.SavedQuizquestion;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import lecturemanagement.Client.Login.Control.LoginSocket;
import lecturemanagement.Client.Main.SlidePane.SlidePaneFXMLController;
import static lecturemanagement.Client.Main.SlidePane.fullScreenMode.LectureViewFullScreen;
import lecturemanagement.Client.Quiz.view.QuizFXMLController;
import static lecturemanagement.DataTransferProtocol.ServerAddress;
import lecturemanagement.Utility.LoadFXML;
import lecturemanagement.ref;
import static lecturemanagement.DataTransferProtocol.QuizSlideNumberPort;

/**
 *
 * @author Amr
 */
public class RecieveSlideNumberAndQuiz implements Runnable {

    public static Socket SlideNumberAndQuizSocket;
    private DataInputStream input;
    private ImageView LectureView;
    private final String StartMarkOfQuizSending = "QUIZ_START";
    private final String EndMarkOfQuizSending = "QUIZ_END";
    private String QuizTerminator_markSending = "QUIZ_TERMINATE";
    private final String path = "tempFile/";
    private final String QuizPathFXML = "/lecturemanagement/Client/Quiz/view/QuizFXML.fxml";
    private QuizFXMLController control;
    private ArrayList<SavedQuizquestion> QuizQuestionList;

    public RecieveSlideNumberAndQuiz(ImageView LectureView) {
        this.LectureView = LectureView;
    }

    public void SendLoginInfo() {
        QuizQuestionList = new ArrayList<>();
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        try {
            SlideNumberAndQuizSocket = new Socket(ServerAddress, QuizSlideNumberPort);
            while (true) {
                input = new DataInputStream(SlideNumberAndQuizSocket.getInputStream());
                String Str = input.readUTF();
                String QuizTerminatorAr[] = DecapsulationReceived(Str);
                if (StartMarkOfQuizSending.equals(Str)) {
                    ref.QuizRunning = true;
                    RecieveQuiz();
                } else if (QuizTerminator_markSending.equals(QuizTerminatorAr[0])) {
                    String status = QuizTerminatorAr[1];
                    control.QuizTerminatorFromDoctor(status);
                }

                if (!ref.QuizRunning) {
                    MoveToSlide(Str);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecieveSlideNumberAndQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MoveToSlide(String number) {
        ChangeImage(new File(path + number + ".png"), LectureView);
        if (LectureViewFullScreen != null) {
            ChangeImage(new File(path + number + ".png"), LectureViewFullScreen);
        }
    }

    public void ChangeImage(File file, ImageView LectureView) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(SlidePaneFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        LectureView.setImage(image);
    }

    public void RecieveQuiz() throws IOException, ClassNotFoundException {
        String Time, AllowToShowResult;
        QuizQuestionList.clear();
        input = new DataInputStream(SlideNumberAndQuizSocket.getInputStream());
        String split[] = DecapsulationReceived(input.readUTF());
        Time = split[0];
        AllowToShowResult = split[1];

        while (true) {
            ObjectInputStream inObject = new ObjectInputStream(SlideNumberAndQuizSocket.getInputStream());
            SavedQuizquestion question = (SavedQuizquestion) inObject.readObject();
            if (question.getQuestion().equals(EndMarkOfQuizSending)) {
                break;
            }
            QuizQuestionList.add(question);
        }

        Platform.runLater(() -> {
            control = (QuizFXMLController) new LoadFXML().LoadFXMLSetCenter(ref.RootPane, QuizPathFXML);
            control.setQuiz(QuizQuestionList);
            control.setTime(Time);
            control.setAllowToShowResult(Integer.parseInt(AllowToShowResult));

        });

    }

    private String[] DecapsulationReceived(String rec) {
        String[] split = rec.split("-"); //  -----------> split[0] for Time  , split[1] for Allowing To Show Result        
        return split;                    // split[0] QuizMarkerTirminator  , if he want get result or not 
    }
}
