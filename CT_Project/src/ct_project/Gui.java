package ct_project;

import GuiElements.MenuBar;
import GuiElements.activities.ActivityID;
import GuiElements.activities.GroceryList;
import GuiElements.activities.HomeScreen;
import GuiElements.activities.LoginScreen;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author marc.doderer
 */
public class Gui {

    public static final int SCREEN_WIDTH = 450;
    public static final int SCREEN_HEIGHT = 800;

    private JFrame frame;
    private JPanel panel;
    private MenuBar menu;
    
    private Manager manager;
    
    public Gui() {
            this.frame = new JFrame("CT_Project");
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            final Dimension d = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
            this.frame.setSize(d);
            this.frame.setMaximumSize(d);
            this.frame.setMinimumSize(d);
            this.frame.setResizable(false);
            this.frame.setVisible(true);
            this.panel = new JPanel();
            this.panel.setPreferredSize(d);
            this.panel.setSize(d);
            this.menu = new MenuBar(getActionListener(), getActionListener(ActivityID.LOGIN_SCREEN));
            
            this.frame.setLayout(null);
            
            this.panel.add(menu);
            this.frame.add(panel);
            this.panel.setLayout(null);
            this.frame.pack();
            
            this.manager = new Manager();
            
            changeActivity(ActivityID.LOGIN_SCREEN);
    }

    private ActionListener getActionListener(ActivityID activity, ActivityID oldActivity) {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(oldActivity != ActivityID.LOGIN_SCREEN)
                    manager.addActivityID(oldActivity);
                changeActivity(activity);
            }
        };

        return listener;
    }

    private ActionListener getActionListener(ActivityID activity) {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.resetStack();
                changeActivity(activity);
            }
        };

        return listener;
    }
    
    private ActionListener getActionListener() {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeActivity(manager.getLastActivityID());
            }
        };

        return listener;
    }

    
    private void changeActivity(ActivityID activity) {
        for (int i = 0; i < this.panel.getComponentCount(); i++) {
            if (!(this.panel.getComponent(i) instanceof MenuBar)) {
                this.panel.getComponent(i).setVisible(false);
                this.panel.remove(i);
                i--;
            }
        }
        
        if(this.manager.isEmpty())
            this.menu.disableReturnButton();
        else
            this.menu.enableReturnButton();
        
        
        switch (activity) {
            case HOME_SCREEN:
                this.panel.add(new HomeScreen(getActionListener(ActivityID.GROCERY_LIST, activity)));
                break;
            case GROCERY_LIST:
                this.panel.add(new GroceryList());
                break;
            case LOGIN_SCREEN:
                this.panel.add(new LoginScreen(getActionListener(ActivityID.HOME_SCREEN, activity)));
                break;
        }
        
        this.frame.repaint();
    } 

    
}
