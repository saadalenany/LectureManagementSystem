package lecturemanagement.Server.QuizMaker;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import SerClass.SavedQuizquestion;
import com.jfoenix.controls.*;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import lecturemanagement.Server.Main.Control.LogoutStudent;
import lecturemanagement.Server.Main_.MainFXMLController;
import lecturemanagement.Server.QuizMaker.control.CustomRadioButton;
import lecturemanagement.Server.QuizMaker.control.Question;
import lecturemanagement.Server.QuizMaker.control.QuizMakerTools;
import lecturemanagement.Server.QuizMaker.control.ValidationTools;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;
import static lecturemanagement.Server.Main.Control.SendQuizSlideNumberEndLecture.SendQuizSlideNumberEndLectureSocket;

/**
 * 
 * @author Saad
 */
public class QuizMakerController implements Initializable {

    @FXML
    private JFXButton add;

    @FXML
    private JFXTextField QuizName;

    @FXML
    private JFXTextField numberofchoices;

    @FXML
    private StackPane stackp;

    @FXML
    private HBox HBoxInfo;

    @FXML
    private MenuItem save;

    @FXML
    private HBox BorderBox;

    @FXML
    private HBox BottomHbox;

    @FXML
    private VBox content;

    @FXML
    private JFXButton remove;

    @FXML
    private JFXCheckBox resultPreview;

    @FXML
    private JFXTextField timestop;

    @FXML
    private Text QuizIDTxt;

    @FXML
    private Menu file;

    @FXML
    private JFXButton sendBtn;

    @FXML
    private BorderPane RootBorder;

    @FXML
    private MenuItem newone;

