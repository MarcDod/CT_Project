/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.HomeManager;
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
public class HomeScreen extends Activity {

    public final static String ORDER_DONE = "ERLEDIGTE BESTELLUNGEN";
    public final static String ALL_ORDERS = "ALLE BESTELLUNGEN";
    public final static String NEW_ORDERS = "NEUE BESTELLUNGEN";
    public final static String CANCLED_ORDERS = "STRONIERTE BESTELLUNGEN";
    
    private Button[] buttons;
    
    private HomeManager homeManager;

    public HomeScreen(ActionListener groceryListListener, ActionListener showOrders,HomeManager homeManager) {
        super(ActivityID.HOME_SCREEN,"STARTFENSTER" ,new Color(240, 240, 240), homeManager);

        this.homeManager = homeManager;

        buttons = new Button[5];
        
        ActionListener saveButtonInput = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buttonsAction(ae);
            }
        };

        buttons[4] = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        buttons[4].setBackground(Color.WHITE);
        buttons[4].setFont(Gui.BUTTON_FONT);
        buttons[4].setHorizontalAlignment(JButton.LEFT);
        buttons[4].setForeground(Color.BLACK);
        buttons[4].setFocusPainted(false);
        buttons[4].setText(CANCLED_ORDERS);
        buttons[4].addActionListener(showOrders);
        buttons[4].addActionListener(saveButtonInput);
        
        buttons[3] = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        buttons[3].setBackground(Color.WHITE);
        buttons[3].setFont(Gui.BUTTON_FONT);
        buttons[3].setHorizontalAlignment(JButton.LEFT);
        buttons[3].setForeground(Color.BLACK);
        buttons[3].setFocusPainted(false);
        buttons[3].setText(ORDER_DONE);
        buttons[3].addActionListener(showOrders);
        buttons[3].addActionListener(saveButtonInput);
        
        buttons[2] = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        buttons[2].setBackground(Color.WHITE);
        buttons[2].setFont(Gui.BUTTON_FONT);
        buttons[2].setHorizontalAlignment(JButton.LEFT);
        buttons[2].setForeground(Color.BLACK);
        buttons[2].setFocusPainted(false);
        buttons[2].setText(ALL_ORDERS);
        buttons[2].addActionListener(showOrders);
        buttons[2].addActionListener(saveButtonInput);
        
        // Button neue Bestellung
        BufferedImage image = drawGroceryList(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, new Color(124, 252, 0), new Color(0, 100, 0), "NEUE BESTELLUNGEN" , homeManager.getNotWatchedOrdes());
        buttons[0] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, image);
        buttons[0].setText("NEUE BESTELLUNGEN");
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
