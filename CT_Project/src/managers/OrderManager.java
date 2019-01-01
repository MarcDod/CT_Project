/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import DataManagement.database.Connector;
import GuiElements.MovableLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class OrderManager extends ActivityManager{   
    private ArrayList<Order> orders;
    private ArrayList<Orderlist> orderList; 
    private ArrayList<MovableLabel> orderLabels;
    private String name;
    private XMLManager xmlManager;
    private Connector database;
    private int index;
    
    private ActionListener[] swipeListener;
    private boolean[] swipe;
    
    public enum Action{
      SET_BOUGHT_TRUE, SET_BOUGHT_FALSE, SET_CANCEL_TRUE, SET_CANCEL_FALSE, SET_WATCHED_TRUE, NOTHING;  
    }
    
    private Action left;
    private Action right;
    
    public OrderManager(ArrayList<Order> orders,int index ,
            ArrayList<Orderlist> orderList,String name, 
            XMLManager xmlManager, Connector database, Action[] action, boolean[] swipe){
        this.orders = orders;
        this.orderList = orderList;
        this.orderLabels = new ArrayList<>();
        this.name = name;
        this.xmlManager = xmlManager;
        this.index = index;
        this.database = database;
        this.swipe = swipe;
        this.left = action[0];
        this.right = action[1];
    }
    
    public boolean swipeLeftAllowed(){
        return swipe[0];
    }
    
    public boolean swipeRightAllowed(){
        return swipe[1];
    }
    
    public ActionListener getLeftSwipeListener(){
        return swipeListener[0];
    }
    
    public ActionListener getRightSwipeListener(){
        return swipeListener[1];
    }
    
    public int getOrderIDWithActionEvent(ActionEvent ae){
        int i = this.orderLabels.indexOf(getOrderLabel(ae.getSource()));
        return this.orders.get(i).getOrderID();
    }
    
    public MovableLabel getOrderLabel(int i){
        return this.orderLabels.get(i);
    }
    
    public MovableLabel getOrderLabel(Object o){
        for(MovableLabel m : orderLabels){
            if(m.equals(o)){
                return m;
            }
        }
        return null;
    }
    
    public void removeOrderWithIndex(int i){
        this.orders.remove(i);
    }
    
    public void addOrderLabel(MovableLabel label){
        this.orderLabels.add(label);
    }
    
    public int getListSize(){
        return this.orders.size();
    }
    
    public int getOrderLabelSize(){
        return this.orderLabels.size();
    }
    
    public Order getOrder(int i){
        return this.orders.get(i);
    }
    
    public void removeOrder(MovableLabel label) throws IOException{
        int i = this.orderLabels.indexOf(label);
        this.orderLabels.remove(label);
        this.removeOrderWithIndex(i);
        if(index == Integer.MAX_VALUE) return;
        this.orderList.get(index).getOrderIDs().remove(i);
        
        this.xmlManager.saveXMLOrderLists(orderList, new File(GroceryManager.XML_FILE_PATH));
    }

    private void setOrderBought(boolean bought, int orderID) throws SQLException{
        this.database.setOrderBought(orderID, bought);
    }
    
    private void setOrderWatched(boolean watched, int orderID) throws SQLException{
        this.database.setOrderWatched(orderID, watched);
    }
    
    private void setOrdercanceled(boolean canceled, int orderID) throws SQLException{
        this.database.setOrderCanceled(orderID, canceled);
    }
    
    public void swipeLeft(ActionEvent e) throws SQLException{
        int orderID = this.orders.get(this.orderLabels.indexOf(getOrderLabel(e.getSource()))).getOrderID(); 
        doEvent(left, orderID);
    }
  
    public void swipeRight(ActionEvent e) throws SQLException{
        int orderID = this.orders.get(this.orderLabels.indexOf(getOrderLabel(e.getSource()))).getOrderID();
        doEvent(right, orderID);
    }
    
    private void doEvent(Action action, int orderID) throws SQLException{
        switch(action){
            case SET_BOUGHT_FALSE:
                setOrderBought(false, orderID);
                setOrderWatched(true, orderID);
                break;
            case SET_BOUGHT_TRUE:
                setOrderBought(true, orderID);
                setOrderWatched(true, orderID);
                break;
            case SET_CANCEL_TRUE:
                setOrdercanceled(true, orderID);
                setOrderWatched(true, orderID);
                setOrderBought(false, orderID);
                break;
            case SET_CANCEL_FALSE:
                setOrdercanceled(false, orderID);
                setOrderWatched(true, orderID);
                break;
            case SET_WATCHED_TRUE:
                setOrderWatched(true, orderID);
                break; 
            default:
                setOrderWatched(true, orderID);
                break;
        }
        
    }
    
    public void resetOrderLabel() {
        this.orderLabels = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return this.name;
    }
}
