/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import ct_project.Gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marc
 */
public class ItemSelector extends Selector {

    public ItemSelector(int width, int height, ArrayList<String> allElements, ArrayList<String> selectedElements) {
        super(width, height, allElements, new JPanel(), selectedElements, 1);

        this.setBorder(null);

        initTextLabels();
    }

    private void initTextLabels() {
        for (int i = 0; i < allElements.size(); i++) {
            this.labels.get(i).setText(String.valueOf(allElements.get(i)));
            this.labels.get(i).setHorizontalAlignment(JLabel.CENTER);
            this.labels.get(i).setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            this.labels.get(i).setFont(new Font(Gui.BUTTON_FONT.getName(), Font.PLAIN, 30));
        }
    }

    @Override
    protected void orderUpdate(MouseEvent e) {
        super.orderUpdate(e);
        if (!selectedElements.isEmpty()) {
            int i = allElements.indexOf(selectedElements.get(0));
            refreshUpdate(i);
        } else {
            this.labels.forEach((label) -> {
                label.setOpaque(false);
            });
        }
    }

    @Override
    protected void refreshUpdate(int i) {
        this.labels.get(i).setOpaque(true);
        this.labels.get(i).setBackground(selctedColor);
        this.labels.forEach((label) -> {
            if (!this.labels.get(i).equals(label)) {
                label.setOpaque(false);
            }
        });

    }

    @Override
    public void addObject(Object object, boolean top) {
        super.addObject(object, top);
        initTextLabels();
    }

    @Override
    Icon getIcon(int width, int height, int i) {
        return null;
    }

}
