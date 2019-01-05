/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import GuiElements.DateSelector;
import GuiElements.ItemSelector;
import GuiElements.TextField;
import ct_project.Gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import managers.NewOrderManager;

/**
 *
 * @author Marc
 */
public class NewOrderActivity extends Activity {

    private DateSelector date;
    private ItemSelector itemSelector;
    private Button createOrderButton;
    private ActionListener newOrderListener;
    
    
    private Button createItemButton;
    private JPanel newItem;
    
    private JPanel number;
    
    private NewOrderManager newOrderManager;

    public NewOrderActivity(NewOrderManager newOrderManager, ActionListener newOrderListener) {
        super(ActivityID.NEW_ORDER, "NEUE BESTELLUNG", new Color(240, 240, 240), newOrderManager);
        
        final int componentWidth = (int) (this.getWidth() * 0.9);
        final int componentHeight = 90;
        final int componentX = (this.getWidth() - 6 - (int) (this.getWidth() * 0.9)) / 2;
        this.newOrderManager = newOrderManager;
        this.date = new DateSelector(componentWidth, componentHeight);
        this.date.setLocation(componentX, 30);

        this.itemSelector = new ItemSelector(componentWidth / 2, 300, newOrderManager.getItems(), new ArrayList<>());
        this.itemSelector.setLocation(componentX, this.date.getY() + this.date.getHeight() + 30);

        //<editor-fold defaultstate="collapsed" desc="init createButton">
        this.createOrderButton = initButton(Gui.SCREEN_WIDTH, 60, 0, this.getHeight() - 60, "Aufgeben");
        this.newOrderListener = newOrderListener;
        this.createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                makeNewOrder(ae);
            }
        });
        this.add(createOrderButton);
//</editor-fold> 

        this.newItem = initTextField(componentWidth / 2, 50, 
                this.itemSelector.getX() + this.itemSelector.getWidth(), 
                this.itemSelector.getY(), "Neues Item");
        
        this.createItemButton = initButton(componentWidth / 2, 60,
                this.newItem.getX(),
                this.newItem.getY() + this.newItem.getHeight(), "Neues Item");
        this.createItemButton.setHorizontalAlignment(JButton.LEFT);
        this.createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addItem(((TextField) newItem.getComponent(0)).getString());
            }
        });

        this.number = initTextField(50, 50, this.createItemButton.getX(), this.createItemButton.getY() + this.createItemButton.getHeight(), "");
        ((TextField) this.number.getComponent(0)).setHorizontalAlignment(JTextField.CENTER);
        
        this.add(number);
        this.add(createItemButton);
        this.add(date);
        this.add(itemSelector);
        this.add(newItem);

    }
    
    private JPanel initTextField(int width, int height, int x, int y, String hiddenText){ 
        JPanel temp =  new JPanel(null);
        temp.setSize(width, height);
        temp.setLocation(x, y);
        temp.setBackground(Color.WHITE);
        TextField tempTextField = new TextField(Color.WHITE, hiddenText, false);
        int textFieldWidth = (int) (temp.getWidth() * 0.8);
        tempTextField.setSize(textFieldWidth, 20);
        int xTextField = (textFieldWidth + 16 >= width)? 16 - (width - textFieldWidth) : 16;
        tempTextField.setLocation(xTextField, (temp.getHeight() / 2) - tempTextField.getHeight() / 2);
        temp.add(tempTextField);
        temp.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        return temp;
    }

    private Button initButton(int width, int height, int x, int y, String text) {
        Button createButton = new Button(width, height);
        createButton.setBackground(Gui.COLOR);
        createButton.setForeground(Color.WHITE);
        createButton.setFont(Gui.BUTTON_FONT);
        createButton.setText(text);
        createButton.setLocation(x, y);
        createButton.setFocusPainted(false);
        return createButton;
    }

    private void makeNewOrder(ActionEvent ae) {
        try{
            int number = Integer.parseInt(((TextField) this.number.getComponent(0)).getString());      
            int[] dateInt = this.date.getDate();
            String deadLine = dateInt[0] + "." + dateInt[1] + "." + dateInt[2];
            String itemName = ((TextField) newItem.getComponent(0)).getString();
            this.newOrderManager.makeNewOrder(itemName, deadLine, number);
            this.newOrderListener.actionPerformed(ae);
        }catch(NumberFormatException ne){
            JOptionPane.showMessageDialog(this, "Eine vernünftige Zahl bitte");
        }catch(SQLException se){
            
        }catch(IllegalArgumentException ia){
            JOptionPane.showMessageDialog(this, ia.getMessage());
        }
    }

    private void addItem(String itemName){
        try {
            if(itemName.equals("")) throw new IllegalArgumentException();
            this.newOrderManager.addItemName(itemName);
            this.itemSelector.addObject(itemName);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, "Dieses Item ist bereits vorhanden");
        } catch(SQLException se){
            
        } catch(NullPointerException ex){
            
        }
    }

}
