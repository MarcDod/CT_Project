/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;
import GuiElements.Button;
import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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

    private List[] groceryList;
    
    public static List activeList;
    
    public GroceryList(ActionListener lists){
        super(ActivityID.GROCERY_LIST,"EINKAUFSLISTE", new Color(240, 240, 240));

        this.newList = new Button(Gui.SCREEN_WIDTH, 60);
        this.newList.setBackground(Gui.COLOR);
        this.newList.setForeground(Color.WHITE);
        this.newList.setFont(Gui.BUTTON_FONT);
        this.newList.setText("NEUE LISTE");
        this.newList.setLocation(0, this.getHeight() - this.newList.getHeight());
        this.newList.setFocusPainted(false);

        this.jPanel = new JPanel();
        this.jScrollPane = new JScrollPane(jPanel);
        this.jScrollPane.setWheelScrollingEnabled(true);
        this.jScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        int buttonWidth = Gui.SCREEN_WIDTH - 6 - 20;
        int buttonHeight = Gui.SCREEN_HEIGHT / 7;
        this.jScrollPane.setSize(this.getWidth()+20, this.newList.getY());
        this.jScrollPane.setLocation(-1, 0);
        
        this.jPanel.setMaximumSize(new Dimension(this.getWidth(), Integer.MAX_VALUE));
        this.jPanel.setLayout(null);
          
        this.jScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        this.jScrollPane.setBorder(null);
        this.jPanel.setBorder(null);
        
        this.add(this.newList);
        this.add(this.jScrollPane);

        this.groceryList = new List[]{new List("PETER", new int[99]),
            new List("Neue Liste2", new int[55]), new List("Neue Liste",
            new int[99]), new List("Neue Liste", new int[99]), new List(
            "Neue Liste", new int[99]), new List("Neue Liste", new int[99]),
            new List("Neue Liste", new int[99]), new List("Neue Liste",
            new int[99]), new List("Neue Liste", new int[99]), new List(
            "Neue Liste", new int[99]), new List("Neue Liste", new int[99])};
        this.lists = new Button[groceryList.length];
        for(int i = 0; i < this.lists.length; i++){
            this.lists[i] = new Button(buttonWidth, buttonHeight, drawGroceryList(
                    buttonWidth, buttonHeight, groceryList[i].getName(),
                    groceryList[i].getOrderIDs().length));
            int bottomLast = (i != 0) ? this.lists[i - 1].getY() + this.lists[i - 1].
                    getHeight() : 0;
            this.lists[i].setLocation((getWidth() - 6) / 2 - this.lists[i].getWidth() / 2,
                    bottomLast + 20);
            this.lists[i].addActionListener(lists);
            this.lists[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    orderAction(ae);
                }
            });
            this.jPanel.add(this.lists[i]);
        }

        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), this.lists.length * (buttonHeight + 20) + 20));
        this.jPanel.
                setSize(buttonWidth, this.lists.length * (buttonHeight + 20) + 20);

        this.jPanel.setVisible(true);
        this.jScrollPane.setVisible(true);
    }

    private BufferedImage drawGroceryList(int width, int height, String text,
            int numberOrders){
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

        // Text
        g2d.setColor(Color.BLACK);
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.drawString(text, newList.getMargin().left + 3, 30);
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

        // Anzeigen
        g2d.setColor(Gui.COLOR);
        int ovalWidth = (int) ((width / 2) * 0.9);
        int ovalHeight = 37;
        int ovalX = (width) / 2 + (width / 2 - ovalWidth) / 2;
        int ovalY = height / 2;
        g2d.fillRoundRect(ovalX, ovalY, ovalWidth, ovalHeight, ovalHeight,
                ovalHeight);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        int textY = ovalY + (ovalHeight / 2 + fm.getHeight() / 3);
        int textX = ovalX + ((ovalWidth) / 2 - fm.stringWidth("ANZEIGEN") / 2);
        g2d.drawString("ANZEIGEN", textX, textY);
        textY -= (fm.getHeight() / 4);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawPolyline(new int[]{ovalX + ovalWidth - ovalHeight + 5, ovalX
            + ovalWidth - ovalHeight + 10, ovalX + ovalWidth - ovalHeight + 5},
                new int[]{textY - fm.getHeight() / 2, textY, textY + fm.
                    getHeight() / 2}, 3);

        g2d.dispose();

        return image;
    }

    @Override
    protected void paintComponent(Graphics g){
        for(int i = 0; i < lists.length; i++){
            lists[i].repaint();
        }
        super.paintComponent(g);
    }

    private void orderAction(ActionEvent ae){
        for(int i = 0; i < lists.length; i++){
            if(lists[i].equals(ae.getSource())){
                activeList = groceryList[i];
                break;
            }
        }
    }
    
}
