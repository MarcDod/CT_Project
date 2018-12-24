/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import DataManagement.Datatemplates.Order;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author Marc
 */
public class OrderSelector extends JScrollPane{
    
    private JPanel jPanel; 
    private ArrayList<Integer> orderList;
    
    private ArrayList<Order> allOrders;
    
    private ArrayList<JLabel> labels;
    
    private Color selctedOrder;
    
    
    public OrderSelector(int width, int height, ArrayList<Order> allOrders, JPanel panel, ArrayList<Integer> orderList){
        super(panel);
        this.selctedOrder = Color.decode("0xC9FFE5");
        if(orderList != null)
            this.orderList = orderList;
        else
            this.orderList = new ArrayList<>();
        this.allOrders = allOrders;
        this.labels = new ArrayList<>();
        init(width, height, panel);
    }
    
    private void init(int width, int height, JPanel panel){
        int orderHeight = 70;
        this.jPanel = panel;
        this.jPanel.setBackground(Color.WHITE);
        this.setSize(width, height);
        this.setWheelScrollingEnabled(true);
        this.setLocation(-1, 0);
        JScrollBar vertical = new JScrollBar();
        vertical.setPreferredSize(new Dimension(0,0));
        this.setVerticalScrollBar(vertical);
        this.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.jPanel.setMaximumSize(new Dimension(this.getWidth(), Integer.MAX_VALUE));
        this.jPanel.setLayout(null);
          
        this.getVerticalScrollBar().setUnitIncrement(10);

        //this.setBorder(null);
        //this.jPanel.setBorder(null);
        
        for(int i = 0; i < allOrders.size(); i++){
            JLabel temp = new JLabel();
            temp.setSize(this.getWidth(), orderHeight);
            Icon tempIcon = getIcon(temp.getWidth(), temp.getHeight(), i);
            temp.setIcon(tempIcon);
            int bottomLast = (i != 0) ? labels.get(i - 1).getY()
                    + labels.get(i - 1).getHeight() : 0;
            temp.setLocation(0, bottomLast);
            temp.setVisible(true);
            temp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    orderUpdate(e);  }
            
            });
            this.labels.add(temp);
            this.jPanel.add(temp);
        }
        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), labels.size() * orderHeight));
        this.jPanel.
                setSize(this.getWidth(), labels.size() * orderHeight);
        this.jPanel.setVisible(true);
        height = (height > jPanel.getHeight())? jPanel.getHeight() : height;
        this.setSize(width, height);
        this.setVisible(true);
    }
    
    private void orderUpdate(MouseEvent e){
        int index = 0;
        for(int i = 0; i < labels.size(); i++){
            if(labels.get(i).equals(e.getSource())){
                index = i;
                break;
            }
        }
        if(orderList.contains(allOrders.get(index).getOrderID())){
            orderList.remove((Object)allOrders.get(index).getOrderID());
        }else{
            orderList.add(allOrders.get(index).getOrderID());
        }
        this.labels.get(index).setIcon(getIcon(this.labels.get(0).getWidth(), this.labels.get(0).getHeight(), index));
        this.jPanel.repaint();
    }
    
    private Icon getIcon(int width,int height, int i){
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
        boolean selcted = false;
        for(Integer l : orderList){
            if(allOrders.get(i).getOrderID() == l){
                selcted = true;
            }
        }
        Color temp = (selcted) ? selctedOrder : Color.WHITE;
        g2d.setColor(temp);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, height -1, width, height -1);
        
        String productName = allOrders.get(i).getItemName();
        String number = String.valueOf(allOrders.get(i).getNumber());
        String price = allOrders.get(i).getUser() +" hatt bestellt am: " 
                + allOrders.get(i).getDate() + " Bitte fertig bis zum: " + allOrders.get(i).getDeadline();
         
        // Text
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.setColor(Color.BLACK);
        g2d.drawString(productName, 16, (height - 1) / 2);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 40));
        g2d.drawString(number, width - 6 - 30 - fm.stringWidth(number), (height - 1) / 2 + fm.getHeight() / 2 - 10);
        
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, 13));
        fm = g2d.getFontMetrics();
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString(price, 16, (height - 1) / 2 + fm.getHeight());
        
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    public ArrayList<Integer> getOrders(){
        return this.orderList;
    }
}
