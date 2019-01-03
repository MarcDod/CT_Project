/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.DateSelector;
import GuiElements.TextField;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import managers.NewOrderManager;

/**
 *
 * @author Marc
 */
public class NewOrder extends Activity {

    private DateSelector date;

    private NewOrderManager newOrderManager;

    public NewOrder(NewOrderManager newOrderManager) {
        super(ActivityID.NEW_ORDER, "NEUE BESTELLUNG", new Color(240, 240, 240), newOrderManager);
        
        final int componentWidth = (int) (this.getWidth() * 0.9);
        final int componentHeight = 100;
        final int componentX = (this.getWidth() - 6 - (int) (this.getWidth() * 0.9)) / 2;
        this.date = new DateSelector(componentWidth, componentHeight);
        this.date.setLocation(componentX, 30);
        
        this.add(date);
        
    }

}
