/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;

/**
 *
 * @author Evelyn
 */
public class MovableLabel extends JLabel{
    
    private int oldMouseX;
    private boolean leftButtonPressed;        
    
    public MovableLabel(){
        this.leftButtonPressed = false;
        this.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                labelDragged(e);
            }      
        });
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                labelPressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelReleased(e);
            }        
        });
    }
    
    private void labelDragged(MouseEvent e){
        if(!this.leftButtonPressed) return;
        int move = (e.getX() - oldMouseX);
        this.setLocation(this.getX() + move, this.getY());
        if(this.getX() > 0){
            this.getParent().setBackground(Color.GREEN);
        }else{
            this.getParent().setBackground(Color.RED);
        }
    }
    
    private void labelPressed(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1){
            this.leftButtonPressed = true;
            this.oldMouseX = e.getX();
        }
    }
    
    private void labelReleased(MouseEvent e){
        this.leftButtonPressed = false;
    }
}
