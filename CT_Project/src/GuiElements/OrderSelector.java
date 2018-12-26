/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import DataManagement.Datatemplates.Order;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public class OrderSelector extends Selector{

    
    public OrderSelector(int width, int height, ArrayList<Order> allElements, ArrayList<Integer> orderList) {
        super(width, height, allElements, new JPanel(), orderList, Integer.MAX_VALUE);
    }

    @Override
    protected void orderUpdate(MouseEvent e) {
        int index = 0;
        for(int i = 0; i < labels.size(); i++){
            if(labels.get(i).equals(e.getSource())){
                index = i;
                break;
            }
        }
        if(selectedElements.contains(((Order)allElements.get(index)).getOrderID())){
            selectedElements.remove((Object)((Order)allElements.get(index)).getOrderID());
        }else if(selectedElements.size() <= maxElements){
            selectedElements.add(((Order)allElements.get(index)).getOrderID());
        }
        this.labels.get(index).setIcon(getIcon(this.labels.get(0).getWidth(), this.labels.get(0).getHeight(), index));
    }
    
    
    @Override
    protected Icon getIcon(int width,int height, int i){
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
        for(Object l : selectedElements){
            if(((Order) allElements.get(i)).getOrderID() == (int) l ){
                selcted = true;
            }
        }
        Color temp = (selcted) ? selctedColor : Color.WHITE;
        g2d.setColor(temp);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, height -1, width, height -1);
        
        Order selectedOrder = (Order) allElements.get(i);
        
        String productName = selectedOrder.getItemName();
        String number = String.valueOf(selectedOrder.getNumber());
        String price = selectedOrder.getUser() +" hatt bestellt am: " 
                + selectedOrder.getDate() + " Bitte fertig bis zum: " + selectedOrder.getDeadline();
         
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
}
