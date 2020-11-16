 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;
//import connect.konek;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class konek {

    private static Object con;
    Connection conn;
    Statement st;
    ResultSet rs;
    private static Connection koneksi;
    public static Connection getKoneksi() {
        try {
            String url = "jdbc:mysql://localhost:3306/jualbakso";
            String user = "root";
            String pass = "";

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi Berhasil");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("koneksi gagal");
        }
        return koneksi;
    }
}
