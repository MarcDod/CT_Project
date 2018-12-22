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
import javax.swing.JPasswordField;

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
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.decode("0x838B8B"));
            g.drawString(hintText, (this.getWidth() - 6)/2 - fm.stringWidth(hintText) / 2,this.getHeight() / 2 + fm.getHeight() / 4);
        }
    }
    
    public String getString(){
        return String.valueOf(this.getPassword());
    }
}
