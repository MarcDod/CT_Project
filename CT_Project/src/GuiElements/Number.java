/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public class Number extends JPanel{
    
    private JLabel number;
    private Button up, down;
    
    private Color background;
    
    private int value;
    private int maxValue, minValue;
    
    public Number(int width, int height, Color background, int maxValue, int minValue){
        this.setLayout(null);
        this.setSize(width, height);
        this.value = 0;
        this.maxValue = maxValue;
        this.minValue = minValue;
        
        this.background = background;
        
        this.number = new JLabel();
        this.number.setText(String.valueOf(value));
        
        this.up = new Button(width,height/ 4);
        this.up.setBorder(null);
        this.down = new Button(width, height / 4);
        this.down.setBorder(null);
        
        this.number.setSize(width, height / 2);
        
        this.up.setLocation(0, 0);
        this.number.setLocation(0, this.up.getY() + this.up.getHeight());
        this.down.setLocation(0, this.number.getY() + this.number.getHeight());
        
        this.number.setHorizontalAlignment(JLabel.CENTER);
        this.number.setFont(Gui.BUTTON_FONT);
        this.number.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150),1));
        this.number.setOpaque(true);
        this.number.setBackground(Color.WHITE);
        
        this.up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionUp();
            }
        });
        
        this.down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionDown();
            }
        });
        
        this.up.setImage(getOpenImage(up.getWidth(), up.getHeight(), true));
        this.down.setImage(getOpenImage(down.getWidth(), down.getHeight(), false));
        
        this.add(number);
        this.add(up);
        this.add(down);
       
    }
    
     private BufferedImage getOpenImage(int width, int height, boolean up) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(background);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.WHITE);

        g2d.setStroke(new BasicStroke(1.0f));
        int y1 = (up) ? (int)((height - 1) * 0.75) : (int) (height * 0.25);
        int y2 = (!up) ? (int)height - 1 : 0;
        int y3 = (up) ? (int)((height - 1) * 0.75) : (int) (height * 0.25);
        g2d.fillPolygon(new int[]{(int)(width * 0.25), width / 2, (int)((width- 1) * 0.75)}, new int[]{y1, y2, y3}, 3);

        g2d.setColor(new Color(150, 150, 150));
        g2d.drawPolygon(new int[]{(int)(width * 0.25), width / 2, (int)((width- 1) * 0.75)}, new int[]{y1, y2, y3}, 3);
        
        g2d.dispose();

        return image;
    }
 
    private void actionUp(){
        this.value++;
        if(value > maxValue)value--;
        this.number.setText(String.valueOf(value));
    }
    
    private void actionDown(){
        this.value--;
        if(value < minValue)value++;
        this.number.setText(String.valueOf(value));
    }
    
    public int getValue(){
        return this.value;
    }
    
}
