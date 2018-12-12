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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author Marc
 */
public class LoginScreen extends Activity{
    
    private Button loginButton;
    
    private JTextField userName;
    private JTextField password;
    
    private LogInManager logInManager;
    
    public LoginScreen(ActionListener loginButton, LogInManager logInManager) {
        super(ActivityID.LOGIN_SCREEN, Gui.SCREEN_HEIGHT - 29);
        
        this.logInManager = new LogInManager();
        
        this.loginButton = new Button(Gui.SCREEN_WIDTH, 50);
        this.loginButton.setLocation(0, this.getHeight() - this.loginButton.getHeight());
        this.loginButton.addActionListener(loginButton);
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loginAction();
            }
        });
        
        this.userName = initTextField(300);
        this.password = initTextField(350);
        
        this.add(this.loginButton);
        this.add(this.userName);
        this.add(this.password);
    }
    
    private JTextField initTextField(int y){
        JTextField retTextField = new JTextField();
        retTextField.setSize((int) (Gui.SCREEN_WIDTH * 0.9), 40);
        retTextField.setLocation((Gui.SCREEN_WIDTH - 6) / 2 - retTextField.getWidth() / 2, y);
        
        retTextField.setHorizontalAlignment(JTextField.CENTER);
        
        return retTextField;
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
 
    private void loginAction(){
        logInManager.checkUserData(this.password.getText(), this.userName.getText());
    }
}
