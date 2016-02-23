package com.example.gulzar.christuniversitybus;

/**
 * Created by Gulzar on 22-02-2016.
 */
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by H-PC on 16-Oct-15.
 */
public class ConnectionClass {
    String ip = "192.168.173.1";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "bus_app";
    String un = "sa";
    String password = "yoyogssn";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
           Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
