/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public class DateSelector extends JPanel {

    private JLabel[] labels;
    private MovableLabel[] moveLabels;

    private BufferedImage image;

    private int[] date;

    public DateSelector(int width, int height) {
        this.moveLabels = new MovableLabel[3];
        this.labels = new JLabel[3];
        this.date = new int[]{1, 1, 1};
        this.setSize(width, height);
        this.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        
        for (int i = 0; i < labels.length; i++) {
            this.labels[i] = new JLabel();
            this.labels[i].setSize((int) ((this.getWidth() / 3) * 0.9), (int) (this.getHeight() * 0.8));
            int gap = (i == 2) ? 0 : -20;
            this.labels[i].setLocation((this.getWidth() - ((this.labels[i].getWidth() + 7) * 3)) / 2 + i * (this.labels[i].getWidth() + gap), 0);
            this.labels[i].setIcon(getLabelIcon(this.labels[i].getWidth(), this.labels[i].getHeight()));
        }
        initLabel(1, Calendar.getInstance().get(Calendar.YEAR));

        this.image = getImage();
    }

    private void initLabel(int monat, int yearInt) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].removeAll();
            int maxNum = getDaysInMonth(monat, yearInt);
            boolean year = false;
            if (i == 1) {
                maxNum = 12;
            } else if (i == 2) {
                maxNum = 12;
                year = true;
            }    
            this.moveLabels[i] = new MovableLabel(MovableLabel.TOP_DOWN_MODE);
            this.moveLabels[i].setSize(this.labels[i].getWidth(), maxNum * getFontMetrics().getHeight());
            this.moveLabels[i].setIcon(getMoveLabelIcons(maxNum, this.moveLabels[i].getWidth(), this.moveLabels[i].getHeight(), year));
            this.labels[i].add(this.moveLabels[i]);   
            this.moveLabels[i].setLocation(0, (this.date[i] - 1) * -1 * getFontMetrics().getHeight());
            this.moveLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent me) {
                    super.mouseReleased(me);
                    actionLabel(me);
                }

            });
            this.add(this.labels[i]);
        }
        this.repaint();
    }

    private BufferedImage getImage() {
        BufferedImage temp = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = temp.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2d.setColor(Color.BLACK);

        int r = 6;
        g2d.fillOval((labels[2].getX() - r) / 2 - r, (this.getHeight() - r) / 2 - r, r, r);
        g2d.fillOval((labels[2].getX() - r) / 2 - r, (this.getHeight() - r) / 2 + r, r, r);

        g2d.fillOval((labels[1].getX() + labels[1].getWidth() - r), (this.getHeight() - r) / 2 - r, r, r);
        g2d.fillOval((labels[1].getX() + labels[1].getWidth() - r), (this.getHeight() - r) / 2 + r, r, r);

        g2d.dispose();
        return temp;
    }

    private void actionLabel(MouseEvent e) {
        if (!(e.getSource() instanceof MovableLabel)) {
            return;
        }
        MovableLabel temp = (MovableLabel) e.getSource();
        int index = 0;
        for (int i = 0; i < moveLabels.length; i++) {
            if (moveLabels[i].equals(temp)) {
                index = i;
            }
        }
        
        int pos = (temp.getY() - getFontMetrics().getHeight() / 2) / getFontMetrics().getHeight();
        int maxInt = (index == 0)? -31 : -12; 
        if (pos <= maxInt) {
            pos = maxInt + 1;
        }
        if (pos >= 0) {
            pos = 0;
        }
        temp = moveLabels[index];
        if (temp.getY() > 0) {
            temp.setLocation(temp.getX(), 0);
        } else if (temp.getY() < -temp.getHeight() + getFontMetrics().getHeight()) {
            temp.setLocation(temp.getX(), -temp.getHeight() + getFontMetrics().getHeight());
        } else {
            temp.setLocation(temp.getX(), pos * getFontMetrics().getHeight());
        }

        this.date[index] = pos * -1 + 1;
        int days = getDaysInMonth(this.date[1], 
                this.date[2] + Calendar.getInstance().get(Calendar.YEAR) - 1);
        if(this.date[0] >= days) 
            this.date[0] = days;
        if (index != 0) {
            initLabel(this.date[1], this.date[2] + Calendar.getInstance().get(Calendar.YEAR) - 1);
        }

    }

    private void resetDayLength(int days) {
        this.moveLabels[0].setSize(this.moveLabels[0].getWidth(), days * getFontMetrics().getHeight());
    }

    private FontMetrics getFontMetrics() {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = temp.createGraphics();
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 50));
        return g2d.getFontMetrics();
    }

    private Icon getLabelIcon(int width, int height) {
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = temp.createGraphics();

        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 50));
        FontMetrics fm = g2d.getFontMetrics();

        return new ImageIcon(temp);
    }

    private Icon getMoveLabelIcons(int lastNumb, int width, int height, boolean year) {
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = temp.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 50));
        FontMetrics fm = g2d.getFontMetrics();
        for (int i = 1; i <= lastNumb; i++) {
            int tempValue = (year) ? Calendar.getInstance().get(Calendar.YEAR) + i - 1 : i;
            g2d.drawString(String.valueOf(tempValue), (width - fm.stringWidth(String.valueOf(tempValue))) / 2, i * fm.getHeight());
        }

        return new ImageIcon(temp);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(image, 0, 0, this);
    }

    private boolean getLeapYear(int year) {

        boolean leapYear = false;
        if (year % 4 == 0 && year % 100 != 0) {
            leapYear = true;
        }
        if (year % 400 == 0) {
            leapYear = true;
        }
        if (year % 100 == 0 && year % 400 != 0) {
            leapYear = false;
        }

        return leapYear;
    }

    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case (1):
                return 31;
            case (2):
                return (getLeapYear(year)) ? 29 : 28;
            case (3):
                return 31;
            case (4):
                return 30;
            case (5):
                return 31;
            case (6):
                return 30;
            case (7):
                return 31;
            case (8):
                return 31;
            case (9):
                return 30;
            case (10):
                return 31;
            case (11):
                return 30;
            case (12):
                return 31;
        }
        return 0;
    }
    
    public int[] getDate(){
        return new int[]{this.date[0], this.date[1], this.date[2] + Calendar.getInstance().get(Calendar.YEAR) - 1};
    }
}
