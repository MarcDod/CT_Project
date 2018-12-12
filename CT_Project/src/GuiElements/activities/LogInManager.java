/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.database.DB_Connection;

/**
 *
 * @author Marc
 */
public class LogInManager {
    private static boolean valid;
    
    public LogInManager(){
        valid = false;
    }
    
    public void checkUserData(String password, String user){
    }
    
    public boolean isValid(){
        return valid;
    }
}
