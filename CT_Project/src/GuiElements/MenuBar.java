package GuiElements;

import ct_project.Gui;
import GuiElements.activities.Manager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author marc.doderer
 */


public class MenuBar extends JPanel{
    
    public static final int MENUBAR_HEIGHT = 50;
    
    private Button optionButton;
    private Button returnButton;
    
    public MenuBar(ActionListener returnListener, ActionListener optionListener){         
        //<editor-fold defaultstate="collapsed" desc="settings">
        Dimension d = new Dimension(Gui.SCREEN_WIDTH, MENUBAR_HEIGHT);
        this.setPreferredSize(d);
        this.setSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        this.setBounds(0, 0, Gui.SCREEN_WIDTH, d.height);
        this.setLayout(null);
        this.setFocusable(true);
//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Init Button">
        int width = 20;
        int height = d.height - 15;
        this.optionButton = new Button(width, height, getImage(width, height));
        this.optionButton.setLocation(Gui.SCREEN_WIDTH - 20 -
                this.optionButton.getWidth(),
                (d.height - this.optionButton.getHeight())/2);
        this.optionButton.setBorder(null);
        this.optionButton.addActionListener(optionListener);
        this.add(this.optionButton);
        

        height -= 10;
        width -= 10;
        this.returnButton = new Button(width, height, getRetrunImage(width, height));
        this.returnButton.setLocation(20,
                (d.height - this.returnButton.getHeight())/2);
        this.returnButton.setBorder(null);
        this.returnButton.addActionListener(returnListener);
        this.add(this.returnButton);
//</editor-fold>
    }

    private BufferedImage getRetrunImage(int width, int height){
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        
        g2d.setColor(Color.BLACK);
        
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawPolyline(new int[]{width, 0, width}, new int[]{0, height/2, height}, 3);
       
        g2d.dispose();
        
        return image;
    }
    
    private BufferedImage getImage(int width, int height){
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        
        g2d.setColor(Color.BLACK);
        
        int pHeight = height / 5;
        
        for(int i = 0; i < 3; i++){
            g2d.fillRect(width / 2 - pHeight / 2, (int) (i * pHeight * 1.5) + (int)(pHeight * 0.5), pHeight, pHeight);
        }
        
        g2d.dispose();
        
        return image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getParent().getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Color oldColor = g2d.getColor();
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getParent().getWidth(),getHeight());
        
        
        g2d.setColor(new Color(201, 201, 201));
        g2d.drawRect(0, 0, this.getParent().getWidth() - 7,getHeight() - 1);
        g2d.setColor(oldColor);
        
        
                
    }  
    
    public void disableReturnButton(){
        this.returnButton.setVisible(false);
    }
    public void enableReturnButton(){
        this.returnButton.setVisible(true);
    }
}
