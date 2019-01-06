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
import GuiElements.Number;
import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    private JLabel deadLine;

    private Number number;

    private NewOrderManager newOrderManager;

    public NewOrderActivity(NewOrderManager newOrderManager,
            ActionListener newOrderListener) {
        super(ActivityID.NEW_ORDER, "NEUE BESTELLUNG", new Color(240, 240, 240),
                newOrderManager);
        this.newOrderListener = newOrderListener;

        final int componentWidth = (int) (this.getWidth() * 0.9);
        final int componentX = (this.getWidth() - 6 - (int) (this.getWidth()
                * 0.9)) / 2;
        this.newOrderManager = newOrderManager;

        this.newItem = initTextField(componentWidth / 2, 50, componentX,
                30, "Neues Item");

        this.itemSelector = new ItemSelector((int) (componentWidth * 0.9), 300,
                newOrderManager.getItems(), new ArrayList<>());
        this.itemSelector.setLocation(componentX + (componentWidth
                - this.itemSelector.getWidth()) / 2, newItem.getY() + newItem
                .getHeight());

        //<editor-fold defaultstate="collapsed" desc="init createButton">
        this.createOrderButton = initBottomButton("AUFGEBEN");
        this.createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                makeNewOrder(ae);
            }
        });
        this.add(createOrderButton);
//</editor-fold> 

        this.createItemButton = initButton(componentWidth - newItem.getWidth(),
                50,
                this.newItem.getX() + this.newItem.getWidth(),
                this.newItem.getY(), "HINZUFÜGEN");
        this.createItemButton.setFont(new Font(Gui.BUTTON_FONT.getName(),
                Font.BOLD, 20));
        this.createItemButton.setHorizontalAlignment(JButton.CENTER);
        this.createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addItem(((TextField) newItem.getComponent(0)).getString());
            }
        });

        this.number = new Number(50, 100, this.getBackground(), 999, 0);
        this.number.setLocation(this.itemSelector.getX(), this.itemSelector
                .getY() + this.itemSelector.getHeight() + 30);

        this.date = new DateSelector(this.itemSelector.getWidth() - this.number
                .getWidth() - 30, 90);
        this.date.setLocation(this.number.getX() + this.number.getWidth() + 30,
                this.number.getY() + (this.number.getHeight() - this.date
                .getHeight()) / 2);

        this.deadLine = new JLabel();
        this.deadLine.setSize(date.getWidth(), 25);
        this.deadLine.setLocation(date.getX(), date.getY() - deadLine
                .getHeight());
        this.deadLine.setFont(
                new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, 20));
        this.deadLine.setText("DEADLINE");

        this.add(deadLine);
        this.add(number);
        this.add(createItemButton);
        this.add(date);
        this.add(itemSelector);
        this.add(newItem);

    }

    private JPanel initTextField(int width, int height, int x, int y,
            String hiddenText) {
        JPanel temp = new JPanel(null);
        temp.setSize(width, height);
        temp.setLocation(x, y);
        temp.setBackground(Color.WHITE);
        TextField tempTextField = new TextField(Color.WHITE, hiddenText, false);
        int textFieldWidth = (int) (temp.getWidth() * 0.8);
        tempTextField.setSize(textFieldWidth, 20);
        int xTextField = (textFieldWidth + 16 >= width) ? 16 - (width
                - textFieldWidth) : 16;
        tempTextField.setLocation(xTextField, (temp.getHeight() / 2)
                - tempTextField.getHeight() / 2);
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
        try {
            int number = this.number.getValue();
            if (number == 0) {
                throw new IllegalArgumentException(
                        "Mindestens ein Item muss bestellt werden");
            }
            int[] dateInt = this.date.getDate();
            String deadLine = dateInt[0] + "." + dateInt[1] + "." + dateInt[2];
            String itemName = 
                    (String) this.itemSelector.getSelectedElements().get(0);
            this.newOrderManager.makeNewOrder(itemName, deadLine, number);
            this.newOrderListener.actionPerformed(ae);
        } catch (SQLException se) {
            this.notifyException("Fehler bei dem Zugriff auf die Datenbank");
        } catch (IllegalArgumentException ia) {
            this.notifyException(ia.getLocalizedMessage());
        } catch (IndexOutOfBoundsException iobe) {
            this.notifyException("Wählen sie ein Item aus");
        }
    }

    private void addItem(String itemName) {
        try {
            if (itemName.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (this.itemSelector.getAllElements().contains(itemName)) {
                throw new IllegalArgumentException();
            }
            this.newOrderManager.addItemName(itemName);
            this.itemSelector.addObject(itemName, true);
        } catch (IllegalArgumentException iae) {
            this.notifyException("Dieses Item ist bereits vorhanden");
        } catch (SQLException se) {
            this.notifyException("Fehler bei dem Zugriff auf die Datenbank");
        }
    }

}
