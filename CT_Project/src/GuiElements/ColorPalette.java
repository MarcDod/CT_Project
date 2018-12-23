/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import ct_project.Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Marc
 */
public class ColorPalette extends JLabel {

    public static final int BLOB_RADIUS = 40;
    public static final int GAP_BETWEEN_BLOBS = 20;

    private Button[][] colorButtons;
    private int width;
    private int height;
    
    
    
    private Color activeColor;

    private Button unFold;
    private boolean unFolded;
    
    private JPanel panel;

    public ColorPalette(Color startColor, ArrayList<Color> colors, int x, int y, int width, int height) {
        init(colors, x, y, width, height, startColor);
        this.unFold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                action();
            }
        });
    }
    
    public ColorPalette(Color startColor, ArrayList<Color> colors, int x, int y, int width, int height, ActionListener unFoldListener) {
        init(colors, x, y, width, height, startColor);
        this.unFold.addActionListener(unFoldListener);
        this.unFold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                action();
            }
        });
    }

    private void init(ArrayList<Color> colors, int x, int y, int width, int height, Color startColor){
                this.width = width;
        this.height = height;
        this.unFolded = false;
        this.activeColor = startColor;
        this.setLocation(x, y);
        this.setSize(width, height);

        this.unFold = new Button(20, 10, getOpenImage(20, 10, unFolded));
        this.unFold.setLocation(width / 2 - unFold.getWidth() / 2, height - unFold.getHeight() - 5);
        this.unFold.setBorder(null);
        this.add(unFold);
        
        this.panel = new JPanel(null);
        int panelWitdh = (int) (this.width * 0.8);
        double maxColorInOneRow = panelWitdh / (BLOB_RADIUS+ GAP_BETWEEN_BLOBS);
        int col = (int)Math.ceil(maxColorInOneRow / colors.size());
        col++;
        this.panel.setSize((int)maxColorInOneRow * (BLOB_RADIUS + GAP_BETWEEN_BLOBS) - GAP_BETWEEN_BLOBS, col * (BLOB_RADIUS + GAP_BETWEEN_BLOBS));
        int z = 0;
        for(int i = 0; i < col; i++){
            for(int j = 0; j < maxColorInOneRow; j++){
                Button temp = new Button(BLOB_RADIUS, BLOB_RADIUS, getColorImage(colors.get(z)));
                temp.setLocation(j * (BLOB_RADIUS + GAP_BETWEEN_BLOBS), i * (BLOB_RADIUS + GAP_BETWEEN_BLOBS));
                temp.setBorder(null);
                final int colorIndex = z; 
                temp.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        setColor(colors.get(colorIndex));
                    }
                });
                z++;
                panel.add(temp);
                if(z >= colors.size()) break;
            }
            if(z >= colors.size()) break;
        
        }
        this.panel.setLocation((this.getWidth() - panel.getWidth()) / 2, this.unFold.getY() +  10);
        panel.setBackground(new Color(0,0,0,0));
        panel.setVisible(unFolded);
        this.add(panel);
    }
    
    private void setColor(Color color){
        this.activeColor = color;
        this.repaint();
    }
    
    private BufferedImage getColorImage(Color color) {
        BufferedImage image = new BufferedImage(BLOB_RADIUS, BLOB_RADIUS,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, BLOB_RADIUS, BLOB_RADIUS);
        
        g2d.setColor(color);
        g2d.fillOval(0, 0, BLOB_RADIUS, BLOB_RADIUS);
        
        g2d.dispose();

        return image;
    }

    private void drawBasicPalette(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2d.setColor(this.activeColor);
        g2d.fillRect(this.getWidth() - 20, 0, 20, this.getHeight());
        
        g2d.setFont(Gui.BUTTON_FONT);
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString("Farbe", 16, 5 + fm.getHeight());
    }

    private void drawEdge(Graphics2D g2d) {
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }

    private BufferedImage getOpenImage(int width, int height, boolean up) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);

        g2d.setStroke(new BasicStroke(2.0f));
        int y1 = (up) ? height - 1 : 0;
        int y2 = (!up) ? height - 1 : 0;
        int y3 = (up) ? height - 1 : 0;
        g2d.drawPolyline(new int[]{0, width / 2, width - 1}, new int[]{y1, y2, y3}, 3);

        g2d.dispose();

        return image;
    }

    public Icon getIcon() {
        BufferedImage image = new BufferedImage(width, this.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        drawBasicPalette(g2d);

        drawEdge(g2d);

        g2d.dispose();

        return new ImageIcon(image);
    }

    private void action() {
        unFolded = !unFolded;
        if (unFolded) {
            this.setSize(width, height + panel.getHeight());
        } else {
            this.setSize(width, height);
        }
        this.setIcon(getIcon());
        this.unFold.setLocation(width / 2 - unFold.getWidth() / 2, this.getHeight() - unFold.getHeight() - 5);
        this.unFold.setImage(getOpenImage(unFold.getWidth(), unFold.getHeight(), unFolded));
        this.panel.setVisible(unFolded);
    }
    
    public Color getColor(){
        return this.activeColor;
    }
}
