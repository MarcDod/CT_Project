/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

/**
 *
 * @author Marc
 */
public class LoginScreen extends Activity{
    
    private Button loginButton;
    
    public LoginScreen(ActionListener loginButton) {
        super(ActivityID.LOGIN_SCREEN);
        
        this.loginButton = new Button(Gui.SCREEN_WIDTH, 50);
        this.loginButton.setLocation(0, this.getHeight() - this.loginButton.getHeight());
        this.loginButton.addActionListener(loginButton);

        
        this.add(this.loginButton);
    }
    
     @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getParent().getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getParent().getWidth(),getHeight());
        
        // Background
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, this.getParent().getWidth() - 6,getHeight());
        
        g2d.setColor(oldColor);
    }  
    
}
