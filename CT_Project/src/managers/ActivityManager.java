/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import GuiElements.activities.ActivityID;

/**
 *
 * @author Marc
 */
public abstract class ActivityManager {
    private ActivityID activityID;
    
    public ActivityManager(){
        
    }
    
    public void setActivityID(ActivityID id){
        this.activityID = id;
    }
    
    public ActivityID getActivityID(){
        return this.activityID;
    }
    
    public abstract String getTitle();
}
