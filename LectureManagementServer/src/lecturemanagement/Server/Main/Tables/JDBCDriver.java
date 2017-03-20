package lecturemanagement.Server.Main.Tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDriver {

    private Connection con ;
    public Connection getConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/lecturedb","root","");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

}
