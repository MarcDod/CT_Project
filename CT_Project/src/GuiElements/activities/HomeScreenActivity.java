/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import GuiElements.Button;
import ct_project.Gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import managers.HomeManager;

/**
 *
 * @author Marc
 */
public abstract class HomeScreenActivity extends Activity{
    
    protected Button[] buttons;
    
    private HomeManager homeManager;
    
    public HomeScreenActivity(ActivityID activityID, String title, 
            HomeManager manager, ActionListener showOrderListener, 
            String[] smallButtons, String[] bigButtons) {
        super(activityID, title, new Color(240, 240, 240), manager);
        this.homeManager = manager;
        ActionListener saveButtonInput = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buttonsAction(ae);
            }
        };
        this.buttons = new Button[smallButtons.length + bigButtons.length];
        for(int i = 0; i < buttons.length; i++){
            if(i < bigButtons.length)
                this.buttons[i] = initBigButton(bigButtons[i], saveButtonInput);
            else
                this.buttons[i] = initSmallButton(smallButtons
                        [i - bigButtons.length], showOrderListener, 
                    saveButtonInput);
        }
        
        for (int i = 0; i < buttons.length; i++) {
            int bottomLast = (i != 0) ? buttons[i - 1].getY() + buttons[i - 1].getHeight() : 0;
            buttons[i].setLocation((getWidth() - 6) / 2 - buttons[i].getWidth() / 2, bottomLast + 20);
            this.add(buttons[i]);
        }
    }
    
    private Button initBigButton(String text, ActionListener saveButtonInput){
        Button temp = new Button(Activity.STANDART_BUTTON_WIDTH, Activity.STANDART_BUTTON_HEIGHT);
        temp.addActionListener(saveButtonInput);
        temp.setText(text);
        return temp;
    }
    
    private Button initSmallButton(String text, ActionListener showOrderListener, ActionListener saveButtonInput){
        Button temp = new Button(Activity.STANDART_BUTTON_WIDTH, 50);
        temp.setBackground(Color.WHITE);
        temp.setFont(Gui.BUTTON_FONT);
        temp.setHorizontalAlignment(JButton.LEFT);
        temp.setForeground(Color.BLACK);
        temp.setFocusPainted(false);
        temp.setText(text);
        temp.addActionListener(showOrderListener);
        temp.addActionListener(saveButtonInput);
        return temp;
    }
    
    private void buttonsAction(ActionEvent ae){
        Button temp =(Button) ae.getSource();
        this.homeManager.setButtonName(temp.getText());
    }
}
