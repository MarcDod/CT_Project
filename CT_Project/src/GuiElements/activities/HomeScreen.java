/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
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
        super(ActivityID.HOME_SCREEN,"STARTFENSTER" ,new Color(240, 240, 240));

        int buttonWidth = Gui.SCREEN_WIDTH - 6 - 20;
        int buttonHeight = Gui.SCREEN_HEIGHT / 7;

        buttons = new Button[3];

        buttons[2] = new Button(buttonWidth, 50);
        buttons[2].setBackground(Color.WHITE);
        buttons[2].setFont(Gui.BUTTON_FONT);
        buttons[2].setHorizontalAlignment(JButton.LEFT);
        buttons[2].setForeground(Color.BLACK);
        buttons[2].setFocusPainted(false);
        buttons[2].setText("ERLEDIGTE BESTELLUNGEN");
        
        // Button neue Bestellung
        BufferedImage image = drawGroceryList(buttonWidth, buttonHeight, new Color(124, 252, 0), new Color(0, 100, 0), "NEUE BESTELLUNGEN");

        buttons[0] = new Button(buttonWidth, buttonHeight, image);

        // Button einkaufsliste
        image = drawGroceryList(buttonWidth, buttonHeight, new Color(100, 149, 237), new Color(025, 025, 112), "EINKAUFSLISTE");

        buttons[1] = new Button(buttonWidth, buttonHeight, image);
        buttons[1].addActionListener(groceryListListener);;


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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Text
        g2d.setColor(Color.BLACK);
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.drawString(text, buttons[buttons.length - 1].getMargin().left + 3, 30);
        
        // Anzeigen
        g2d.setColor(Gui.COLOR);
        int ovalWidth = (int)((width / 2) * 0.9);
        int ovalHeight = 37;
        int ovalX = (width) / 2 + (width / 2 - ovalWidth) / 2;
        int ovalY = height / 2;
        g2d.fillRoundRect(ovalX, ovalY, ovalWidth, ovalHeight, ovalHeight, ovalHeight);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        int textY = ovalY + (ovalHeight / 2 + fm.getHeight() / 3);
        int textX = ovalX + ((ovalWidth)/2 - fm.stringWidth("ANZEIGEN") / 2);
        g2d.drawString("ANZEIGEN", textX,textY);
        textY -= (fm.getHeight() / 4);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawPolyline(new int[]{ovalX + ovalWidth - ovalHeight + 5,ovalX + ovalWidth - ovalHeight + 10,ovalX + ovalWidth - ovalHeight + 5}, new int[]{textY - fm.getHeight() / 2,textY,textY + fm.getHeight() / 2}, 3);
        
        
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
