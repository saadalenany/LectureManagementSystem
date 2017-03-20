package lecturemanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import lecturemanagement.Utillity.FxDialogs;

public class StorageManager {

    private Connection con;
    private Statement st;

    private ObservableList users_ids;

    public StorageManager(String dbName, String dbUser, String dbPass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, dbUser, dbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            FxDialogs.showInformation("error", ex.getMessage());
            System.exit(0);
        }
    }

    public StorageManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lecturedb", "root", "");
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            FxDialogs.showInformation("error", ex.getMessage());
            System.exit(0);
        }
    }

    public ResultSet SelectFrom(String tableName) {
        ResultSet result = null;
        try {
            st = (Statement) con.createStatement();
            String SQL = "select * from `" + tableName + "`";
            result = st.executeQuery(SQL);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return result;

    }

    public ArrayList<String> getTablesNames() {
        ArrayList<String> tables = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery("show tables");
            rs.beforeFirst();
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            rs.beforeFirst();   //save tables names into array
            return tables;
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean insertInto(String tableName, String[] fields, ArrayList<String> DataFields) {
        String SQL = "INSERT INTO `" + tableName + "` (";
        for (int i = 0; i < fields.length; i++) {
            SQL += fields[i];
            if (i != fields.length - 1) {
                SQL += ",";
            }
        }
        SQL += ") VALUES (";

        for (int i = 0; i < DataFields.size(); i++) {
            SQL += "'" + DataFields.get(i) + "'";
            if (i != fields.length - 1) {
                SQL += ",";
            }
        }
        SQL += ");";

        System.out.println(SQL);
        try {
            st.execute(SQL);
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public int getLastIntValue(String tableName, String Column) {
        int Last = 0;
        try {
            String SQL = "select * from `" + tableName + "`";
            ResultSet set = st.executeQuery(SQL);
            while (set.next()) {
                Last = set.getInt(Column);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Last;
    }

    public boolean executeStatement(String SQL) {
        try {
            con.createStatement().execute(SQL);
            System.out.println(SQL);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean CreateTable(String tableName, String... Fields) {
        try {
            String SQL = "CREATE TABLE " + tableName + " ( ";
            for (int i = 0; i < Fields.length; i++) {
                SQL += Fields[i];
                if (i == Fields.length - 1) {
                    break;
                }
                SQL += " , ";
            }
            SQL += ");";
            System.out.println(SQL);
            con.createStatement().execute(SQL);
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public ResultSet performQuery(String sql) {
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            System.out.println("sql exception");
        }
        return null;
    }

    public ResultSet Search(String tableName, String key, String Column) {
        try {
            String SQL = "select * from `" + tableName + "` where " + Column + " = " + key;
            st = (Statement) con.createStatement();
            ResultSet result = st.executeQuery(SQL);
            return result;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }
        return null;
    }

    public String getCourse(String value) {
        try {
            String SQL = "select `doctor_id` , `doctor_course` from `doctor` ";
            st = con.createStatement();
            ResultSet result = st.executeQuery(SQL);
            while (result.next()) {
                System.out.println(result.getString(1));
                if (result.getString(1).equalsIgnoreCase(value)) {
                    System.out.println(result.getString(2));
                    return result.getString(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }

    public String getId(String tablename, String value) {
        try {
            String SQL = "select `" + tablename + "_id` , " + tablename + "_name from `" + tablename + "` ";
            st = con.createStatement();
            ResultSet result = st.executeQuery(SQL);
            while (result.next()) {
                if (result.getString(2).equalsIgnoreCase(value)) {
                    return result.getString(1);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }

    //saad task
    public String getId(String tablename) {
        String id = null;
        try {
            String SQL = "select `" + tablename + "_id`  from `" + tablename + "` ";
            st = con.createStatement();
            ResultSet result = st.executeQuery(SQL);
            while (result.next()) {
                id = result.getInt(1) + "";
            }
        } catch (SQLException ex) {
            System.out.println("Exception in " + tablename + " ID Retrieval");
        }
        return id;
    }

    public boolean DeleteRow(String TableName, String column, String key) {

        String SQL = "DELETE FROM " + TableName + " WHERE " + column + "='" + key + "';";
        try {
            con.createStatement().executeUpdate(SQL);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
        return true;
    }

    public void Update(String newvalue, String colname, int condition, String tablename) {
        try {
            st.executeUpdate("UPDATE `" + tablename + "` SET `" + colname + "` = '" + newvalue + "' WHERE " + tablename + "_id = " + condition);
            System.out.println("Updated!");
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // -------------------------------------------------------------------------------------- not for all public programs
    public boolean checkDoctorLogIn(String username, String password) {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT `user_name` , `user_password` FROM `user` WHERE `user_name`='" + username + "' AND `user_password`= '" + password + "' AND `user_status`='Server'");
            int i = 0;
            while (rs.next()) {
                i++;
            }
            return i == 1;
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkStudentLogIn(String username, String password) {
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT `user_name` , `user_password` FROM `user` WHERE `user_name`='" + username + "' AND `user_password`= '" + password + "' AND `user_status`='Client'");
            int i = 0;
            while (rs.next()) {
                i++;
            }
            return i == 1;
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ObservableList getIds() {
        try {
            ResultSet rs = st.executeQuery("SELECT `user_id` FROM `user`");
            users_ids = FXCollections.observableArrayList();
            while (rs.next()) {
                users_ids.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users_ids;
    }

    public void saveStudentData(String id, String username, String password, String email, String phone_number, String gender, String department, int year) {
        try {
            st = con.createStatement();
            st.execute("INSERT INTO `user` (`user_id`, `user_name`, `user_email`, `user_password`, `user_department`, `user_gender`, `user_phone`, "
                    + "`user_status`) VALUES ('" + id + "', '" + username + "', '" + email + "', '" + password + "', '" + department + "', '" + gender + "', '" + phone_number + "', 'Client')");
            st.execute("INSERT INTO `student` (`student_id`, `academic_year`) VALUES ('" + id + "', '" + year + "')");
        } catch (SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
