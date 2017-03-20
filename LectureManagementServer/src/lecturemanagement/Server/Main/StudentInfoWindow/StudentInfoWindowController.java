/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.StudentInfoWindow;

import SerClass.StudentTransfer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class StudentInfoWindowController implements Initializable {

    @FXML
    private Text acadimic_ID;

    @FXML
    private Text gender;

    @FXML
    private Text phone;

    @FXML
    private Text email;

    @FXML
    private Text username;
    @FXML
    private HBox BorderBox;
    
    
    private StudentTransfer student;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void CloseAction(ActionEvent event) {
        Stage stage = (Stage) username.getScene().getWindow();
        stage.close();
    }

    public String getAcadimic_ID() {
        return acadimic_ID.getText();
    }

    public void setAcadimic_ID(String acadimic_ID) {
        this.acadimic_ID.setText(acadimic_ID);
    }

    public String getGender() {
        return gender.getText();
    }

    public void setGender(String gender) {
        this.gender.setText(gender);
    }

    public String getPhone() {
        return phone.getText();
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public String getEmail() {
        return email.getText();
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public String getUsername() {
        return username.getText();
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public StudentTransfer getStudent() {
        return student;
    }

    public void setStudent(StudentTransfer student) {
        this.student = student;

        this.setUsername(student.getUser_name());
        this.setAcadimic_ID(student.getAcademic_id());
        this.setEmail(student.getUser_email());
        this.setGender(student.getUser_gender());
        this.setPhone(student.getUser_phone());

    }

    public HBox getBorderBox() {
        return BorderBox;
    }

    

}
