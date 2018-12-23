/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.ColorPalette;
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

    private NewListManager newListManager;
    
    public NewList(ActionListener newListListener, NewListManager newListManager) {
        super(ActivityID.NEW_LIST, "Neue Liste", new Color(240, 240, 240));

        this.newListManager = newListManager;
        
        //<editor-fold defaultstate="collapsed" desc="init listName">
        int componentWidth = (int) (this.getWidth() * 0.9);
        int componentHeight = 50;
        int componentX = (this.getWidth() - 6 - (int) (this.getWidth() * 0.9)) / 2;
        this.listName = new JPanel(null);
        this.listName.setSize(componentWidth, componentHeight);
        this.listName.setLocation(componentX, 30);
        this.listName.setBackground(Color.WHITE);
        TextField tempTextField = new TextField(Color.WHITE, "Listenname", false);
        int textFieldWidth = (int)(componentWidth * 0.8);
        tempTextField.setSize(textFieldWidth, 20);
        tempTextField.setLocation(13, (componentHeight / 2) - tempTextField.getHeight() / 2);
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

        this.palette = new ColorPalette(colors, componentX, listName.getY() + listName.getHeight() + 20, componentWidth, componentHeight);
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

    }

    private void saveNewList(){
        try {
            newListManager.saveOrderList(palette.getColor(), ((TextField)listName.getComponent(0)).getString());
        } catch (IOException ex) {
            Logger.getLogger(NewList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
