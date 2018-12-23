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
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class OrderManager {
    private ArrayList<Order> orders;
    private ArrayList<Orderlist> orderList; 
    private Connector connector;
    private ArrayList<MovableLabel> orderLabels;
    private String name;
    private XMLManager xmlManager;
    private int index;
    
    public OrderManager(ArrayList<Order> orders,int index ,ArrayList<Orderlist> orderList, Connector connector, String name, XMLManager xmlManager){
        this.orders = orders;
        this.orderList = orderList;
        this.connector = connector;
        this.orderLabels = new ArrayList<>();
        this.name = name;
        this.xmlManager = xmlManager;
        this.index = index;
    }
    
    public Order getOrder(int index) throws SQLException, NullPointerException{
        return orders.get(index);
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
    
    public void removeOrderWithID(int i){
        this.orders.remove(i);
    }
    
    public String getListName(){
        return this.name;
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
    
    public void removeOrder(MovableLabel label) throws IOException{
        int i = this.orderLabels.indexOf(label);
        this.orderLabels.remove(label);
        this.removeOrderWithID(i);
        if(index == Integer.MAX_VALUE) return;
        this.orderList.get(index).getOrderIDs().remove(i);
        
        this.xmlManager.saveXMLOrderLists(orderList, new File(GroceryManager.XML_FILE_PATH));
    }
 

    public void orderAccept(MovableLabel label){
        
    }

    public void resetOrderLabel() {
        this.orderLabels = new ArrayList<>();
    }
    
}
