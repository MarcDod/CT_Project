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
    private Connector database;
    private Activity currentActivity;
    
    public Manager(){
        this.activities = new Stack<>();
    }
    
    public void newConnection() throws SQLException{
        this.database = new Connector();
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

    public void setCurrentActivity(Activity tempActivity){
        this.currentActivity = tempActivity;
    }

    public boolean ping(int timeout) throws SQLException{
        return database.ping(timeout);
    }

    public void reconnect() throws SQLException{
        this.database.reconnect();
    }
}
