/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;
import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Marc
 */
public class ShowOrder extends Activity{

    private final JPanel jPanel;
    private final JScrollPane jScrollPane;

    private JLabel orders[];
    
    public ShowOrder(List list) {
        super(ActivityID.SHOW_ORDER_SCREEN,list.getName() ,new Color(240, 240, 240));
        
        int orderHeight = 100;
        
        this.jPanel = new JPanel();
        this.jScrollPane = new JScrollPane(jPanel);
        this.jScrollPane.setWheelScrollingEnabled(true);
        this.jScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.jScrollPane.setSize(this.getWidth()+20, this.getHeight());
        this.jScrollPane.setLocation(-1, 0);
        
        this.jPanel.setMaximumSize(new Dimension(this.getWidth(), Integer.MAX_VALUE));
        this.jPanel.setLayout(null);
          
        this.jScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        this.jScrollPane.setBorder(null);
        this.jPanel.setBorder(null);
        
        this.add(this.jScrollPane);
        
        this.orders = new JLabel[list.getOrderIDs().length];
        
        for(int i = 0; i < this.orders.length; i++){
            this.orders[i] = new JLabel("Hallo");
            this.orders[i].setIcon(getOrderIcon(this.getWidth(), orderHeight));
            this.orders[i].setSize(this.getWidth(), orderHeight);
            int bottomLast = (i != 0) ? this.orders[i - 1].getY() + this.orders[i - 1].
                    getHeight() : 0;
            this.orders[i].setLocation(0, bottomLast);
            this.orders[i].setVisible(true);
            this.jPanel.add(this.orders[i]);
        }
        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), this.orders.length * orderHeight));
        this.jPanel.
                setSize(this.getWidth(), this.orders.length * orderHeight);

        this.jPanel.setVisible(true);
        this.jScrollPane.setVisible(true);
    }
    
    private Icon getOrderIcon(int width, int height){
                BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);


        return new ImageIcon(image);
    }
    
}
