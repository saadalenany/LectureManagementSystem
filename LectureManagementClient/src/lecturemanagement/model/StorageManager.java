package Server.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class StorageManager {

    private Connection con;
    private Statement st;

    public StorageManager(String dbName, String dbUser, String dbPass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, dbUser, dbPass);
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StorageManager(String dbName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "");
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean executeStatement(String SQL) {
        try {
            con.createStatement().execute(SQL);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean CreateTable(String tableName, ArrayList<String> Fields) {
        try {
            String SQL = "CREATE TABLE " + tableName + " ( ";
            for (int i = 0; i < Fields.size(); i++) {
                SQL += Fields.get(i);
                if (i == Fields.size() - 1) {
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

    public void close() {
        try {
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
