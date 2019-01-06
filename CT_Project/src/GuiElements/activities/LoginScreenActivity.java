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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JTextField;

/**
 *
 * @author Marc
 */
public class LoginScreenActivity extends Activity{
    
    private Button loginButton;
    
    private TextField userName;
    private TextField password;
    
    private LogInManager logInManager;
    
    private BufferedImage iconImage;
    
    public LoginScreenActivity(ActionListener loginButton,
            LogInManager logInManager) {
        super(ActivityID.LOGIN_SCREEN,"" ,Gui.SCREEN_HEIGHT - 29,
                Color.WHITE, logInManager);
        this.logInManager = logInManager;
       
        this.iconImage = loadImage();
       
        //<editor-fold defaultstate="collapsed" desc="init loginButton">
        this.loginButton = initBottomButton("LOGIN");
        this.loginButton.addActionListener(loginButton);
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loginAction();
            }
        });
//</editor-fold>
        
        
        this.userName = initTextField(350, "Username",false);
        this.password = initTextField(this.userName.getY() + 
                this.userName.getHeight() + 30, "Password",true);
            
        this.add(this.loginButton);
        this.add(this.userName);
        this.add(this.password);
    }
    
    private BufferedImage loadImage(){
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("rsc/Icon.png"));
        } catch (IOException ex) {
            this.notifyException("Bild konnte nicht geladen werden");
        }
        return icon;
    }
    
    private TextField initTextField(int y, String hintText,boolean password){
        TextField retTextField = new TextField(this.getBackground(),
                hintText,password);
        retTextField.setSize((int) (Gui.SCREEN_WIDTH * 0.8), 20);
        retTextField.setLocation(
                (Gui.SCREEN_WIDTH - 6) / 2 - retTextField.getWidth() / 2, y);
        retTextField.setHorizontalAlignment(JTextField.CENTER);
        return retTextField;
    }
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int d = (int)(this.getWidth() * 0.7);
        
        if(iconImage != null)
            g.drawImage(iconImage, (this.getWidth() - d) / 2, 0, d, d,this);
    }  
 
    private void loginAction(){
        try {
            logInManager.checkUserData(this.password.getString(),
                    this.userName.getString());
        } catch (SQLException | IllegalAccessException ex) {
            this.notifyException("Die Daten stimmen nicht überein.");
        } 
    }
    
    public Account getUser(){
        return this.logInManager.getUser();
    }
}
