/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;

import GuiElements.Button;
import ct_project.Gui;
import java.awt.Color;
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
    
    private Button groceryList;
    
    public HomeScreen(ActivityID activityID, ActionListener actionListener) {
        super(activityID);
        
        int buttonWidth = Gui.SCREEN_WIDTH - 6 - 20;
        int buttonHeight = Gui.SCREEN_HEIGHT / 5;
        
        BufferedImage image = new BufferedImage(buttonWidth, buttonHeight,BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();

        drawGroceryList(g2d, buttonWidth, buttonHeight);
        
        g2d.dispose();
        
        this.groceryList = new Button(buttonWidth, buttonHeight, image);
        this.groceryList.setLocation((Gui.SCREEN_WIDTH - 6 - this.groceryList.getWidth()) / 2, 30);
        this.add(this.groceryList);
        
        this.groceryList.addActionListener(actionListener);
         
    }
    
    private void drawGroceryList(Graphics2D g2d, int width, int height){
        int groceryLists = 2;
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString("EINKAUFSLISTEN", 10, 30);
        
        GradientPaint gradient = new GradientPaint(0, 0, new Color(100,149,237), width, 0, new Color(025,025,112));
        g2d.setPaint(gradient);
        
        g2d.fillRect(0, height - 5, width, 5);
        
        g2d.setColor(oldColor);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getParent().getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, this.getParent().getWidth() - 6,getHeight());
        g2d.setColor(oldColor);
       
    }  
   
    
}
