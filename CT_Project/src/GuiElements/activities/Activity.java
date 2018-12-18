/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.MenuBar;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public abstract class Activity extends JPanel{
    private ActivityID activityID;
    
    public Activity(ActivityID activityID, Color background){
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
        this.setBackground(background);
    }
    
    public Activity(ActivityID activityID, int height, Color background){
        this.activityID = activityID;
        
        Dimension d = new Dimension(Gui.SCREEN_WIDTH,height);
        this.setPreferredSize(d);
        this.setSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        this.setBounds(0, 0, Gui.SCREEN_WIDTH, d.height);
        this.setLocation(0, 0);
        this.setLayout(null);
        this.setFocusable(true);
        this.setBackground(background);
    }
        
    public ActivityID getActivityID(){
        return this.activityID;
    }
    
}
