/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Evelyn
 */
public class MovableLabel extends JLabel{
    
    private int oldMouseX;
    private boolean leftButtonPressed;        
    private Color colorLeft;
    private Color colorRight;
    
    private boolean swipe;
    
    private ArrayList<ActionListener> actionLeft;
    private ArrayList<ActionListener> actionRight;
    
    private double actionXLeft;
    private double actionXRight;
    
    public MovableLabel(){
        initMovableLabel(Color.RED, Color.GREEN);
    }
    public MovableLabel(Color left, Color right){
        initMovableLabel(left,right);
    }
    private void initMovableLabel(Color left, Color right){
        this.leftButtonPressed = false;
        this.colorLeft = left;
        this.colorRight = right;
        this.swipe = true;
        this.actionLeft = new ArrayList<>();
        this.actionRight = new ArrayList<>();
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
        if(!this.leftButtonPressed || !this.swipe) return;
        int move = (e.getX() - oldMouseX);
        this.setLocation(this.getX() + move, this.getY());
        if(this.getX() > 0){
            this.getParent().setBackground(colorRight);
            if(this.getX() > actionXRight){
                for(ActionListener a : actionRight){
                    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
                }
            }
        }else if(this.getX() < 0){
            this.getParent().setBackground(colorLeft);
            if(this.getX() < actionXLeft){
                for(ActionListener a : actionLeft){
                    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
                }
            }
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
        this.setLocation(0, this.getY());
    }
    
    public void enableSwipe(){
        this.swipe = true;
    }
    
    public void disableSwipe(){
        this.swipe = false;
    }
    
    public void setActionXLeft(double x){
        this.actionXLeft = x;
    }
    
    public void setActionXRight(double x){
        this.actionXRight = x;
    }
    
    public void addActionListenerLeft(ActionListener newListenerLeft){
        this.actionLeft.add(newListenerLeft);
    }
    public void removeActionListenerLeft(ActionListener newListenerLeft){
        this.actionLeft.remove(newListenerLeft);
    }
    public void removeActionListenerLeft(int i){
        this.actionLeft.remove(i);
    }
    public void addActionListenerRight(ActionListener newListenerRight){
        this.actionRight.add(newListenerRight);
    }
    public void removeActionListenerRight(ActionListener newListenerRight){
        this.actionRight.remove(newListenerRight);
    }
    public void removeActionListenerRight(int i){
        this.actionRight.remove(i);
    }
}
