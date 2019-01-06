/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc
 */
public class DB_Connection {
    private String url;
    
    private String user;
    private String password;
    
    private Connection con;
    
    protected DB_Connection() throws SQLException{
        this.url = "jdbc:mysql://localhost:3306/mydb";
        this.user = "root";
        this.password = "";
        this.con = null;
        this.buildConnection();
    }
    
    private void buildConnection() throws SQLException{
        
        con = (Connection) DriverManager.getConnection(url, user, password);
        
    }
    
    protected void deleteConnection() throws SQLException{
        con.close();
    }   
    
    protected ResultSet sendSqlStatement(String sqlQuery) throws SQLException{
        Statement stmt = this.con.createStatement();
        return stmt.executeQuery(sqlQuery);
    }
    
    protected boolean ping(int timeout) throws IllegalArgumentException{
        try {
            return this.con.isValid(timeout);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Timeout muss größer als 0 sein");
        }
    }

    protected void reconnect() throws SQLException{
        buildConnection();
    }
    
    protected void executeUpdate(String statement) throws SQLException{
        Statement stmt = this.con.createStatement();
        stmt.executeUpdate(statement);
    }
}
