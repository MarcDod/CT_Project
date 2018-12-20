/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.database.Connector;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc
 */
public class LogInManager {
    private static boolean valid;
    private Connector database;
    
    public LogInManager(Connector database){
        valid = false;
        this.database = database;
    }
    
    public void checkUserData(String password, String user){
        try {
            database = new Connector();
            valid = database.getPassword(user).equals(password);
        }catch (SQLException ex){
            System.err.println("MENSCH");
        }
    }
    
    public boolean isValid(){
        return valid;
    }
}