    @FXML
    private ScrollPane scroller;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem open;
    //-------------------------------------------------------------------------
    private ArrayList<Question> QuestionList = new ArrayList<>();
    private final String StartMarkOfQuizSending = "QUIZ_START";
    private final String EndMarkOfQuizSending = "QUIZ_END";
    private QuizMakerTools tools;
    private int questionNumber = 0;
    private final String gradeMarker = "-";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ref.QuizRunning) {
            sendBtn.setDisable(true);
        } else {
            sendBtn.setDisable(false);
        }
        tools = new QuizMakerTools();

    }

    @FXML
    public void onAdding() {
        if (numberofchoices.getText().equals("") || !numberofchoices.getText().matches("\\d+")) {
            new notifier("Error", "please Enter a valid value");
            return;
        } else if (Integer.parseInt(numberofchoices.getText()) <= 1
                || Integer.parseInt(numberofchoices.getText()) > 10) {
            new notifier("Error", "Please Enter a value greater than 1 or less than 11");
            return;
        }
        numberofchoices.setDisable(true);
        questionNumber++;
        Question question = new Question(Integer.parseInt(numberofchoices.getText()));
        content.getChildren().add(question);
        QuestionList.add(question);
    }

    @FXML
    public void onRemoving(ActionEvent event) {
        if (questionNumber > 0) {
            content.getChildren().remove(questionNumber - 1);
            QuestionList.remove(questionNumber - 1);
        }
        questionNumber--;
        if (questionNumber < 0) {
            content.getChildren().clear();
            QuestionList.clear();
            questionNumber = 0;
        }
    }

    @FXML
    void ClickPaneAction(MouseEvent event) {
        refreshAnswers();
    }

    @FXML
    void ClosePaneAction(MouseEvent event) {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onNew(ActionEvent event) {
        ClearData();
    }

    private void ClearData() {
        QuizName.setText("");
        numberofchoices.setDisable(false);
        numberofchoices.setEditable(true);
        numberofchoices.setText("");
        content.getChildren().clear();
        QuestionList.clear();
        questionNumber = 0;
    }

    @FXML
    public void onOpening() {
        if (ref.QuizRunning) {
            sendBtn.setDisable(true);
        } else {
            sendBtn.setDisable(false);
        }
        ClearData();
        FileInputStream filein = null;
        try {
            FileChooser filechooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", new String[]{"*.txt"});
            filechooser.getExtensionFilters().add(extFilter);
            File openedFile = filechooser.showOpenDialog(new Stage());
            if (openedFile == null) {
                System.out.println("no file selected!");
                return;
            }
            filein = new FileInputStream(openedFile);
            ObjectInputStream ois = new ObjectInputStream(filein);
            questionNumber = 0;
            SavedQuizquestion sqq;
            numberofchoices.setDisable(true);
            try {
                while ((sqq = (SavedQuizquestion) ois.readObject()) != null) {
                    numberofchoices.setText(sqq.getChoicesData().size() + "");
                    Question question = new Question(sqq.getChoicesData().size());
                    question.setRightAnsField(sqq.getRightans());
                    question.setQuestionDataField(sqq.getQuestion());
                    question.setRadioChoices(sqq.getChoicesData());
                    questionNumber++;
                    content.getChildren().add(question);
                    QuestionList.add(question);
                    QuizName.setText(sqq.getQuizName());
                }
            } catch (EOFException | SocketTimeoutException | ClassNotFoundException | FileNotFoundException exc) {
                exc.getMessage();
            } catch (IOException exc) {
                exc.getMessage();
            }
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            try {
                filein.close();
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
        new notifier("info", "Openned...");
    }

    @FXML
    void MouseEnteredBtnAction(MouseEvent event) {
        Glow aff = new Glow();
        aff.setLevel(0.5);
        JFXButton btn = (JFXButton) event.getSource();
        btn.setEffect(aff);
    }

    @FXML
    void MouseExitBtnAction(MouseEvent event) {
        Glow aff = new Glow();
        aff.setLevel(0);
        JFXButton btn = (JFXButton) event.getSource();
        btn.setEffect(aff);
    }

    @FXML
    public void onSaving() {
        if (QuizName.getText().trim().equals("")) {
            new notifier("error", "check Quiz name field");
            return;
        }
        if (!new ValidationTools().isQuestionValid(QuestionList)) {
            new notifier("error", "check red fields ");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File Savefile = fileChooser.showSaveDialog(new Stage());

        if (Savefile == null) {
            return;
        }

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(Savefile);
            oos = new ObjectOutputStream(fout);

            for (int i = 0; i < QuestionList.size(); i++) {
                if (QuestionList.get(i).getQuestionDataField().getText().equals("")) {
                    continue;
                }
                String rightAns = QuestionList.get(i).getRightAnsField().getText();
                String QuestionData = QuestionList.get(i).getQuestionDataField().getText();
                ArrayList<String> data = new ArrayList<>();
                for (int j = 0; j < QuestionList.get(i).getRadioChoices().size(); j++) {
                    String choice = QuestionList.get(i).getRadioChoices().get(j).getText();
                    data.add(choice);
                }
                SavedQuizquestion sqq = new SavedQuizquestion(QuestionData, data, rightAns);
                sqq.setQuizName(QuizName.getText());
                oos.writeObject(sqq);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(QuizMakerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        new notifier("info", "Saved..");
    }

    //saad task
    @FXML
    public void onSending() {
        //---------------- validation
        if (!TimeIsValid()) {
            new notifier("error", "check time field");
            return;
        } else if (QuizName.getText().trim().equals("")) {
            new notifier("error", "check Quiz name field");
            return;
        }

        if (!new ValidationTools().isQuestionValid(QuestionList)) {
            new notifier("error", "check red fields ");
            return;
        }
        refreshAnswers(); // replace TextField to 
        //-----------------------------------------------

        StorageManager sm = new StorageManager();
        ArrayList<String> quizvalues = new ArrayList();
        ArrayList<String> quizquestionvalues = new ArrayList();
        String docid = new StorageManager().getId("user", ref.username);

//quiz table has only 2 cols ==> quiz_id "Auto Increment" : lecture_id

//        quizvalues.add(QuizName.getText());
//        quizvalues.add(docid);

        ResultSet rs2 = sm.performQuery("SELECT `lecture_id` FROM `lecture` WHERE `doctor_id` = " + docid);
        try {
            while (rs2.next()) {
                System.out.println(rs2.getString(1));
                ref.lecID = rs2.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        quizvalues.add(ref.lecID);

        sm.insertInto("quiz", new String[]{"lecture_id"}, quizvalues);
        ref.QuizID = new StorageManager().getId("quiz");
        // send start quiz Marker
        SendStartOfQuizMarker();
        //----------------------------
        SendTimeAndShowResult();
        for (int i = 0; i < QuestionList.size(); i++) {
            String questionData = QuestionList.get(i).getQuestionDataField().getText();
            String RightAns = QuestionList.get(i).getRightAnsField().getText();
            if (questionData.trim().equals("")) {
                continue;
            }
            ArrayList<String> data = new ArrayList<>();
            ArrayList<CustomRadioButton> choicesList = QuestionList.get(i).getRadioChoices();
            String choicesdata = "";
            for (int j = 0; j < choicesList.size(); j++) {
                data.add(choicesList.get(j).getText());
                choicesdata += choicesList.get(j).getText() + ",";
            }
            SavedQuizquestion sqq = new SavedQuizquestion(questionData, data, RightAns);
            //-------------------------
            // send Object
            SendQuizToStudent(sqq);
            //-----------------------
            quizquestionvalues.clear();
            //table quizquestion values
            quizquestionvalues.add(choicesList.size() + "");
            quizquestionvalues.add(questionData);
            quizquestionvalues.add(choicesdata);
            quizquestionvalues.add(RightAns);
            quizquestionvalues.add(ref.QuizID);
            sm.insertInto("quizquestion", new String[]{"numberofchoices", "question_data", "choice_data", "doctor_ans", "quiz_id"}, quizquestionvalues);
            System.out.println(sqq.toString());

        }
        // send end quiz Marker
        SendQuizToStudent(new SavedQuizquestion(EndMarkOfQuizSending, null, EndMarkOfQuizSending));
        ref.QuizRunning = true;
        sendBtn.setDisable(true);
        GetQuizResult();

    }

    private void refreshAnswers() {
        // Transform TextField to radio button  
        try {
            for (int i = 0; i < QuestionList.size(); i++) {
                ArrayList<CustomRadioButton> Choices = QuestionList.get(i).getRadioChoices();
                for (int j = 0; j < Choices.size(); j++) {
                    Choices.get(j).transformTextFieldToRadio();
                }
            }
        } catch (Exception e) {
        }
    }

    private void GetQuizResult() {
        Thread th = new Thread(() -> {
            for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
                // get quiz result
                try {
                    DataInputStream in = new DataInputStream(SendQuizSlideNumberEndLectureSocket.get(i).getInputStream());
                    String StudentGrade = in.readUTF();
                    if (StudentGrade.contains(gradeMarker)) {
                        String split[] = DecapsulationReceived(StudentGrade);
                        String username = split[0];
                        String grade = split[1];
                        SaveGradeToFile(username, grade);
                        System.out.println(username + " ---- " + grade);
                        //-----------------------------------------
                        tools.getQuizDurationController().newSubmitedStudent();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        th.start();
    }

    //saad task
    private void SaveGradeToFile(String username, String grade) {

        String studentid = "0";
        int quizid = 0;
        int mark =(int) ((Double.parseDouble(grade) / QuestionList.size()) * 10);

        StorageManager st = new StorageManager();
        ResultSet rs = st.performQuery("SELECT `user_id` FROM `user` WHERE `user_name` = '" + username + "'");

        try {
            while (rs.next()) {
                System.out.println(rs.getString(1));
                studentid = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("sql exception");
        }

        StorageManager st2 = new StorageManager();
        ResultSet rs2 = st2.performQuery("SELECT `quiz_id` FROM `quiz` WHERE `lecture_id` = '" + ref.lecID+"'");

        try {
            while (rs2.next()) {
                System.out.println(rs2.getInt(1));
                quizid = rs2.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizMakerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        StorageManager st3 = new StorageManager();

        String[] fields = {"student_id", "quiz_id", "student_mark"};

        ArrayList DataFields = new ArrayList();
        DataFields.add(studentid);
        DataFields.add(String.valueOf(quizid));
        DataFields.add(String.valueOf(mark));

        st3.insertInto("student_marks", fields, DataFields);

//        File dir = new File("studentMarks");
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
//        System.out.println("-----< " + ref.QuizID);
//        try (FileWriter fw = new FileWriter("studentMarks/" + ref.QuizID + ".txt", true);
//                BufferedWriter bw = new BufferedWriter(fw);
//                PrintWriter out = new PrintWriter(bw)) {
//            out.println(username + " " + grade);
//            //more code
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

    private String[] DecapsulationReceived(String rec) {
        String[] split = rec.split("-"); //  -----------> split[0] for username  , split[1] for password  
        return split;
    }

    private void SendQuizToStudent(SavedQuizquestion sqq) {
        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(SendQuizSlideNumberEndLectureSocket.get(i).getOutputStream());
                out.writeObject(sqq);
            } catch (IOException ex) {

            }
        }
        if (sqq.getQuestion().equals(EndMarkOfQuizSending)) {
            new notifier("info", "Quiz Sended ");
            // load Quiz Duration
            //-------------------------------------
            tools.LoadQuizDurationStage(timestop.getText());
        }
    }

    private void SendStartOfQuizMarker() {
        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            try {
                DataOutputStream out = new DataOutputStream(SendQuizSlideNumberEndLectureSocket.get(i).getOutputStream());
                out.writeUTF(StartMarkOfQuizSending);
            } catch (SocketException e) {
                // from start only because it first point 
                new LogoutStudent().removeLogoutStudent(SendQuizSlideNumberEndLectureSocket.get(i));
                //-----------------------------
            } catch (IOException ex) {

            }
        }
    }

    private void SendTimeAndShowResult() {

        for (int i = 0; i < SendQuizSlideNumberEndLectureSocket.size(); i++) {
            try {
                DataOutputStream out = new DataOutputStream(SendQuizSlideNumberEndLectureSocket.get(i).getOutputStream());
                int state;
                if (resultPreview.isSelected()) {
                    state = 1;
                } else {
                    state = 0;
                }
                out.writeUTF(timestop.getText() + "-" + state);
            } catch (IOException ex) {
            }
        }

    }

    private boolean TimeIsValid() {
        if (!timestop.getText().matches("\\d+")) {
            return false;
        }
        return true;
    }

    public HBox getBorderBox() {
        return BorderBox;
    }

}
