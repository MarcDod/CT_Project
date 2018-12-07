/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ct_project;

import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public class Activity {
    private int activityID;
    
    public Activity(int activityID){
        this.activityID = activityID;
    }
    
    public JPanel getJPanel(){
        return null;
    }
    
    public int getID(){
        return this.activityID;
    }
}
