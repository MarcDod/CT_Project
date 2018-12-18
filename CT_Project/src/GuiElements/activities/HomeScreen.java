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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author marc.doderer
 */
public class HomeScreen extends Activity {

    private Button[] buttons;

    public HomeScreen(ActionListener groceryListListener) {
        super(ActivityID.HOME_SCREEN, new Color(240, 240, 240));

        int buttonWidth = Gui.SCREEN_WIDTH - 6 - 20;
        int buttonHeight = Gui.SCREEN_HEIGHT / 7;

        buttons = new Button[3];

        // Button neue Bestellung
        BufferedImage image = drawGroceryList(buttonWidth, buttonHeight, new Color(124, 252, 0), new Color(0, 100, 0), "NEUE BESTELLUNGEN");

        buttons[0] = new Button(buttonWidth, buttonHeight, image);

        // Button einkaufsliste
        image = drawGroceryList(buttonWidth, buttonHeight, new Color(100, 149, 237), new Color(025, 025, 112), "EINKAUFSLISTE");

        buttons[1] = new Button(buttonWidth, buttonHeight, image);
        buttons[1].addActionListener(groceryListListener);;

        buttons[2] = new Button(buttonWidth, 50);
        buttons[2].setBackground(Color.WHITE);
        buttons[2].setFont(Gui.BUTTON_FONT);
        buttons[2].setHorizontalAlignment(JButton.LEFT);
        buttons[2].setForeground(Color.BLACK);
        buttons[2].setFocusPainted(false);
        buttons[2].setText("ERLEDIGTE BESTELLUNGEN");

        for (int i = 0; i < buttons.length; i++) {
            int bottomLast = (i != 0) ? buttons[i - 1].getY() + buttons[i - 1].getHeight() : 0;
            buttons[i].setLocation((getWidth() - 6) / 2 - buttons[i].getWidth() / 2, bottomLast + 20);
            this.add(buttons[i]);
        }
    }

    private BufferedImage drawGroceryList(int width, int height, Color beginColor, Color endColor, String text) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Text
        g2d.setColor(Color.BLACK);
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.drawString(text, 13, 30);

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

}
