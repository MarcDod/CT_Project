package GuiElements;

import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author marc.doderer
 */


public class MenuBar extends JPanel{
    
    public Button optionButton;
    
    public MenuBar(){
        Dimension d = new Dimension(Gui.SCREEN_WIDTH,60);
        this.setPreferredSize(d);
        this.setSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        this.setBounds(0, 0, Gui.SCREEN_WIDTH, d.height);
        this.setLayout(null);
        this.setFocusable(true);
        
        this.optionButton = new Button(30, d.height - 10);
        this.optionButton.setLocation(Gui.SCREEN_WIDTH - 10 - 
                this.optionButton.getWidth(), 
                (d.height - this.optionButton.getHeight())/2);
        this.add(this.optionButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getParent().getWidth() - 1,getHeight() - 1);
        g2d.setColor(oldColor);
    }  
}
