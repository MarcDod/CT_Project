/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import managers.HomeManagerUser;

/**
 *
 * @author Marc
 */
public class HomeScreenUserActivity extends HomeScreenActivity{
    public static final String MY_ORDERS = "MEINE OFFENEN BESTELLUNGEN";
    public static final String MY_GROUP_ORDERS = "BESTELLUNGEN MEINER GRUPPE";
    public static final String MY_CANCELED_ORDERS = "ABGELEHNTE BESTELLUNGEN";
    public static final String MY_FINISHED_ORDERS = "FERTIGE BESTELLUNGEN";
    
    private HomeManagerUser homeManager;
    
    public HomeScreenUserActivity(ActionListener newOrderListener, ActionListener showOrderListener, String title, HomeManagerUser homeManager) {
        super(ActivityID.HOME_SCREEN, title, homeManager, showOrderListener, 
                new String[]{MY_GROUP_ORDERS, MY_FINISHED_ORDERS, MY_CANCELED_ORDERS}, 
                new String[]{"NEUE BESTELLUNG", MY_ORDERS});
        
        this.homeManager = homeManager;  
        this.buttons[0].setImage(drawNewOrder(this.buttons[0].getWidth(), this.buttons[0].getHeight(), Color.decode("0x1fbc00"), Color.decode("0x0a3d00"), "NEUE BESTELLUNG"));
        this.buttons[0].addActionListener(newOrderListener);
        this.buttons[1].setImage(drawMyOrders(this.buttons[1].getWidth(), this.buttons[1].getHeight(), Color.decode("0x005fe2"), Color.decode("0x002c68"), MY_ORDERS, homeManager.getSizeOfMyOrder()));
        ActionListener temp = this.buttons[1].getActionListeners()[0];
        this.buttons[1].removeActionListener(temp);
        this.buttons[1].addActionListener(showOrderListener);
        this.buttons[1].addActionListener(temp);
    }

    
    private BufferedImage drawMyOrders(int width, int height, Color beginColor, Color endColor, String text, int number) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        drawStandartButton(g2d, width, height, text, Gui.COLOR);
        
        // Nummer
        String numberString = String.valueOf(number);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 50));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(numberString, width / 4 - fm.stringWidth(numberString) / 2, height / 2 + fm.getHeight() / 2);
        
        GradientPaint gradient = new GradientPaint(0, 0, beginColor, width, 0, endColor);
        g2d.setPaint(gradient);

        g2d.fillRect(0, height - 7, width, 7);

        g2d.dispose();

        return image;
    }
    
    private BufferedImage drawNewOrder(int width, int height, Color beginColor, Color endColor, String text) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        drawStandartButton(g2d, width, height, text, Gui.COLOR);
        
        GradientPaint gradient = new GradientPaint(0, 0, beginColor, width, 0, endColor);
        g2d.setPaint(gradient);

        g2d.fillRect(0, height - 7, width, 7);

        g2d.dispose();

        return image;
    }
    
    private void buttonsAction(ActionEvent ae){
        Button temp =(Button) ae.getSource();
        this.homeManager.setButtonName(temp.getText());
    }
}
