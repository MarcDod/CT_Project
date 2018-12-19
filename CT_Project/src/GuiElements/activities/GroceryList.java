/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;
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
import java.awt.image.BufferedImage;

/**
 *
 * @author marc.doderer
 */
public class GroceryList extends Activity{
    
    Button lists[];
    
    Button newList;
    
    public GroceryList() {
        super(ActivityID.GROCERY_LIST, new Color(240, 240, 240));
        
        int buttonWidth = Gui.SCREEN_WIDTH - 6 - 20;
        int buttonHeight = Gui.SCREEN_HEIGHT / 7;
        
        
        this.newList = new Button(Gui.SCREEN_WIDTH, 60);
        this.newList.setBackground(Gui.COLOR);
        this.newList.setForeground(Color.WHITE);
        this.newList.setFont(Gui.BUTTON_FONT);
        this.newList.setText("NEUE LISTE");
        this.newList.setLocation(0, this.getHeight() - this.newList.getHeight());
        this.newList.setFocusPainted(false);
        this.add(this.newList);
        
        List[] groceryList = new List[]{new List("Neue Liste", new int[99]), new List("Neue Liste2", new int[55])};
        this.lists = new Button[groceryList.length];
        for(int i = 0; i < lists.length; i++){
            lists[i] = new Button(buttonWidth, buttonHeight, drawGroceryList(buttonWidth, buttonHeight, groceryList[i].getName(), groceryList[i].getOrderIDs().length));
            int bottomLast = (i != 0) ? lists[i - 1].getY() + lists[i - 1].getHeight() : 0;
            lists[i].setLocation((getWidth() - 6) / 2 - lists[i].getWidth() / 2, bottomLast + 20);
            this.add(lists[i]);
        }
    }
    
        private BufferedImage drawGroceryList(int width, int height, String text, int numberOrders) {
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
        g2d.drawString(text, newList.getMargin().left + 3, 30);
        Font font = new Font(Gui.BUTTON_FONT.getName(), Gui.BUTTON_FONT.getStyle(), 40);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(numberOrders), newList.getMargin().left + 3, height - 30);
        font = new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, 20);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setFont(font);
        g2d.drawString("GEGENSTÄNDE", newList.getMargin().left + 3 + fm.stringWidth(String.valueOf(numberOrders)) + 5, height - 30);
        
        // Anzeigen
        g2d.setColor(Gui.COLOR);
        int ovalWidth = (int)((width / 2) * 0.9);
        int ovalHeight = 37;
        int ovalX = (width) / 2 + (width / 2 - ovalWidth) / 2;
        int ovalY = height / 2;
        g2d.fillRoundRect(ovalX, ovalY, ovalWidth, ovalHeight, ovalHeight, ovalHeight);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        int textY = ovalY + (ovalHeight / 2 + fm.getHeight() / 3);
        int textX = ovalX + ((ovalWidth)/2 - fm.stringWidth("ANZEIGEN") / 2);
        g2d.drawString("ANZEIGEN", textX,textY);
        textY -= (fm.getHeight() / 4);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawPolyline(new int[]{ovalX + ovalWidth - ovalHeight + 5,ovalX + ovalWidth - ovalHeight + 10,ovalX + ovalWidth - ovalHeight + 5}, new int[]{textY - fm.getHeight() / 2,textY,textY + fm.getHeight() / 2}, 3);
        
        g2d.dispose();

        return image;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    } 
    
}
