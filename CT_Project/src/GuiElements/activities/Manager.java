/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.database.Connector;
import java.sql.SQLException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc
 */
public class Manager {
    
    private Stack<ActivityID> activities;
    private Thread ping;
    private Connector database;
    
    public Manager(){
        this.activities = new Stack<>();
        try {
            this.database = new Connector();
            ping = new Thread(new Runnable() {
                @Override
                public void run(){
                    try {
                        database.ping(60);
                    }catch (SQLException ex){
                        Logger.getLogger(Manager.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                }
            });
        }catch (SQLException ex){
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ActivityID getLastActivityID(){
        if(!this.activities.isEmpty())
            return this.activities.pop();
        return null;
    }
    
    public void addActivityID(ActivityID id){
        this.activities.push(id);
    }
    
    public boolean isEmpty(){
        return this.activities.isEmpty();
    }
    
    public void resetStack(){
        this.activities.clear();
    }
    
    public LogInManager getLogInManager(){
        return new LogInManager(this.database);
    }
}
