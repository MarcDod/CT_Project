/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.OrderManager;
import DataManagement.Datatemplates.Order;
import GuiElements.MovableLabel;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Marc
 */
public class ShowOrder extends Activity{

    private final JPanel jPanel;
    private final JScrollPane jScrollPane;

    private OrderManager orderManager;
    
    public ShowOrder(OrderManager orderManager) throws SQLException {
        super(ActivityID.SHOW_ORDER_SCREEN, orderManager.getListName(),Color.WHITE);
        this.orderManager = orderManager;
        int orderHeight = 70;
        
        this.jPanel = new JPanel();
        this.jPanel.setBackground(this.getBackground());
        this.jScrollPane = new JScrollPane(jPanel);
        this.jScrollPane.setWheelScrollingEnabled(true);
        this.jScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.jScrollPane.setLocation(-1, 0);
        
        this.jPanel.setMaximumSize(new Dimension(this.getWidth(), Integer.MAX_VALUE));
        this.jPanel.setLayout(null);
          
        this.jScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        this.jScrollPane.setBorder(null);
        this.jPanel.setBorder(null);
        
        this.add(this.jScrollPane);
        
        try{
        buildOrders(orderHeight);
        } catch(NullPointerException ex){
              System.err.println("Keine Verbindung!");  
        } catch(SQLException ex){
            System.err.println("Fehler!");
        }
        reSizeJScroolPane(orderHeight);
    }
    
    private Icon getOrderIcon(int width, int height, int i, int size) throws SQLException, NullPointerException{
                BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Order tempOrder = this.orderManager.getOrder(i);
        if(tempOrder == null) return null; 

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
        int x = (size % 2 == 0) ? 1 : 0;
        if(i % 2 == x){
            g2d.setColor(Color.BLACK);
            g2d.drawRect(1, 0, width - 2, height - 1);
        }
        String productName = tempOrder.getItemName();
        String number = String.valueOf(tempOrder.getNumber());
        String price = tempOrder.getUser() +" hatt bestellt am: " 
                + tempOrder.getDate() + " Bitte fertig bis zum: " + tempOrder.getDeadline();
         
        // Text
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.setColor(Color.BLACK);
        g2d.drawString(productName, 16, (height - 1) / 2);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 40));
        g2d.drawString(number, width - 6 - 30 - fm.stringWidth(number), (height - 1) / 2 + fm.getHeight() / 2);
        
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, 13));
        fm = g2d.getFontMetrics();
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString(price, 16, (height - 1) / 2 + fm.getHeight());
        
        g2d.dispose();
        
        return new ImageIcon(image);
    }
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }  

    private void buildOrders(int orderHeight) throws SQLException, NullPointerException{ 
        jPanel.removeAll();
        this.orderManager.resetOrderLabel();
        for(int i = 0; i < orderManager.getListSize(); i++){
            MovableLabel temp = new MovableLabel();
            temp.setSize(this.getWidth() - 5, orderHeight);
            Icon tempIcon = getOrderIcon(temp.getWidth(), temp.getHeight(), i, orderManager.getListSize());
            if(tempIcon == null){
                this.orderManager.removeOrderWithID(i);
                i--;
                continue;
            }
            temp.setIcon(tempIcon);
            int bottomLast = (i != 0) ? this.orderManager.getOrderLabel(i - 1).getY()
                    + this.orderManager.getOrderLabel(i - 1).getHeight() : 0;
            temp.setLocation(0, bottomLast);
            temp.setVisible(true);
            temp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    orderReleased(e);  }
            
            });
            this.jPanel.add(temp);
            this.orderManager.addOrderLabel(temp);
        }
    }
  
    private void reSizeJScroolPane(int orderHeight){
        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), this.orderManager.getOrderLabelSize() * orderHeight));
        this.jPanel.
                setSize(this.getWidth(), this.orderManager.getOrderLabelSize() * orderHeight);
        int scrollHeight = (jPanel.getHeight() > this.getHeight()) ? this.getHeight():jPanel.getHeight();
        this.jScrollPane.setSize(jPanel.getWidth() + 20, scrollHeight);
        this.jPanel.setVisible(true);
        this.jScrollPane.setVisible(true);
    }
    
    private void orderReleased(MouseEvent e){
        MovableLabel temp = this.orderManager.getOrderLabel(e.getSource());
        if(temp == null) return;
        if(temp.getX() > temp.getWidth() / 2){
            removeOrder(temp);
            orderManager.orderAccept(temp);
        }else if(temp.getX() < -temp.getWidth() / 2){
            removeOrder(temp);
        }else{
            temp.setLocation(0, temp.getY());
        }
    }
    
    private void removeOrder(MovableLabel temp){
        try {
            this.orderManager.removeOrder(temp);
            buildOrders(temp.getHeight());
        } catch (SQLException ex) {
            Logger.getLogger(ShowOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException ex){
            System.err.println("Keine Verbidnung");
        } catch(IOException ex){
            
        }
        reSizeJScroolPane(temp.getHeight());
        repaint();
    }
}
