/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
/**
 *
 * @author duyda
 */
public class Connect {
   String host = "localhost";
    String port = "1433";
    String dbName = "QLKS";
    String server = "LAPTOP-VI237932";
    
    Connection conn = null;
    
    String user = "sa";
    String password = "123456";
    public Connect() {
    }
    
    public  void connect(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://"+host+":"+port+";databaseName="+dbName;
            conn = DriverManager.getConnection(dbUrl,user,password);
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("loi: "+e.getMessage()); 
        } 
    }
    
    public ResultSet showDB(String sql)
    {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
                return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Truy vấn thất bại: " + ex,"Thong bao",1);
        }
        return null;     
    }
    
    public void updateDB(String sql){
        try {
            Statement st = conn.createStatement();  
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
