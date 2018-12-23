/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Julian
 */
public class TextField extends JPasswordField{
    
    private String hintText;
    
    public TextField(Color bg, String hintText,boolean password){
        this.setBorder(null);
        this.setBackground(bg);
        this.hintText = hintText;
        this.setFont(new Font("SansSerif", Font.PLAIN, 15));
        if(!password){
            this.setEchoChar((char) 0);
        }
    }
   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
        if(this.getString().equals("")){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.BLACK);
            int hintX;
            if(this.getHorizontalAlignment() == JTextField.CENTER)
                hintX = (this.getWidth() - 6)/2 - fm.stringWidth(hintText) / 2;
            else{
                hintX = 0;
            }
            int hintTextY = this.getHeight() / 2 + fm.getHeight() / 4;
            g.drawString(hintText, hintX, hintTextY);
        }
    }
    
    public String getString(){
        return String.valueOf(this.getPassword());
    }
}
