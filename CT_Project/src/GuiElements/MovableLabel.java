/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Evelyn
 */
public class MovableLabel extends JLabel {

    public static final int TOP_DOWN_MODE = 0;
    public static final int LEFT_RIGHT_MODE = 1;

    private int mode;

    private int oldMousePos;
    private boolean leftButtonPressed;
    private Color colorLeftTop;
    private Color colorRightDown;

    private boolean swipeRightDown;
    private boolean swipeLeftTop;

    private ArrayList<ActionListener> actionLeftTop;
    private ArrayList<ActionListener> actionRightDown;

    private double boundaryLeftTop;
    private double boundaryRightDown;

    private int startPos;

    public MovableLabel(int mode) {
        initMovableLabel(Color.RED, Color.GREEN, mode);
    }

    public MovableLabel(Color left, Color right, int mode) {
        initMovableLabel(left, right, mode);
    }

    private void initMovableLabel(Color left, Color right, int mode) {
        this.leftButtonPressed = false;
        this.colorLeftTop = left;
        this.colorRightDown = right;
        this.swipeRightDown = true;
        this.swipeLeftTop = true;
        this.actionLeftTop = new ArrayList<>();
        this.actionRightDown = new ArrayList<>();
        this.mode = mode;
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                labelDragged(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                labelPressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelReleased(e);
            }
        });
    }

    private void labelDragged(MouseEvent e) {
        if (!this.leftButtonPressed || (!this.swipeLeftTop && !this.swipeRightDown)) {
            return;
        }
        int move = 0;
        int pos = 0;
        switch (mode) {
            case (0):
                move = (e.getY() - oldMousePos);
                pos = this.getY();
                break;
            case (1):
                move = (e.getX() - oldMousePos);
                pos = this.getX();
                break;
            default:
                return;
        }

        if (!this.swipeLeftTop && move < startPos && pos <= startPos || !this.swipeRightDown && move > startPos && pos >= startPos) {
            switch (mode) {
                case (0):
                    this.setLocation(this.getX(), startPos);
                    break;
                case (1):
                    this.setLocation(startPos, this.getY());
                    break;
            }
            return;
        }

        switch (mode) {
            case (0):
                this.setLocation(this.getX(), pos + move);
                break;
            case (1):
                this.setLocation(pos + move, this.getY());
                break;
        }
        if (pos > startPos) {
            this.getParent().setBackground(colorRightDown);
        } else {
            this.getParent().setBackground(colorLeftTop);
        }
    }

    private void labelPressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.leftButtonPressed = true;
            switch (mode) {
                case (0):
                    this.oldMousePos = e.getY();
                    this.startPos = this.getY();
                    break;
                case (1):
                    this.oldMousePos = e.getX();
                    this.startPos = this.getX();
                    break;
            }
        }
    }

    private void labelReleased(MouseEvent e) {
        this.leftButtonPressed = false;
        int pos;
        switch (mode) {
            case (0):
                pos = this.getY();
                break;
            case (1):
                pos = this.getX();
                break;
            default:
                return;
        }
        if (pos > 0) {
            this.getParent().setBackground(colorRightDown);
            if (pos > boundaryRightDown) {
                actionRightDown.forEach((a) -> {
                    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
                });
            } else {
                switch (mode) {
                    case (0):
                        this.setLocation(this.getX(), startPos);
                        break;
                    case (1):
                        this.setLocation(startPos, this.getY());
                        break;
                }
            }
        } else if (pos < 0) {
            this.getParent().setBackground(colorLeftTop);
            if (pos < boundaryLeftTop) {
                actionLeftTop.forEach((a) -> {
                    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
                });
            } else {
                switch (mode) {
                    case (0):
                        this.setLocation(this.getX(), startPos);
                        break;
                    case (1):
                        this.setLocation(startPos, this.getY());
                        break;
                }
            }
        }
    }

    public void enableSwipeLeftTop() {
        this.swipeLeftTop = true;
    }

    public void disableSwipeLeftTop() {
        this.swipeLeftTop = false;
    }

    public void enableSwipeRightDown() {
        this.swipeRightDown = true;
    }

    public void disableSwipeRightDown() {
        this.swipeRightDown = false;
    }

    public void setBoundaryLeftTop(double x) {
        this.boundaryLeftTop = x;
    }

    public void setBoundaryRightDown(double x) {
        this.boundaryRightDown = x;
    }

    public void addActionListenerLeftTop(ActionListener newListenerLeft) {
        this.actionLeftTop.add(newListenerLeft);
    }

    public void removeActionListenerLeftTop(ActionListener newListenerLeft) {
        this.actionLeftTop.remove(newListenerLeft);
    }

    public void removeActionListenerLeftTop(int i) {
        this.actionLeftTop.remove(i);
    }

    public void addActionListenerRightDown(ActionListener newListenerRight) {
        this.actionRightDown.add(newListenerRight);
    }

    public void removeActionListenerRightDown(ActionListener newListenerRight) {
        this.actionRightDown.remove(newListenerRight);
    }

    public void removeActionListenerRightDown(int i) {
        this.actionRightDown.remove(i);
    }
}
