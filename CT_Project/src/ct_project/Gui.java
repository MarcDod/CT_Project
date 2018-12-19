package ct_project;

import GuiElements.activities.Manager;
import GuiElements.MenuBar;
import GuiElements.activities.ActivityID;
import GuiElements.activities.GroceryList;
import GuiElements.activities.HomeScreen;
import GuiElements.activities.LogInManager;
import GuiElements.activities.LoginScreen;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author marc.doderer
 */
public class Gui {

    public static final int SCREEN_WIDTH = 450;
    public static final int SCREEN_HEIGHT = 800;
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Color COLOR = Color.decode("0x0050be");
    

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

        changeActivity(ActivityID.HOME_SCREEN);
    }

    private ActionListener getActionListener(ActivityID activity, ActivityID oldActivity) {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        if (activity == ActivityID.LOGIN_SCREEN) {
            this.menu.setVisible(false);
        } else {
            this.menu.setVisible(true);
        }

        if (this.manager.isEmpty()) {
            this.menu.disableReturnButton("Paul");
        } else {
            this.menu.enableReturnButton(activity.getString());
        }

        switch (activity) {
            case HOME_SCREEN:
                this.panel.add(new HomeScreen(getActionListener(ActivityID.GROCERY_LIST, activity)));
                break;
            case GROCERY_LIST:
                this.panel.add(new GroceryList());
                break;
            case LOGIN_SCREEN:
                LogInManager logInManager = new LogInManager();
                this.panel.add(new LoginScreen(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if(logInManager.isValid()){
                            changeActivity(ActivityID.HOME_SCREEN);
                        }
                    }
                }, logInManager));
                break;
        }

        this.frame.repaint();
    }

}
