/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.LogInManager;
import DataManagement.Datatemplates.Account;
import GuiElements.Button;
import GuiElements.TextField;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Marc
 */
public class LoginScreen extends Activity{
    
    private Button loginButton;
    
    private TextField userName;
    private TextField password;
    
    private LogInManager logInManager;
    
    public LoginScreen(ActionListener loginButton, LogInManager logInManager) {
        super(ActivityID.LOGIN_SCREEN,"" ,Gui.SCREEN_HEIGHT - 29, Color.WHITE);
        this.logInManager = logInManager;
        
        //<editor-fold defaultstate="collapsed" desc="init loginButton">
        this.loginButton = new Button(Gui.SCREEN_WIDTH, 60);
        this.loginButton.setBackground(Gui.COLOR);
        this.loginButton.setForeground(Color.WHITE);
        this.loginButton.setFont(Gui.BUTTON_FONT);
        this.loginButton.setText("LOGIN");
        this.loginButton.setLocation(0, this.getHeight() - this.loginButton.getHeight());
        this.loginButton.addActionListener(loginButton);
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loginAction();
            }
        });
//</editor-fold>
        
        this.userName = initTextField(300, "Username",false);
        this.password = initTextField(350, "Password",true);
        
        this.add(this.loginButton);
        this.add(this.userName);
        this.add(this.password);
    }
    
    private TextField initTextField(int y, String hintText,boolean password){
        TextField retTextField = new TextField(this.getBackground(), hintText,password);
        retTextField.setSize((int) (Gui.SCREEN_WIDTH * 0.8), 20);
        retTextField.setLocation((Gui.SCREEN_WIDTH - 6) / 2 - retTextField.getWidth() / 2, y);
        
        retTextField.setHorizontalAlignment(JTextField.CENTER);
        
        return retTextField;
    }
    
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }  
 
    private void loginAction(){
        try {
            logInManager.checkUserData(this.password.getString(), this.userName.getString());
        } catch (SQLException ex) {
            Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Account getUser(){
        return this.logInManager.getUser();
    }
}
