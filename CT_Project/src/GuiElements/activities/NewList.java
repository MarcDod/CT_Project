/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.ColorPalette;

import ct_project.Gui;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class NewList extends Activity{
    
        private Button createListButton;
        private ColorPalette palette;
    
    public NewList() {
        super(ActivityID.NEW_LIST, "Neue Liste", new Color(240, 240, 240));
        
        //<editor-fold defaultstate="collapsed" desc="init loginButton">
        this.createListButton = new Button(Gui.SCREEN_WIDTH, 60);
        this.createListButton.setBackground(Gui.COLOR);
        this.createListButton.setForeground(Color.WHITE);
        this.createListButton.setFont(Gui.BUTTON_FONT);
        this.createListButton.setText("ERSTELLEN");
        this.createListButton.setLocation(0, this.getHeight() - this.createListButton.getHeight());
        this.add(createListButton);
//</editor-fold> 

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.RED);
        
        this.palette = new ColorPalette(colors, (this.getWidth() - (int)(this.getWidth() * 0.9)) / 2, 100, (int)(this.getWidth() * 0.9), 60);
        this.add(palette);
    }

}
