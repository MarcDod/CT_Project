/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.MenuBar;
import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import managers.ActivityManager;

/**
 *
 * @author Marc
 */
public abstract class Activity extends JPanel {

    private ActivityID activityID;
    private String title;

    public static final int STANDART_BUTTON_WIDTH = Gui.SCREEN_WIDTH - 6 - 20;
    public static final int STANDART_BUTTON_HEIGHT = Gui.SCREEN_HEIGHT / 7;

    private ActivityManager manager;

    public Activity(ActivityID activityID, String title, Color background, ActivityManager manager) {
        this(activityID, title, Gui.SCREEN_HEIGHT - 29 - MenuBar.MENUBAR_HEIGHT, background, manager);
        this.setLocation(0, MenuBar.MENUBAR_HEIGHT);
    }

    public Activity(ActivityID activityID, String title, int height, Color background, ActivityManager manager) {
        this.activityID = activityID;
        this.title = title;
        this.manager = manager;

        Dimension d = new Dimension(Gui.SCREEN_WIDTH, height);
        this.setPreferredSize(d);
        this.setSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        this.setBounds(0, 0, Gui.SCREEN_WIDTH, d.height);
        this.setLocation(0, 0);
        this.setLayout(null);
        this.setFocusable(true);
        this.setBackground(background);
    }

    public ActivityID getActivityID() {
        return this.activityID;
    }

    protected Button initBottomButton(String text){
        Button tempButton = new Button(Gui.SCREEN_WIDTH, 60);
        tempButton.setBackground(Gui.COLOR);
        tempButton.setForeground(Color.WHITE);
        tempButton.setFont(Gui.BUTTON_FONT);
        tempButton.setText(text);
        tempButton.setLocation(0, this.getHeight() - tempButton.getHeight());
        tempButton.setFocusPainted(false);
        
        return tempButton;
    }
    
    public String getTitle() {
        return this.title;
    }

    public void drawStandartButton(Graphics2D g2d, int buttonWidth, int buttonHeight, String text, Color color) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, buttonWidth, buttonHeight);

        // Text
        g2d.setColor(Color.BLACK);
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.drawString(text, 16, 30);

        // Anzeigen
        g2d.setColor(color);
        int ovalWidth = (int) ((buttonWidth / 2) * 0.9);
        int ovalHeight = 37;
        int ovalX = (buttonWidth) / 2 + (buttonWidth / 2 - ovalWidth) / 2;
        int ovalY = buttonHeight / 2;
        g2d.fillRoundRect(ovalX, ovalY, ovalWidth, ovalHeight, ovalHeight, ovalHeight);
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        int textY = ovalY + (ovalHeight / 2 + fm.getHeight() / 3);
        int textX = ovalX + ((ovalWidth) / 2 - fm.stringWidth("ANZEIGEN") / 2);
        g2d.drawString("ANZEIGEN", textX, textY);
        textY -= (fm.getHeight() / 4);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawPolyline(new int[]{ovalX + ovalWidth - ovalHeight + 5, ovalX + ovalWidth - ovalHeight + 10, ovalX + ovalWidth - ovalHeight + 5}, new int[]{textY - fm.getHeight() / 2, textY, textY + fm.getHeight() / 2}, 3);
    }

    public ActivityManager getActivityManager() {
        return this.manager;
    }

    protected void notifyException(String message) {
        JOptionPane.showMessageDialog(this, message, "Ein Fehler ist aufgetreten", JOptionPane.ERROR_MESSAGE);
    }

}
