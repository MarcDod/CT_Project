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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author marc.doderer
 */
public class HomeScreen extends Activity{
    
    private Button showGroceryList;
    private Button showNewOrder;
    
    public HomeScreen(ActionListener groceryListListener) {
        super(ActivityID.HOME_SCREEN);
        
        int buttonWidth = Gui.SCREEN_WIDTH - 6 - 20;
        int buttonHeight = Gui.SCREEN_HEIGHT / 7;
        
        // Button neue Bestellung
        BufferedImage image = drawGroceryList(buttonWidth, buttonHeight, new Color(124, 252, 0) , new Color(0, 100, 0), "NEUE BESTELLUNGEN");
        
        this.showNewOrder = new Button(buttonWidth, buttonHeight, image);
        this.add(this.showNewOrder);
        this.showNewOrder.setLocation((Gui.SCREEN_WIDTH - 6 - this.showNewOrder.getWidth()) / 2, 30);
        
        // Button einkaufsliste
        image = drawGroceryList(buttonWidth, buttonHeight, new Color(100,149,237), new Color(025,025,112), "EINKAUFSLISTE");
           
        this.showGroceryList = new Button(buttonWidth, buttonHeight, image);
        this.showGroceryList.setLocation((Gui.SCREEN_WIDTH - 6 - this.showGroceryList.getWidth()) / 2, this.showNewOrder.getX() + this.showNewOrder.getHeight() + 30);
        this.showGroceryList.addActionListener(groceryListListener);
        this.add(this.showGroceryList);
        
        
         
    }
    
    private BufferedImage drawGroceryList(int width, int height, Color beginColor, Color endColor, String text){
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        
        int groceryLists = 2;
        
        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // Text
        g2d.setColor(Color.BLACK);
        Font font = new Font("SansSerif", Font.BOLD, 15);
        g2d.setFont(font);
        g2d.drawString(text, 10, 30);
        
        
        GradientPaint gradient = new GradientPaint(0, 0, beginColor, width, 0, endColor);
        g2d.setPaint(gradient);
        
        g2d.fillRect(0, height - 7, width, 7);
              
        g2d.dispose();
        
        return  image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getParent().getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, this.getParent().getWidth() - 6,getHeight());
        g2d.setColor(oldColor);
       
    }  
   
    
}
