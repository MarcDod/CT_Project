package GuiElements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

/**
 *
 * @author marc.doderer
 */


public class Button extends JButton{

    private BufferedImage image;
    
    public Button(int width, int height){
        initButton(width, height);
        this.image = null;
    }
    
    public Button(int width, int height, BufferedImage image){
        initButton(width, height);
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        if(this.image ==  null){
            super.paintComponent(g);
            return;
        }
        g.drawImage(image, 0, 0, this);
        
    }
 
    public void setImage(BufferedImage image){
        this.image = image;
    }
    
    private void initButton(int width,int height){
        Dimension d = new Dimension(width, height);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        this.setRolloverEnabled(false);
    }
}
