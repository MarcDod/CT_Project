/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author Marc
 */
public abstract class Selector extends JScrollPane{
    private JPanel jPanel; 
    protected ArrayList<Object> selectedElements;
    
    protected ArrayList<Object> allElements;
    
    protected ArrayList<JLabel> labels;
    protected int maxElements;
    
    protected Color selctedColor;
    private int maxHeight;
    
    public Selector(int width, int height, ArrayList allElements, JPanel panel, ArrayList selectedElements, int maxElements){
        super(panel);
        this.selctedColor = Color.decode("0xC9FFE5");
        if(selectedElements != null)
            this.selectedElements = selectedElements;
        else
            this.selectedElements = new ArrayList<>();
        this.allElements = allElements;
        this.labels = new ArrayList<>();
        this.maxElements = maxElements;
        init(width, height, panel);
    }
    
    private void init(int width, int height, JPanel panel){
        this.maxHeight = height;
        this.jPanel = panel;
        this.jPanel.setBackground(Color.WHITE);
        this.setSize(width, height);
        this.setWheelScrollingEnabled(true);
        this.setLocation(-1, 0);
        JScrollBar vertical = new JScrollBar();
        vertical.setPreferredSize(new Dimension(0,0));
        this.setVerticalScrollBar(vertical);
        this.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.jPanel.setMaximumSize(new Dimension(this.getWidth(), Integer.MAX_VALUE));
        this.jPanel.setLayout(null);
          
        this.getVerticalScrollBar().setUnitIncrement(10);

        initLabels();
    }
    
    protected void initLabels(){
        this.labels.removeAll(this.labels);
        this.jPanel.removeAll();
        int orderHeight = 70;
        for(int i = 0; i < allElements.size(); i++){
            JLabel temp = new JLabel();
            temp.setSize(this.getWidth(), orderHeight);
            Icon tempIcon = getIcon(temp.getWidth(), temp.getHeight(), i);
            temp.setIcon(tempIcon);
            int bottomLast = (i != 0) ? labels.get(i - 1).getY()
                    + labels.get(i - 1).getHeight() : 0;
            temp.setLocation(0, bottomLast);
            temp.setVisible(true);
            temp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    orderUpdate(e);  }
            
            });
            this.labels.add(temp);
            this.jPanel.add(temp);
        }
        this.jPanel.setPreferredSize(new Dimension(this.getWidth(), labels.size() * orderHeight));
        this.jPanel.
                setSize(this.getWidth(), labels.size() * orderHeight);
        this.jPanel.setVisible(true);
        int height = (maxHeight > jPanel.getHeight())? jPanel.getHeight() : maxHeight;
        this.setSize(this.getWidth(), height);
        this.setVisible(true);
    }
    
    protected void orderUpdate(MouseEvent e){
        int index = 0;
        for(int i = 0; i < labels.size(); i++){
            if(labels.get(i).equals(e.getSource())){
                index = i;
                break;
            }
        }
        if(selectedElements.contains(allElements.get(index))){
            selectedElements.remove((Object)allElements.get(index));
        }else if(selectedElements.size() < maxElements){
            selectedElements.add(allElements.get(index));
        }else{
            selectedElements.remove(selectedElements.size() - 1);
            selectedElements.add(allElements.get(index));
        }
        this.labels.get(index).setIcon(getIcon(this.labels.get(0).getWidth(), this.labels.get(0).getHeight(), index));
        this.jPanel.repaint();
    }
    
    abstract Icon getIcon(int width,int height, int i);
    
    public void addObject(Object object) throws IllegalArgumentException{
        if(this.allElements.contains(object)) throw new IllegalArgumentException();
        this.allElements.add(object);
        initLabels();
    }
    
    public ArrayList<Object> getSelectedElements(){
        return this.selectedElements;
    }
}
