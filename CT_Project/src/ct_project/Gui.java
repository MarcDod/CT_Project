package ct_project;

import GuiElements.MenuBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
    
    public Gui(){
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
    }
}
