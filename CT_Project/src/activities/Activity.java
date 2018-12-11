/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import GuiElements.MenuBar;
import ct_project.Gui;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public abstract class Activity extends JPanel{
    private ActivityID activityID;
    
    public Activity(ActivityID activityID){
        this.activityID = activityID;
        
        Dimension d = new Dimension(Gui.SCREEN_WIDTH,Gui.SCREEN_HEIGHT - 29 - MenuBar.MENUBAR_HEIGHT); // 29 = TopBar
        this.setPreferredSize(d);
        this.setSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        this.setBounds(0, 0, Gui.SCREEN_WIDTH, d.height);
        this.setLocation(0, MenuBar.MENUBAR_HEIGHT);
        this.setLayout(null);
        this.setFocusable(true);
    }
        
    public ActivityID getActivityID(){
        return this.activityID;
    }
}
