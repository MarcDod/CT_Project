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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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

    private ArrayList<JLabel> orders;
    
    private OrderManager manager;
    
    public ShowOrder(List list, Manager manager) {
        super(ActivityID.SHOW_ORDER_SCREEN,list.getName() ,Color.WHITE);
        this.manager = new OrderManager(list, manager);
        int orderHeight = 70;
        
        this.jPanel = new JPanel();
        this.jPanel.setBackground(this.getBackground());
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
        
        drawOrders(orderHeight);
    }
    
    private Icon getOrderIcon(int width, int height, int i){
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
        g2d.setColor(this.getBackground());
        g2d.fillRect(0, 0, width, height);
        if(i % 2 == 0){
            g2d.setColor(Color.BLACK);
            g2d.drawRect(1, 0, width - 7, height - 1);
        }
        
        String productName = "Test";
        String number = "4";
        String price = "2.50€";
         
        // Text
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.setColor(Color.BLACK);
        g2d.drawString(productName, 16, (height - 1) / 2);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 40));
        g2d.drawString(number, width - 6 - 30 - fm.stringWidth(number), (height - 1) / 2 + fm.getHeight() / 2);
        
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, Gui.BUTTON_FONT.getSize()));
        fm = g2d.getFontMetrics();
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString(price, 16, (height - 1) / 2 + fm.getHeight() - 5);
        
        return new ImageIcon(image);
    }
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }  

    private void drawOrders(int orderHeight) {
        this.orders = new ArrayList<>();
        
        for(int i = 0; i < manager.getSize(); i++){
            JLabel temp = new JLabel("Hallo");
            temp.setIcon(getOrderIcon(this.getWidth(), orderHeight, i));
            temp.setSize(this.getWidth(), orderHeight);
            int bottomLast = (i != 0) ? this.orders.get(i - 1).getY() + this.orders.get(i - 1).
                    getHeight() : 0;
            temp.setLocation(0, bottomLast);
            temp .setVisible(true);
            this.jPanel.add(temp);
            this.orders.add(temp);
        }
        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), this.orders.size() * orderHeight));
        this.jPanel.
                setSize(this.getWidth(), this.orders.size() * orderHeight);

        this.jPanel.setVisible(true);
        this.jScrollPane.setVisible(true);
    }
}
