/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.HomeManagerResourceManager;
import GuiElements.Button;
import static GuiElements.activities.HomeScreenUserActivity.MY_ORDERS;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

/**
 *
 * @author marc.doderer
 */
public class HomeScreenResourceManagerActivity extends HomeScreenActivity {

    public final static String ORDER_DONE = "ERLEDIGTE BESTELLUNGEN";
    public final static String ALL_ORDERS = "ALLE BESTELLUNGEN";
    public final static String NEW_ORDERS = "NEUE BESTELLUNGEN";
    public final static String CANCLED_ORDERS = "STRONIERTE BESTELLUNGEN";
    
    private HomeManagerResourceManager homeManager;

    public HomeScreenResourceManagerActivity(ActionListener groceryListListener, ActionListener showOrders,HomeManagerResourceManager homeManager) {
        super(ActivityID.HOME_SCREEN_RESOURCE_MANAGER,"STARTFENSTER" ,
                homeManager ,showOrders, 
                new String[]{ALL_ORDERS, ORDER_DONE, CANCLED_ORDERS}, 
                new String[]{NEW_ORDERS, "EINKAUFSLISTE"});

        this.homeManager = homeManager;
        this.buttons[0].setImage(drawGroceryList(this.buttons[0].getWidth(), this.buttons[0].getHeight(), Color.decode("0x1fbc00"), Color.decode("0x0a3d00"), NEW_ORDERS, homeManager.getNotWatchedOrdes()));
        ActionListener temp = this.buttons[1].getActionListeners()[0];
        this.buttons[0].removeActionListener(temp);
        this.buttons[0].addActionListener(showOrders);
        this.buttons[0].addActionListener(temp);
        
        
        this.buttons[1].setImage(drawGroceryList(this.buttons[1].getWidth(), this.buttons[1].getHeight(), Color.decode("0x005fe2"), Color.decode("0x002c68"), "EINKAUFSLISTEN", homeManager.getGrocerySize()));
        this.buttons[1].addActionListener(groceryListListener);
    }

    private BufferedImage drawGroceryList(int width, int height, Color beginColor, Color endColor, String text, int number) {
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

    private void buttonsAction(ActionEvent e){
        Button temp = (Button) e.getSource();
        this.homeManager.setButtonName(temp.getText());
    }
}
