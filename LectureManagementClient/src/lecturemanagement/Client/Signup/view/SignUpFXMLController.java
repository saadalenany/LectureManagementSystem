package lecturemanagement.Client.Signup.view;

import SerClass.StudentTransfer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lecturemanagement.Client.Signup.control.SignUpSocket;
import lecturemanagement.Utility.notifier;

/**
 *
 * @author Amr
 */
public class SignUpFXMLController implements Initializable {

    @FXML
    private TextField phoneTxt;

    @FXML
    private ComboBox<String> genderCombo;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField repasswordTxt;

    @FXML
    private ComboBox<String> departmentCombo;

    @FXML
    private TextField emailTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField academicIdTxt;

    @FXML
    private ComboBox<String> academicYearCombo;
//----------------------------------------------------
    private StudentTransfer student;

    @FXML
    void SignUpAction(ActionEvent event) {
        if (!validation()) {
            return;
        }
        new notifier("info", " Request sent wait for response");
        SignUpSocket signUp = new SignUpSocket(student, phoneTxt.getScene());
        signUp.SendSignUpInfo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departmentCombo.getItems().addAll("public", "CS", "IS", "OR", "IT");
        genderCombo.getItems().addAll("Male", "Female");
        academicYearCombo.getItems().addAll("1", "2", "3", "4");
    }

    private boolean validation() {
        if (!phoneTxt.getText().matches("\\d+")) {
            new notifier("error", " check the phone field ");
            return false;
        }
        if (genderCombo.getValue() == null || departmentCombo.getValue() == null || academicYearCombo.getValue() == null) {
            new notifier("error", " choose value from boxs ");
            return false;
        }
        if (usernameTxt.getText().contains(" ") || usernameTxt.getText().contains("-") || usernameTxt.getText().length() < 3) {
            new notifier("error", " check the username field maybe has - or space or less than 3 word ");
            return false;
        }
        if (!isValidEmailAddress(emailTxt.getText())) {
            new notifier("error", " check the email field ");
            return false;
        }
        if (!academicIdTxt.getText().matches("\\d+")) {
            new notifier("error", " check the academic id field ");
            return false;
        }

        if (!(passwordTxt.getText().equals(repasswordTxt.getText())) || passwordTxt.getText().contains(" ")
                || passwordTxt.getText().length() < 3) {

            new notifier("error", " check the password field ");
            return false;
        }
        student = new StudentTransfer();
        student.setAcademic_id(academicIdTxt.getText());
        student.setAcademic_year(Integer.parseInt(academicYearCombo.getValue()));
        student.setUser_department(departmentCombo.getValue());
        student.setUser_email(emailTxt.getText());
        student.setUser_gender(genderCombo.getValue());
        student.setUser_name(usernameTxt.getText());
        student.setUser_password(passwordTxt.getText());
        student.setUser_phone(phoneTxt.getText());
        return true;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
