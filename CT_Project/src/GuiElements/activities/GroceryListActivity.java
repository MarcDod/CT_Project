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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author marc.doderer
 */
public class GroceryListActivity extends Activity {

    private Button lists[];
    private JScrollPane jScrollPane;
    private JPanel jPanel;

    private Button newList;

    private GroceryManager groceryManager;
    private ActionListener listsListener;

    public GroceryListActivity(ActionListener listsListener,
            ActionListener newListListener, GroceryManager groceryManager) {
        super(ActivityID.GROCERY_LIST, "EINKAUFSLISTE", new Color(240, 240, 240),
                groceryManager);

        this.groceryManager = groceryManager;
        this.listsListener = listsListener;

        this.newList = initBottomButton("NEUE LISTE");
        this.newList.addActionListener(newListListener);

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

    private void createButtons() {
        ArrayList<Orderlist> groceryList = groceryManager.getGroceryList();
        if (groceryList == null) {
            return;
        }

        this.jPanel.removeAll();

        this.lists = new Button[groceryList.size()];
        for (int i = 0; i < this.lists.length; i++) {

            MovableLabel temp = new MovableLabel(
                    this.getBackground(),
                    this.getBackground(),
                    MovableLabel.LEFT_RIGHT_MODE);
            temp.setSize(this.getWidth(), Activity.STANDART_BUTTON_HEIGHT);

            this.lists[i] = new Button(
                    Activity.STANDART_BUTTON_WIDTH,
                    Activity.STANDART_BUTTON_HEIGHT);

            this.lists[i].setImage(drawGroceryList(
                    Activity.STANDART_BUTTON_WIDTH,
                    Activity.STANDART_BUTTON_HEIGHT,
                    groceryList.get(i).getName(),
                    groceryList.get(i).getOrderIDs().size(),
                    Color.decode(groceryList.get(i).getColor())));

            int bottomLast;
            // Alle Orders ohne liste sollen nicht gelöscht / editiert werden.
            // Erste liste kann sich nicht unter eine andere liste hängen.
            if (i == 0) {
                bottomLast = 0;
                temp.disableSwipeLeftTop();
                temp.disableSwipeRightDown();
            } else {
                bottomLast = (this.lists[i - 1].getHeight() + 20) * (i);
            }

            temp.setLocation(0, bottomLast + 20);
            this.lists[i].setLocation(
                    (getWidth() - 6) / 2 - this.lists[i].getWidth() / 2, 0);
            //Button klick events
            this.lists[i].addActionListener(this.listsListener);
            this.lists[i].addActionListener((ActionEvent ae) -> {
                updateActiveList(ae);
            });

            // Rechts und Links swipe Actions der labels
            temp.setBoundaryLeftTop(-temp.getWidth() * 0.85);
            temp.setBoundaryRightDown(temp.getWidth() / 2);
            temp.addActionListenerLeftTop(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swipedLeft(e);
                }
            });
            temp.addActionListenerRightDown(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    swipedRight(e);
                }
            });

            temp.add(this.lists[i]);
            this.jPanel.add(temp);
        }

        this.jPanel.setPreferredSize(new Dimension(this.getWidth(),
                this.lists.length * (Activity.STANDART_BUTTON_HEIGHT + 20) + 20));
        this.jPanel.setSize(Activity.STANDART_BUTTON_HEIGHT,
                this.lists.length * (Activity.STANDART_BUTTON_HEIGHT + 20) + 20);
    }

    private int getIndexFromButtonInLabel(MovableLabel label) {
        for (int i = 0; i < lists.length; i++) {
            if (lists[i].equals(label.getComponent(0))) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    private void swipedRight(ActionEvent e) {
        MovableLabel temp = (MovableLabel) e.getSource();
        this.groceryManager.setActivIndex(getIndexFromButtonInLabel(temp));
        newList.getActionListeners()[0].actionPerformed(new ActionEvent(this, 0, ""));
    }

    private void swipedLeft(ActionEvent e) {
        MovableLabel temp = (MovableLabel) e.getSource();
        Object[] options = {"LÖSCHEN", "ZURÜCK"};
        int optionResult = JOptionPane.showOptionDialog(this, "Die Liste wirklich löschen?.", "Liste löschen",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

        switch (optionResult) {
            case 0:
                try {
                    this.groceryManager.removeList(getIndexFromButtonInLabel(temp));
                } catch (IOException ex) {
                    this.notifyException("Fehler beim Dateizugriff.");
                }
                createButtons();
                break;
            default:
                temp.setLocation(0, temp.getY());
                break;
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
}
