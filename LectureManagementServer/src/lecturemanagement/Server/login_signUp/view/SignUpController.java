package lecturemanagement.Server.login_signUp.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import static lecturemanagement.Server.Main_.MainFXMLController.RECT_WIDTH;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.ScreenTools;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;

public class SignUpController implements Initializable {

    @FXML
    JFXTextField txtUser, txtEmail, txtPhone, txt_id, txtcourse;

    @FXML
    RadioButton maleRadio, femaleradio;

    @FXML
    JFXComboBox comboDepartemet;

    @FXML
    JFXPasswordField txtPassword, reTxtPassword;

    @FXML
    JFXButton signUp;

    private String MainPath = "/lecturemanagement/Server/Main_/MainFXML.fxml";
    private final String OnlineStudentPath = "/lecturemanagement/Server/OnlineStudent/view/OnlineFXML.fxml";
    private final String ServerSignUpPath = "/lecturemanagement/Server/Signup/SignUpFXML.fxml";
    private final String ServerMainPath_ = "/lecturemanagement/Server/Main_/MainFXML.fxml";

    private ArrayList<String> DoctorData;
    private StorageManager st;

    private ArrayList<String> academicids;
    private ArrayList<String> usernames;

    private void retrieveData() {
        academicids = new ArrayList();
        usernames = new ArrayList();

        ResultSet rs = st.performQuery("SELECT `user_id` , `user_name` FROM `user` WHERE `user_status` = 'Server'");

        try {
            while (rs.next()) {
                academicids.add(rs.getString(1));
                usernames.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println("Exception in Academic Ids , Usernames Retrieval !");
        }

    }

    @FXML
    private void SignUpAction(ActionEvent event) {
        try {
            if (!validation()) {
                return;
            }
            String[] user = new String[]{"user_id", "user_name", "user_email", "user_password", "user_department", "user_gender", "user_phone", "user_status"};

            DoctorData.add(txt_id.getText());
            DoctorData.add(txtUser.getText());
            DoctorData.add(txtEmail.getText());
            DoctorData.add(txtPassword.getText());
            DoctorData.add(comboDepartemet.getValue() + "");
            if (maleRadio.isSelected()) {
                DoctorData.add("male");

            } else {
                DoctorData.add("female");

            }
            DoctorData.add(txtPhone.getText());
            DoctorData.add("Server");

            st.insertInto("user", user, DoctorData);

            String[] doctor = new String[]{"doctor_id", "doctor_course", "numberofquizes"};
            ArrayList<String> docvals = new ArrayList<>();
            docvals.add(txt_id.getText());
            docvals.add(txtcourse.getText());
            docvals.add(0 + "");
            st.insertInto("doctor", doctor, docvals);
            Scene scene = signUp.getScene();

            LoadFXML loader = new LoadFXML();
            ref.rootStage = loader.LoadFXMLWithTransparentSrage(ServerMainPath_);
            relocateMainStage();
            Stage stage = (Stage) this.signUp.getScene().getWindow();
            stage.close();
            // --------- onlineStudent  
            LoadOnlineStudent();

           // new LoadFXML().LoadFXML(scene, MainPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validation() {
        if (!txtPhone.getText().matches("\\d+")) {
            new notifier("error", " check the phone field ");
            return false;
        }
        if (comboDepartemet.getValue() == null) {
            new notifier("error", " choose value from boxs ");
            return false;
        }
        if (txtUser.getText().contains(" ") || txtUser.getText().contains("-") || txtUser.getText().length() < 3) {
            new notifier("error", " check the username field maybe has - or space or less than 3 word ");
            return false;
        }

        if (!isValidEmailAddress(txtEmail.getText())) {
            new notifier("error", " check the email field ");
            return false;
        }
        if (!txt_id.getText().matches("\\d+")) {
            new notifier("error", " check the academic id field ");
            return false;
        }
        if (isAcadimicIdAlreadySaved()) {
            new notifier("error", " academic id is already used ");
            return false;
        }
        if (isUsernameAlreadySaved()) {
            new notifier("error", " username is already used ");
            return false;
        }
        if (!(txtPassword.getText().equals(reTxtPassword.getText())) || txtPassword.getText().contains(" ")
                || txtPassword.getText().length() < 3) {

            new notifier("error", " check the password field ");
            return false;
        }

        return true;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        st = new StorageManager();
        DoctorData = new ArrayList<>();
        comboDepartemet.setItems(FXCollections.observableArrayList(new String[]{"CS", "IT", "IS", "OR"}));
        retrieveData();
    }

    private boolean isAcadimicIdAlreadySaved() {
        // saad task
        for (int i = 0; i < academicids.size(); i++) {
            System.out.println("ID ==> " + academicids.get(i));
            if (txt_id.getText().equals(academicids.get(i))) {
                System.out.println("ID false");
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameAlreadySaved() {
        // saad task
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println("username ==> " + usernames.get(i));
            if (txtUser.getText().equals(usernames.get(i))) {
                System.out.println("username false");
                return true;
            }
        }
        return false;
    }

    private void LoadOnlineStudent() {
        ScreenTools screen = new ScreenTools();
        Stage stage = new LoadFXML().LoadFXMLRootedStage(OnlineStudentPath);
        stage.setX(screen.getScreenWidth() - stage.getWidth());
        stage.setY(screen.getScreenHeight() - stage.getHeight() - screen.getTaskBarHeight());
    }

    private void relocateMainStage() {
        ScreenTools screen = new ScreenTools();
        int X = (int) (screen.getScreenWidth() / 2 - RECT_WIDTH / 2);
        ref.rootStage.setX(X);
        ref.rootStage.setY(-20);
    }

//
////----------------------------------------------------
//    private StudentTransfer student;
//
//    @FXML
//    void SignUpAction(ActionEvent event) {
//        if (!validation()) {
//            return;
//        }
//        new notifier("info", " Request sent wait for response");
//        SignUpSocket signUp = new SignUpSocket(student, txtPhone.getScene());
//        signUp.SendSignUpInfo();
//        
//       
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        comboDepartemet.getItems().addAll("public", "CS", "IS", "OR", "IT");
//        comboyear.getItems().addAll("1", "2", "3", "4");
//    }
//
//    private boolean validation() {
//        if (!txtPhone.getText().matches("\\d+")) {
//            new notifier("error", " check the phone field ");
//            return false;
//        }
//        if (comboDepartemet.getValue() == null || comboyear.getValue() == null) {
//            new notifier("error", " choose value from boxs ");
//            return false;
//        }
//        if (txtUser.getText().contains(" ") || txtUser.getText().contains("-") || txtUser.getText().length() < 3) {
//            new notifier("error", " check the username field maybe has - or space or less than 3 word ");
//            return false;
//        }
//        if (!isValidEmailAddress(txtEmail.getText())) {
//            new notifier("error", " check the email field ");
//            return false;
//        }
//        if (!txt_id.getText().matches("\\d+")) {
//            new notifier("error", " check the academic id field ");
//            return false;
//        }
//
//        if (!(txtPassword.getText().equals(reTxtPassword.getText())) || txtPassword.getText().contains(" ")
//                || txtPassword.getText().length() < 3) {
//
//            new notifier("error", " check the password field ");
//            return false;
//        }
//        student = new StudentTransfer();
//        student.setAcademic_id(txt_id.getText());
//        student.setAcademic_year(Integer.parseInt(comboyear.getValue() + ""));
//        student.setUser_department(comboDepartemet.getValue() + "");
//        student.setUser_email(txtEmail.getText());
//        if (maleRadio.isSelected()) {
//            student.setUser_gender("male");
//
//        } else if (femaleradio.isSelected()) {
//            student.setUser_gender("female");
//
//        }
//        student.setUser_name(txtUser.getText());
//        student.setUser_password(txtPassword.getText());
//        student.setUser_phone(txtPhone.getText());
//        return true;
//    }
//
//    public boolean isValidEmailAddress(String email) {
//        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
//        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
//        java.util.regex.Matcher m = p.matcher(email);
//        return m.matches();
//    }
    /**
     * ******************** -- end of code -- *********************************
     */
    //    @FXML
//    public void onSignUpAction() {
//
//        String valid = Validate();
//        if (!valid.equals("valid")) {
//            msgError(valid);
//            return;
//
//        }
//        StudentTransfer std = new StudentTransfer();
//        std.setUser_name(txtUser.getText());
//        std.setUser_email(txtEmail.getText());
//        std.setUser_phone(txtPhone.getText());
//        std.setUser_password(txtPassword.getText());
//        std.setAcademic_year(Integer.parseInt(txtYear.getText()));
//
//        if (maleRadio.isSelected()) {
//            std.setUser_gender("Male");
//        } else if (femaleradio.isSelected()) {
//            std.setUser_gender("feMale");
//
//        }
//
//        std.setUser_department(comboDepartemet.getSelectionModel().getSelectedItem().toString());
//
//        try {
//            Socket skt = null;
//            try {
//                skt = new Socket(DataTransferProtocol.ServerAddress, DataTransferProtocol.SignUpPort);
//            } catch (Exception ex) {
//                msgError("Sorry There's no Server Opened");
//                return;
//
//            }
//            ObjectOutputStream output = new ObjectOutputStream(skt.getOutputStream());
//            DataInputStream input = new DataInputStream(skt.getInputStream());
//            output.writeObject(std);
//
//            int confirm = input.readInt();
//            if (confirm == 1) {
//                System.out.println("signed successful");
//            } else {
//                System.out.println("you didnot signed ");
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        // ObjectOutputStream output;
//
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
////        comboDepartemet
//
//        ObservableList<String> departements = FXCollections.observableArrayList();
//        departements.add("Public");
//        departements.add("CS");
//        departements.add("IS");
//        departements.add("IT");
//        ToggleGroup group = new ToggleGroup();
//        maleRadio.setToggleGroup(group);
//        femaleradio.setToggleGroup(group);
//
//        comboDepartemet.setItems(departements);
//
//    }
//
//    public void msgError(String msg) {
//        lblMessage.setText(msg);
//        lblMessage.setVisible(true);
//
//        new Timeline(new KeyFrame(Duration.seconds(5), null, new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                lblMessage.setText("");
//                lblMessage.setVisible(false);
//            }
//        })).play();
//
//    }
//
//    // this method for validate function 
//    public String Validate() {
//        if (txtUser.getText().length() < 3) {
//            return "UserName can't be less than 3 charachter";
//        } else if (!txtEmail.getText().matches("[\\w\\d]+@[\\w]+\\.com")) {
//            return "email pattern is wrong";
//        } else if (!txtPhone.getText().matches("\\d{11}")) {
//            return "phone must be 11 numbers";
//        } else if (txtPassword.getText().length() < 6) {
//            return "password can't be less than 6 charachter";
//
//        } else if (!txtYear.getText().matches("\\d*")) {
//            return "year must be integer";
//        } else if (Integer.parseInt(txtYear.getText()) < 0 || Integer.parseInt(txtYear.getText()) > 4) {
//            return "year must between 1 - 4";
//        } else if (comboDepartemet.getSelectionModel().getSelectedIndex() == -1) {
//            return "Please select your Departement";
//        }
//
//        return "valid";
//    }
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
}
