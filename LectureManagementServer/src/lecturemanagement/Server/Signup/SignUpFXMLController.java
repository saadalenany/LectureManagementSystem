package lecturemanagement.Server.Signup;

import com.jfoenix.controls.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.LoadFXML;
import lecturemanagement.Utillity.notifier;

public class SignUpFXMLController implements Initializable {

    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXComboBox<String> gender;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField course;
    @FXML
    private JFXComboBox<String> department;
    @FXML
    private JFXPasswordField rePassword;
    @FXML
    private JFXTextField academic_id;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton signup;

    private String MainPath = "/lecturemanagement/Server/Main/view/MainFXML.fxml";
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
    private void handleSignUpAction(ActionEvent event) {
        if (!validation()) {
            return;
        }
        String[] user = new String[]{"user_id", "user_name", "user_email", "user_password", "user_department", "user_gender", "user_phone", "user_status"};

        DoctorData.add(academic_id.getText());
        DoctorData.add(username.getText());
        DoctorData.add(email.getText());
        DoctorData.add(password.getText());
        DoctorData.add(department.getValue());
        DoctorData.add(gender.getValue());
        DoctorData.add(phone.getText());
        DoctorData.add("Server");

        st.insertInto("user", user, DoctorData);

        String[] doctor = new String[]{"doctor_id", "doctor_course", "numberofquizes"};
        ArrayList<String> docvals = new ArrayList<>();
        docvals.add(academic_id.getText());
        docvals.add(course.getText());
        docvals.add(0 + "");
        st.insertInto("doctor", doctor, docvals);
        Scene scene = signup.getScene();
        new LoadFXML().LoadFXML(scene, MainPath);

    }

    private boolean validation() {
        if (!phone.getText().matches("\\d+")) {
            new notifier("error", " check the phone field ");
            return false;
        }
        if (gender.getValue() == null || department.getValue() == null) {
            new notifier("error", " choose value from boxs ");
            return false;
        }
        if (username.getText().contains(" ") || username.getText().contains("-") || username.getText().length() < 3) {
            new notifier("error", " check the username field maybe has - or space or less than 3 word ");
            return false;
        }

        if (!isValidEmailAddress(email.getText())) {
            new notifier("error", " check the email field ");
            return false;
        }
        if (!academic_id.getText().matches("\\d+")) {
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
        if (!(password.getText().equals(rePassword.getText())) || password.getText().contains(" ")
                || password.getText().length() < 3) {

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
        gender.setItems(FXCollections.observableArrayList(new String[]{"male", "female"}));
        department.setItems(FXCollections.observableArrayList(new String[]{"CS", "IT", "IS", "OR"}));
        retrieveData();
    }

    private boolean isAcadimicIdAlreadySaved() {
        // saad task
        for (int i = 0; i < academicids.size(); i++) {
            System.out.println("ID ==> "+academicids.get(i));
            if (academic_id.getText().equals(academicids.get(i))) {
                System.out.println("ID false");
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameAlreadySaved() {
        // saad task
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println("username ==> "+usernames.get(i));
            if (username.getText().equals(usernames.get(i))) {
                System.out.println("username false");
                return true;
            }
        }
        return false;
    }

}
