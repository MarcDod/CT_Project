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
public class DateSelector extends JPanel{
    
    private JLabel[] labels;
    private MovableLabel[] moveLabels;
    
    public DateSelector(int width, int height){
        this.moveLabels = new MovableLabel[3];
        this.labels = new JLabel[3];
        this.setSize(width, height);
        this.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        
        
        for(int i = 0; i < labels.length; i++){
            this.labels[i] = new JLabel();
            this.labels[i].setSize((int)((width / 3) * 0.9),(int) (height * 0.8));
            this.labels[i].setLocation((this.getWidth() - ((this.labels[i].getWidth() + 7) * 3)) / 2 + i * (this.labels[i].getWidth() + 10), 0);           
            this.labels[i].setIcon(getLabelIcon(this.labels[i].getWidth(), this.labels[i].getHeight()));
            int maxNum = 31;
            boolean year = false;
            if(i == 1){
                maxNum = 12;
            }else if(i == 2){
                maxNum = 12;
                year = true;
            }
            this.moveLabels[i] = new MovableLabel(MovableLabel.TOP_DOWN_MODE);
            this.moveLabels[i].setSize(this.labels[i].getWidth(), maxNum * getFontMetrics().getHeight());
            this.moveLabels[i].setIcon(getMoveLabelIcons(maxNum, this.moveLabels[i].getWidth(), this.moveLabels[i].getHeight(), year));
            this.labels[i].add(this.moveLabels[i]); 
            this.moveLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent me) {
                    super.mouseReleased(me);
                    actionLabel(me);
                }
                
            });
            this.add(this.labels[i]);
        }
    }
    
    private void actionLabel(MouseEvent e){
        if(!(e.getSource() instanceof MovableLabel)) return;
        MovableLabel temp = (MovableLabel) e.getSource();
        int pos = (temp.getY() - getFontMetrics().getHeight() / 2) / getFontMetrics().getHeight();
        if(temp.getY() > 0) temp.setLocation(temp.getX(), 0);
        else if(temp.getY() < -temp.getHeight() + getFontMetrics().getHeight()) temp.setLocation(temp.getX(), -temp.getHeight() + getFontMetrics().getHeight());
        else temp.setLocation(temp.getX(), pos * getFontMetrics().getHeight());
    }
    
    private FontMetrics getFontMetrics(){
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = temp.createGraphics();
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 50));
        return g2d.getFontMetrics();
    }
    
    private Icon getLabelIcon(int width, int height){
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = temp.createGraphics();
        
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Gui.BUTTON_FONT.getName(), Font.BOLD, 50));
        FontMetrics fm  = g2d.getFontMetrics();
        //g2d.drawRect(0, (height - fm.getHeight()) / 2, width - 1, fm.getHeight());
        
        return new ImageIcon(temp);
    }
    
    private Icon getMoveLabelIcons(int lastNumb,int width,int height, boolean year){
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
        FontMetrics fm  = g2d.getFontMetrics();
        for(int i = 1; i <= lastNumb; i++){
            int tempValue = (year)? Calendar.getInstance().get(Calendar.YEAR) + i - 1 : i;
            g2d.drawString(String.valueOf(tempValue), (width - fm.stringWidth(String.valueOf(tempValue))) / 2, i * fm.getHeight());
        }
        
        return new ImageIcon(temp);
    }
    
}
