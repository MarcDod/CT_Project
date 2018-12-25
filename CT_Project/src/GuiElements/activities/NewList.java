/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.ColorPalette;
import GuiElements.OrderSelector;
import GuiElements.TextField;

import ct_project.Gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import managers.NewListManager;

/**
 *
 * @author Marc
 */
public class NewList extends Activity {

    private Button createListButton;
    private ColorPalette palette;
    private JPanel listName;

    private OrderSelector orderSelector;
    
    private NewListManager newListManager;
    
    public NewList(ActionListener newListListener, NewListManager newListManager) {
        super(ActivityID.NEW_LIST, newListManager.getTitle(), new Color(240, 240, 240));

        this.newListManager = newListManager;
        
        //<editor-fold defaultstate="collapsed" desc="init listName">
        final int componentWidth = (int) (this.getWidth() * 0.9);
        final int componentHeight = 50;
        final int componentX = (this.getWidth() - 6 - (int) (this.getWidth() * 0.9)) / 2;
        this.listName = new JPanel(null);
        this.listName.setSize(componentWidth, componentHeight);
        this.listName.setLocation(componentX, 30);
        this.listName.setBackground(Color.WHITE);
        TextField tempTextField = new TextField(Color.WHITE, "Listenname", false);
        int textFieldWidth = (int)(componentWidth * 0.8);
        tempTextField.setSize(textFieldWidth, 20);
        tempTextField.setLocation(13, (componentHeight / 2) - tempTextField.getHeight() / 2);
        tempTextField.setText(newListManager.getListName());
        this.listName.add(tempTextField);
        this.listName.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        this.add(listName);
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="init ColorPalette">
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Gui.COLOR);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.PINK);
        colors.add(Color.DARK_GRAY);

        this.palette = new ColorPalette(newListManager.getColor(), colors, componentX, listName.getY() + listName.getHeight() + 20, componentWidth, componentHeight, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reLocate(componentX);
            }
        });
        this.add(palette);
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="init createButton">
        this.createListButton = new Button(Gui.SCREEN_WIDTH, 60);
        this.createListButton.setBackground(Gui.COLOR);
        this.createListButton.setForeground(Color.WHITE);
        this.createListButton.setFont(Gui.BUTTON_FONT);
        this.createListButton.setText("ERSTELLEN");
        this.createListButton.setLocation(0, this.getHeight() - this.createListButton.getHeight());
        this.createListButton.addActionListener(newListListener);
        this.createListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveNewList();
            }
        });
        this.add(createListButton);
//</editor-fold> 
        

        int maxHeight = this.createListButton.getY() - (this.palette.getY() +this.palette.getMaxHeight() + 20);
        this.orderSelector = new OrderSelector(componentWidth, maxHeight, this.newListManager.getAllOrders(), new JPanel(), this.newListManager.getOrderlist());
        reLocate(componentX);
        this.add(this.orderSelector);
    }

    private void reLocate(int componentX){
        this.orderSelector.setLocation(componentX, this.palette.getY() + this.palette.getHeight() + 20);
    }
    
    private void saveNewList(){
        try {
            newListManager.saveOrderList(palette.getColor(), ((TextField)listName.getComponent(0)).getString(), this.orderSelector.getOrders());
        } catch (IOException ex) {
            Logger.getLogger(NewList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
