/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.HomeManagerResourceManager;
import GuiElements.Button;
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
public class HomeScreenResourceManagerActivity extends Activity {

    public final static String ORDER_DONE = "ERLEDIGTE BESTELLUNGEN";
    public final static String ALL_ORDERS = "ALLE BESTELLUNGEN";
    public final static String NEW_ORDERS = "NEUE BESTELLUNGEN";
    public final static String CANCLED_ORDERS = "STRONIERTE BESTELLUNGEN";
    
    private Button[] buttons;
    
    private HomeManagerResourceManager homeManager;

    public HomeScreenResourceManagerActivity(ActionListener groceryListListener, ActionListener showOrders,HomeManagerResourceManager homeManager) {
        super(ActivityID.HOME_SCREEN_RESOURCE_MANAGER,"STARTFENSTER" ,new Color(240, 240, 240), homeManager);

        this.homeManager = homeManager;

        buttons = new Button[5];
        
        ActionListener saveButtonInput = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buttonsAction(ae);
            }
        };

        buttons[4] = initButton(CANCLED_ORDERS, showOrders, saveButtonInput);
        
        buttons[3] = initButton(ORDER_DONE, showOrders, saveButtonInput);
        
        buttons[2] = initButton(ALL_ORDERS, showOrders, saveButtonInput);
        
        // Button neue Bestellung
        BufferedImage image = drawGroceryList(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, new Color(124, 252, 0), new Color(0, 100, 0), "NEUE BESTELLUNGEN" , homeManager.getNotWatchedOrdes());
        buttons[0] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, image);
        buttons[0].setText(NEW_ORDERS);
        buttons[0].addActionListener(showOrders);
        buttons[0].addActionListener(saveButtonInput);
        
        // Button einkaufsliste
        image = drawGroceryList(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, new Color(100, 149, 237), new Color(025, 025, 112), "EINKAUFSLISTEN", homeManager.getGrocerySize());
        buttons[1] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, image);
        buttons[1].addActionListener(groceryListListener);



        for (int i = 0; i < buttons.length; i++) {
            int bottomLast = (i != 0) ? buttons[i - 1].getY() + buttons[i - 1].getHeight() : 0;
            buttons[i].setLocation((getWidth() - 6) / 2 - buttons[i].getWidth() / 2, bottomLast + 20);
            this.add(buttons[i]);
        }
    }
    
    private Button initButton(String text, ActionListener showOrderListener, ActionListener saveButtonInput){
        Button temp = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        temp.setBackground(Color.WHITE);
        temp.setFont(Gui.BUTTON_FONT);
        temp.setHorizontalAlignment(JButton.LEFT);
        temp.setForeground(Color.BLACK);
        temp.setFocusPainted(false);
        temp.setText(text);
        temp.addActionListener(showOrderListener);
        temp.addActionListener(saveButtonInput);
        return temp;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void buttonsAction(ActionEvent e){
        Button temp = (Button) e.getSource();
        this.homeManager.setButtonName(temp.getText());
    }
}
