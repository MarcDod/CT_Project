package ct_project;

import managers.Manager;
import GuiElements.MenuBar;
import GuiElements.activities.Activity;
import GuiElements.activities.ActivityID;
import GuiElements.activities.GroceryListActivity;
import GuiElements.activities.HomeScreenActivity;
import GuiElements.activities.HomeScreenResourceManagerActivity;
import managers.LogInManager;
import GuiElements.activities.LoginScreenActivity;
import GuiElements.activities.NewListActivity;
import GuiElements.activities.NewOrderActivity;
import GuiElements.activities.ShowOrderActivity;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jdom2.JDOMException;

/**
 *
 * @author marc.doderer
 */
public class Gui{

    public static final int SCREEN_WIDTH = 450;
    public static final int SCREEN_HEIGHT = 800;
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Color COLOR = Color.decode("0x0050be");

    private JFrame frame;
    private JPanel panel;
    private MenuBar menu;

    private Manager manager;
    private final Timer ping;

    public Gui(){
        this.ping = new Timer(1000, (ActionEvent e) -> {
            try {
                if(!manager.ping(60)){
                    Object[] options = {"Erneut Verbinden", "Beenden"};
                    int optionResult = JOptionPane.showOptionDialog(frame,
                            "Die Verbindung zur Datenbank wurde verloren.",
                            "Verbindung verloren",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.ERROR_MESSAGE, null, options,
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
            }catch (NullPointerException ex){
            }
        });
        //<editor-fold defaultstate="collapsed" desc="Init frame and panel">
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

        this.menu = new MenuBar(getActionListener(), logout());

        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(null);

        this.panel.setLayout(null);

        this.panel.add(menu);
        this.frame.add(panel);
        this.frame.pack();
//</editor-fold>

        this.manager = new Manager();

        this.ping.setInitialDelay(5000);
        this.ping.start();

        initializeConnection();
        changeActivity(ActivityID.NEW_ORDER);

    }

    private void initializeConnection(){
        boolean connected = false;
        while(!connected){
            try {
                this.manager.newConnection();
                connected = true;
            }catch (SQLException ex){
                Object[] options = {"Erneut Verbinden", "Beenden"};
                int optionResult = JOptionPane.showOptionDialog(frame,
                        "Die Verbindung zur Datenbank wurde verloren.",
                        "Verbindung verloren",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, options,
                        options[1]);
                switch(optionResult){
                    case 0:
                        break;
                    case 1:
                        System.exit(1);
                        break;
                }
            }
        }
    }

    /**
     * For switching to new Activity and adding to activity stack
     */
    private ActionListener getActionListener(ActivityID activity,
            ActivityID oldActivity){

        ActionListener listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(oldActivity != null){
                    manager.addActivityID(oldActivity);
                    changeActivity(activity);
                }else{
                    changeActivity(manager.getLastActivityID());
                }
            }
        };

        return listener;
    }

    /**
     * For returning to the Login Screen, with resetting the stack
     */
    private ActionListener logout(){

        ActionListener listener = (ActionEvent e) -> {
            manager.resetStack();
            changeActivity(ActivityID.LOGIN_SCREEN);
        };

        return listener;
    }

    /**
     * For returning to the Last activity
     */
    private ActionListener getActionListener(){

        ActionListener listener = (ActionEvent e) -> {
            changeActivity(manager.getLastActivityID());
        };

        return listener;
    }

    private void changeActivity(ActivityID activity){
        for(int i = this.panel.getComponentCount() - 1; i >= 0; i--){
            if(!(this.panel.getComponent(i) instanceof MenuBar)){
                this.panel.getComponent(i).setVisible(false);
                this.panel.remove(i);
            }
        }

        //<editor-fold defaultstate="collapsed" desc="MenuBar visibility">
        if(activity == ActivityID.LOGIN_SCREEN){
            this.menu.setVisible(false);
        }else{
            this.menu.setVisible(true);
        }
        //</editor-fold>

        Activity tempActivity = null;
        try {
            switch(activity){
                case HOME_SCREEN_RESOURCE_MANAGER:
                    tempActivity = new HomeScreenResourceManagerActivity(
                            getActionListener(ActivityID.GROCERY_LIST, activity),
                            getActionListener(ActivityID.SHOW_ORDER_SCREEN,
                                    activity),
                            manager.getHomeManagerResourceManager());
                    break;
                case HOME_SCREEN:
                    tempActivity = new HomeScreenActivity(
                            getActionListener(ActivityID.NEW_ORDER, activity),
                            getActionListener(ActivityID.SHOW_ORDER_SCREEN,
                                    activity),
                            manager.getUserName(), manager.getHomeManager());
                    break;
                case GROCERY_LIST:
                    tempActivity = new GroceryListActivity(
                            getActionListener(ActivityID.SHOW_ORDER_SCREEN,
                                    activity),
                            getActionListener(ActivityID.NEW_LIST, activity),
                            manager.getGroceryManager());
                    break;
                case SHOW_ORDER_SCREEN:
                    tempActivity = new ShowOrderActivity(manager.getOrderManager());
                    break;
                case NEW_LIST:
                    tempActivity = new NewListActivity(
                            getActionListener(ActivityID.GROCERY_LIST, null),
                            manager.getNewListManager());
                    break;
                case NEW_ORDER:
                    tempActivity = new NewOrderActivity(
                            manager.getNewOrderManager(),
                            getActionListener(ActivityID.HOME_SCREEN, null));
                    break;
                case LOGIN_SCREEN:
                    LogInManager logInManager = manager.getLogInManager();
                    tempActivity = new LoginScreenActivity(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent ae){
                            if(manager.loginIsValid()){
                                if(manager.getUserIsReosourceManager()){                                          
                                    changeActivity(
                                            ActivityID.HOME_SCREEN_RESOURCE_MANAGER);
                                }else{
                                    changeActivity(ActivityID.HOME_SCREEN);
                                }
                            }
                        }
                    }, logInManager);
                    break;
            }
        }catch (JDOMException ex){

        }catch (IOException ex){

        }catch (SQLException ex){

        }

        if(tempActivity != null){
            this.panel.add(tempActivity);
            this.manager.setCurrentActivity(tempActivity.getActivityManager());
        }

        if(this.manager.isStackEmpty()){
            this.menu.disableReturnButton(this.manager.getUserName());
        }else{
            if(tempActivity != null){
                this.menu.enableReturnButton(tempActivity.getTitle());
            }
        }

        this.frame.repaint();
    }

}
