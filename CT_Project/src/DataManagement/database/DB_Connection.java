/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marc
 */
public class DB_Connection {
    private String url;
    
    private String user;
    private String password;
    
    private Connection con;
    
    public DB_Connection() throws SQLException{
        this.url = "jdbc:mysql://localhost:3306/mydb";
        this.user = "root";
        this.password = "";
        this.con = null;
        this.buildConnection();
    }
    
    private void buildConnection() throws SQLException{
        
        con = (Connection) DriverManager.getConnection(url, user, password);
        
    }
    
    public void deleteConnection() throws SQLException{
        con.close();
    }        
}
