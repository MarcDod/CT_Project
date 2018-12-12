/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author marc.doderer
 */
public class GroceryList extends Activity{
    
    public GroceryList() {
        super(ActivityID.GROCERY_LIST);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getParent().getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, this.getParent().getWidth(),getHeight());
        g2d.setColor(oldColor);
    } 
    
}
