/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.TextField;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
    
    private Color background;
    
    public LoginScreen(ActionListener loginButton, LogInManager logInManager) {
        super(ActivityID.LOGIN_SCREEN, Gui.SCREEN_HEIGHT - 29);
        this.background = Color.WHITE;
        this.logInManager = new LogInManager();
        
        this.loginButton = new Button(Gui.SCREEN_WIDTH, 50, getLoginImage(Gui.SCREEN_WIDTH,
                50));
        this.loginButton.setLocation(0, this.getHeight() - this.loginButton.getHeight());
        this.loginButton.addActionListener(loginButton);
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loginAction();
            }
        });
        
        this.userName = initTextField(300, "Username",false);
        this.password = initTextField(350, "Password",true);
        
        this.add(this.loginButton);
        this.add(this.userName);
        this.add(this.password);
    }
    
    private TextField initTextField(int y, String hintText,boolean password){
        TextField retTextField = new TextField(background, hintText,password);
        retTextField.setSize((int) (Gui.SCREEN_WIDTH * 0.8), 20);
        retTextField.setLocation((Gui.SCREEN_WIDTH - 6) / 2 - retTextField.getWidth() / 2, y);
        
        retTextField.setHorizontalAlignment(JTextField.CENTER);
        
        return retTextField;
    }
    
    private BufferedImage getLoginImage(int width, int height){
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.decode("0x0000F0"));
        g2d.fillRect(0, 0, width, height);
        
        g2d.setFont(new Font("SansSerif", Font.BOLD, 15));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString("LOGIN", (width - 6)/2 - fm.stringWidth("LOGIN") / 2,height / 2 + fm.getHeight() / 4);
        
        g2d.dispose();
        return image;
    }
    
     @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getParent().getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getParent().getWidth(),getHeight());
        
        // Background
        g2d.setColor(background);
        g2d.fillRect(0, 0, this.getParent().getWidth() - 6,getHeight());
        
        g2d.setColor(oldColor);
    }  
 
    private void loginAction(){
        logInManager.checkUserData(this.password.getText(), this.userName.getText());
    }
}
