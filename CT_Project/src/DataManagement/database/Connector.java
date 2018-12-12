/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.database;

import java.sql.SQLException;

/**
 *
 * @author Marc
 */
public class Connector {
    private DB_Connection con;
    
    public Connector() throws SQLException{
        con = new DB_Connection();
    }
    
    public void sentSQLStatement(String statement){
        
        
    }
}
