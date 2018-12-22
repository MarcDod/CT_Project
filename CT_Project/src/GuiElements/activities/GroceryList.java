/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.GroceryManager;
import DataManagement.Datatemplates.Orderlist;
import GuiElements.Button;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author marc.doderer
 */
public class GroceryList extends Activity{

    private Button lists[];
    private JScrollPane jScrollPane;
    private JPanel jPanel;

    private Button newList;
    
    private GroceryManager groceryManager;

    public GroceryList(ActionListener lists, ActionListener newList ,GroceryManager groceryManager){
        super(ActivityID.GROCERY_LIST,"EINKAUFSLISTE", new Color(240, 240, 240));

        this.groceryManager = groceryManager;
        
        //<editor-fold defaultstate="collapsed" desc="init NewList">
        this.newList = new Button(Gui.SCREEN_WIDTH, 60);
        this.newList.setBackground(Gui.COLOR);
        this.newList.setForeground(Color.WHITE);
        this.newList.setFont(Gui.BUTTON_FONT);
        this.newList.setText("NEUE LISTE");
        this.newList.setLocation(0, this.getHeight() - this.newList.getHeight());
        this.newList.setFocusPainted(false);
        this.newList.addActionListener(newList);
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="inti scrollPane and panel">
this.jPanel = new JPanel();
this.jScrollPane = new JScrollPane(jPanel);
this.jScrollPane.setWheelScrollingEnabled(true);
this.jScrollPane.setSize(this.getWidth()+20, this.newList.getY());
this.jScrollPane.setLocation(-1, 0);
this.jPanel.setLayout(null);
this.jScrollPane.getVerticalScrollBar().setUnitIncrement(10);
this.jScrollPane.setBorder(null);
this.jPanel.setBorder(null);
//</editor-fold>
        
        this.add(this.newList);
        this.add(this.jScrollPane);

        ArrayList<Orderlist> groceryList = groceryManager.getGroceryList();
        if(groceryList == null) return;
        
        this.lists = new Button[groceryList.size()];
        for(int i = 0; i < this.lists.length; i++){
            this.lists[i] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, drawGroceryList(
                    Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, groceryList.get(i).getName(),
                    groceryList.get(i).getOrderIDs().size()));
            int bottomLast = (i != 0) ? this.lists[i - 1].getY() + this.lists[i - 1].
                    getHeight() : 0;
            this.lists[i].setLocation((getWidth() - 6) / 2 - this.lists[i].getWidth() / 2,
                    bottomLast + 20);
            this.lists[i].addActionListener(lists);
            this.lists[i].addActionListener((ActionEvent ae) -> {
                updateActiveList(ae);
            });
            this.jPanel.add(this.lists[i]);
        }

        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), this.lists.length * (Activity.STANDART_BUTTON_HEIGHT + 20) + 20));
        this.jPanel.
                setSize(Activity.STANDART_BUTTON_HEIGHT, this.lists.length * (Activity.STANDART_BUTTON_HEIGHT + 20) + 20);

        this.jPanel.setVisible(true);
        this.jScrollPane.setVisible(true);
    }

    private BufferedImage drawGroceryList(int width, int height, String text,
            int numberOrders){
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        drawStandartButton(g2d, width, height, text);
        
        g2d.setColor(Color.BLACK);
        Font font = new Font(Gui.BUTTON_FONT.getName(), Gui.BUTTON_FONT.
                getStyle(), 40);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(numberOrders), newList.getMargin().left
                + 3, height - 30);
        font = new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, 20);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setFont(font);
        g2d.drawString("GEGENSTÄNDE", newList.getMargin().left + 3 + fm.
                stringWidth(String.valueOf(numberOrders)) + 5, height - 30);

        g2d.dispose();

        return image;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    private Orderlist updateActiveList(ActionEvent ae){
        for(int i = 0; i < lists.length; i++){
            if(lists[i].equals(ae.getSource())){
                this.groceryManager.setActivIndex(i);
            }
        }
        return null;
    }
    
    public ArrayList<Orderlist> getList(){
        return this.groceryManager.getGroceryList();
    }
    
    public String getCurrentName(){
        return this.groceryManager.getActiveName();
    }

    public int getCurrentIndex() {
        return this.groceryManager.getActiveIndex();
    }
}
