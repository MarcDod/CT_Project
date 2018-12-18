/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author marc.doderer
 */
public class GroceryList extends Activity{
    
    Button[] listen[];
    
    Button neueListe[];
    
    public GroceryList() {
        super(ActivityID.GROCERY_LIST, new Color(240, 240, 240));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    } 
    
}
