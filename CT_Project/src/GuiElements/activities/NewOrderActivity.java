/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.DateSelector;
import GuiElements.ItemSelector;
import GuiElements.TextField;
import ct_project.Gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import managers.NewOrderManager;

/**
 *
 * @author Marc
 */
public class NewOrderActivity extends Activity {

    private DateSelector date;
    private ItemSelector itemSelector;
    private Button createOrderButton;

    private NewOrderManager newOrderManager;

    public NewOrderActivity(NewOrderManager newOrderManager, ActionListener newOrderListener) {
        super(ActivityID.NEW_ORDER, "NEUE BESTELLUNG", new Color(240, 240, 240), newOrderManager);
        
        final int componentWidth = (int) (this.getWidth() * 0.9);
        final int componentHeight = 100;
        final int componentX = (this.getWidth() - 6 - (int) (this.getWidth() * 0.9)) / 2;
        this.date = new DateSelector(componentWidth, componentHeight);
        this.date.setLocation(componentX, 30);

        this.itemSelector = new ItemSelector(componentWidth / 2, 300, newOrderManager.getItems(), new ArrayList<>());
        this.itemSelector.setLocation(componentX, this.date.getY() + this.date.getHeight() + 30);
        
        //<editor-fold defaultstate="collapsed" desc="init createButton">
        this.createOrderButton = new Button(Gui.SCREEN_WIDTH, 60);
        this.createOrderButton.setBackground(Gui.COLOR);
        this.createOrderButton.setForeground(Color.WHITE);
        this.createOrderButton.setFont(Gui.BUTTON_FONT);
        this.createOrderButton.setText("AUFTRAGEN");
        this.createOrderButton.setLocation(0, this.getHeight() - this.createOrderButton.getHeight());
        this.createOrderButton.addActionListener(newOrderListener);
        this.createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                makeNewOrder();
            }
        });
        this.add(createOrderButton);
//</editor-fold> 
        
        this.add(date);
        this.add(itemSelector);
        
    }
    
    private void makeNewOrder(){
        
    }

}
