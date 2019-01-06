/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Account;
import DataManagement.database.Connector;
import java.sql.SQLException;

/**
 *
 * @author Marc
 */
public class LogInManager extends ActivityManager{
    private Connector database;
    
    private Account user;
    
    public LogInManager(Connector database){
        this.database = database;
        this.user = null;
    }
    
    public void checkUserData(String password, String user) throws SQLException, IllegalAccessException{
        database = new Connector();
        if(database.getPassword(user).equals(password)){
            this.user = database.getAccount(user);
        }else throw new IllegalAccessException();
    }
    
    public Account getUser(){
        return this.user;
    }

    @Override
    public String getTitle() {
        return "";
    }
}
