/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.MenuBar;
import GuiElements.activities.ActivityID;
import GuiElements.activities.GroceryList;
import GuiElements.activities.HomeScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 *
 * @author Marc
 */
public class Manager {
    
    private Stack<ActivityID> activities;
    
    public Manager(){
        this.activities = new Stack<>();
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
}
