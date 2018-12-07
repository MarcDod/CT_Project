package GuiElements;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;

/**
 *
 * @author marc.doderer
 */


public class Button extends JButton{

    
    public Button(int width, int height){
        Dimension d = new Dimension(width, height);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        super.paintComponent(g);
    }
    
}
