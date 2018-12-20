package ct_project;

import DataManagement.Datatemplates.List;
import GuiElements.activities.Manager;
import GuiElements.MenuBar;
import GuiElements.activities.Activity;
import GuiElements.activities.ActivityID;
import GuiElements.activities.GroceryList;
import GuiElements.activities.HomeScreen;
import GuiElements.activities.LogInManager;
import GuiElements.activities.LoginScreen;
import GuiElements.activities.ShowOrder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

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
    private final Timer ping;

    public Gui() {
        this.ping = new Timer(1000, (ActionEvent e) -> {
            try {
                if(!manager.ping(60)){
                    Object[] options = {"Erneut Verbinden","Beenden"};
                    int optionResult = JOptionPane.showOptionDialog(frame, "Die Verbindung zur Datenbank wurde verloren.", "Verbindung verloren",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options,
                            options[1]);
                    switch(optionResult){
                        case 0:
                            manager.reconnect();
                            break;
                        case 1:
                            System.exit(1);
                            break;
                    }
                }
            }catch (SQLException ex){
                Logger.getLogger(Manager.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });
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
        
        this.ping.setInitialDelay(5000);
        this.ping.start();

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
        Activity tempActivity = null;
        for (int i = 0; i < this.panel.getComponentCount(); i++) {
            if (!(this.panel.getComponent(i) instanceof MenuBar)) {
                if(this.panel.getComponent(i) instanceof Activity)tempActivity = (Activity)this.panel.getComponent(i);
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

        switch (activity) {
            case HOME_SCREEN:
                tempActivity = new HomeScreen(getActionListener(ActivityID.GROCERY_LIST, activity));
                break;
            case GROCERY_LIST:
                tempActivity = new GroceryList(getActionListener(ActivityID.SHOW_ORDER_SCREEN, activity));
                break;
            case SHOW_ORDER_SCREEN:
                if (tempActivity != null){
                    if(tempActivity instanceof GroceryList){
                        GroceryList tempGroceryList = (GroceryList) tempActivity;
                        tempActivity = new ShowOrder(tempGroceryList.getActiveList());
                    }
                }
                break;
            case LOGIN_SCREEN:
                LogInManager logInManager = manager.getLogInManager();
                tempActivity = new LoginScreen(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (logInManager.isValid()) {
                            changeActivity(ActivityID.HOME_SCREEN);
                        }
                    }
                }, logInManager);
                break;
        }

        if (tempActivity != null) {
            this.panel.add(tempActivity);
            this.manager.setCurrentActivity(tempActivity);
        }

        if (this.manager.isEmpty()) {
            this.menu.disableReturnButton("Paul");
        } else {
            if (tempActivity != null) {
                this.menu.enableReturnButton(tempActivity.getTitle());
            }
        }

        this.frame.repaint();
    }

}
