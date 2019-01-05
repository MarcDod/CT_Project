/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import static GuiElements.activities.HomeScreenResourceManagerActivity.ALL_ORDERS;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import managers.HomeManager;

/**
 *
 * @author Marc
 */
public class HomeScreenActivity extends Activity{
    public static final String MY_ORDERS = "MEINE OFFENEN BESTELLUNGEN";
    public static final String MY_GROUP_ORDERS = "BESTELLUNGEN MEINER GRUPPE";
    public static final String MY_CANCELED_ORDERS = "ABGELEHNTE BESTELLUNGEN";
    public static final String My_FINISHED_ORDERS = "FERTIGE BESTELLUNGEN";
    private Button[] buttons;
    
    private HomeManager homeManager;
    
    public HomeScreenActivity(ActionListener newOrderListener, ActionListener showOrderListener, String title, HomeManager homeManager) {
        super(ActivityID.HOME_SCREEN, title, new Color(240, 240, 240), homeManager);
        
        this.homeManager = homeManager; 
        this.buttons = new Button[5];
        
        ActionListener saveButtonInput = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buttonsAction(ae);
            }
        };
        
                // Button neue Bestellung
        BufferedImage image = drawNewOrder(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, new Color(124, 252, 0), new Color(0, 100, 0), "NEUE BESTELLUNG");
        buttons[0] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, image);
        buttons[0].addActionListener(newOrderListener);
        
        // Button einkaufsliste
        image = drawMyOrders(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, new Color(100, 149, 237), new Color(025, 025, 112), MY_ORDERS, homeManager.getSizeOfMyOrder());
        buttons[1] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, image);
        buttons[1].addActionListener(showOrderListener);
        buttons[1].addActionListener(saveButtonInput);
        buttons[1].setText(MY_ORDERS);
        
        buttons[2] = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        buttons[2].setBackground(Color.WHITE);
        buttons[2].setFont(Gui.BUTTON_FONT);
        buttons[2].setHorizontalAlignment(JButton.LEFT);
        buttons[2].setForeground(Color.BLACK);
        buttons[2].setFocusPainted(false);
        buttons[2].setText(MY_GROUP_ORDERS);
        buttons[2].addActionListener(showOrderListener);
        buttons[2].addActionListener(saveButtonInput);
        
        buttons[3] = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        buttons[3].setBackground(Color.WHITE);
        buttons[3].setFont(Gui.BUTTON_FONT);
        buttons[3].setHorizontalAlignment(JButton.LEFT);
        buttons[3].setForeground(Color.BLACK);
        buttons[3].setFocusPainted(false);
        buttons[3].setText(My_FINISHED_ORDERS);
        buttons[3].addActionListener(showOrderListener);
        buttons[3].addActionListener(saveButtonInput);
        
        buttons[4] = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        buttons[4].setBackground(Color.WHITE);
        buttons[4].setFont(Gui.BUTTON_FONT);
        buttons[4].setHorizontalAlignment(JButton.LEFT);
        buttons[4].setForeground(Color.BLACK);
        buttons[4].setFocusPainted(false);
        buttons[4].setText(MY_CANCELED_ORDERS);
        buttons[4].addActionListener(showOrderListener);
        buttons[4].addActionListener(saveButtonInput);
        
        for (int i = 0; i < buttons.length; i++) {
            int bottomLast = (i != 0) ? buttons[i - 1].getY() + buttons[i - 1].getHeight() : 0;
            buttons[i].setLocation((getWidth() - 6) / 2 - buttons[i].getWidth() / 2, bottomLast + 20);
            this.add(buttons[i]);
        }
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