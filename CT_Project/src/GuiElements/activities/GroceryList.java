/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import managers.GroceryManager;
import DataManagement.Datatemplates.Orderlist;
import GuiElements.Button;
import GuiElements.MovableLabel;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.EventListenerList;

/**
 *
 * @author marc.doderer
 */
public class GroceryList extends Activity {

    private Button lists[];
    private JScrollPane jScrollPane;
    private JPanel jPanel;

    private Button newList;

    private GroceryManager groceryManager;
    private ActionListener listsListener;
    
    public GroceryList(ActionListener listsListener, ActionListener newList, GroceryManager groceryManager) {
        super(ActivityID.GROCERY_LIST, "EINKAUFSLISTE", new Color(240, 240, 240));

        this.groceryManager = groceryManager;
        this.listsListener = listsListener;

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
        this.jScrollPane.setSize(this.getWidth() + 20, this.newList.getY());
        this.jScrollPane.setLocation(-1, 0);
        this.jPanel.setLayout(null);
        this.jScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.jScrollPane.setBorder(null);
        this.jPanel.setBorder(null);
//</editor-fold>

        this.add(this.newList);
        this.add(this.jScrollPane);

        createButtons();
        
        this.jPanel.setVisible(true);
        this.jScrollPane.setVisible(true);
    }

    private void createButtons(){
        ArrayList<Orderlist> groceryList = groceryManager.getGroceryList();
        if (groceryList == null) {
            return;
        }
        
        this.jPanel.removeAll();
        
        this.lists = new Button[groceryList.size()];
        for (int i = 0; i < this.lists.length; i++) {
            MovableLabel temp = new MovableLabel(this.getBackground(), this.getBackground());
            temp.setSize(this.getWidth(), Activity.STANDART_BUTTON_HEIGHT);
            this.lists[i] = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, drawGroceryList(
                    Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT, groceryList.get(i).getName(),
                    groceryList.get(i).getOrderIDs().size(), Color.decode(groceryList.get(i).getColor())));
            int bottomLast = (i != 0) ? (this.lists[i - 1].getHeight()  + 20) * (i): 0;
            temp.setLocation(0, bottomLast + 20);
            this.lists[i].setLocation((getWidth() - 6) / 2 - this.lists[i].getWidth() / 2,
                    0);
            this.lists[i].addActionListener(this.listsListener);
            this.lists[i].addActionListener((ActionEvent ae) -> {
                updateActiveList(ae);
            });
            temp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    deleteList(e);  
                }
            });
            temp.add(this.lists[i]);
            this.jPanel.add(temp);
        }

        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), this.lists.length * (Activity.STANDART_BUTTON_HEIGHT + 20) + 20));
        this.jPanel.
                setSize(Activity.STANDART_BUTTON_HEIGHT, this.lists.length * (Activity.STANDART_BUTTON_HEIGHT + 20) + 20);
    }

    private int getIndexFromButtonInLabel(MovableLabel label){
        for(int i = 0; i < lists.length; i++){
            if(lists[i].equals(label.getComponent(0)))
                return i;
        }
        return Integer.MAX_VALUE;
    }
    
    private void deleteList(MouseEvent e){
        try{
            MovableLabel temp = (MovableLabel) e.getSource();
            if(temp == null) return;
            if(temp.getX() > temp.getWidth() / 2){
                this.groceryManager.removeList(getIndexFromButtonInLabel(temp));
                createButtons();
            }else if(temp.getX() < -temp.getWidth() / 2){
                this.groceryManager.removeList(getIndexFromButtonInLabel(temp));
                createButtons();
            }else{
                temp.setLocation(0, temp.getY());
            }
        }catch(IOException ex){
            
        }
    }

    private BufferedImage drawGroceryList(int width, int height, String text,
            int numberOrders, Color color) {
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        drawStandartButton(g2d, width, height, text, color);

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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private Orderlist updateActiveList(ActionEvent ae) {
        for (int i = 0; i < lists.length; i++) {
            if (lists[i].equals(ae.getSource())) {
                this.groceryManager.setActivIndex(i);
            }
        }
        return null;
    }

    public ArrayList<Orderlist> getList() {
        return this.groceryManager.getGroceryList();
    }

    public String getCurrentName() {
        return this.groceryManager.getActiveName();
    }

    public int getCurrentIndex() {
        return this.groceryManager.getActiveIndex();
    }
}
