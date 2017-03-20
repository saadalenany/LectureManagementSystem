/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturemanagement.Server.Main.TakeAbsence;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lecturemanagement.Server.Main.view.MainFXMLController;
import lecturemanagement.StorageManager;
import lecturemanagement.Utillity.notifier;
import lecturemanagement.ref;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class TakeAbsenceFXMLController implements Initializable {

    ArrayList ids;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void takeAbsence() {
        StorageManager st = new StorageManager();

        ids = new ArrayList();

        String[] fields = {"student_id","lecture_id"};

        for (int i = 0; i < ref.StudentList.size(); i++) {
            ResultSet rs = st.performQuery("SELECT `user_id` FROM `user` WHERE `user_name`= '" + ref.StudentList.get(i).getUsername() + "'");
            try {
                while (rs.next()) {
                    ids.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ArrayList DataFields = new ArrayList();

            DataFields.add(ids.get(i));
            DataFields.add(ref.lecID);

            st.insertInto("absence", fields, DataFields);
        }
        new notifier("info", "Absence taken");
    }

}
