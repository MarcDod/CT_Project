package ct_project;

import GuiElements.MenuBar;
import activities.ActivityID;
import activities.GroceryList;
import activities.HomeScreen;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
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
        this.menu = new MenuBar();

        this.frame.setLayout(null);

        this.panel.add(menu);
        this.frame.add(panel);
        this.panel.setLayout(null);
        this.frame.pack();

        changeActivity(ActivityID.HOME_SCREEN);
    }

    private ActionListener getActionListener(ActivityID activity) {

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeActivity(activity);
            }
        };

        return listener;
    }

    public void changeActivity(ActivityID activity) {
        for (int i = 0; i < this.panel.getComponentCount(); i++) {
            if (!(this.panel.getComponent(i) instanceof MenuBar)) {
                this.panel.getComponent(i).setVisible(false);
                this.panel.remove(i);
                i--;
            }
        }
        
        switch (activity) {
            case HOME_SCREEN:
                this.panel.add(new HomeScreen(ActivityID.HOME_SCREEN, getActionListener(ActivityID.GROCERY_LIST)));
                break;
            case GROCERY_LIST:
                this.panel.add(new GroceryList(ActivityID.GROCERY_LIST));
                break;

        }
        
        this.frame.repaint();
    }
    
}
