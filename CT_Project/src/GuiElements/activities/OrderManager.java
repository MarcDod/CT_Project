/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.database.Connector;
import GuiElements.MovableLabel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class OrderManager {
    private ArrayList<Orderlist> list;
    private int index;
    private Connector connector;
    private ArrayList<MovableLabel> orderLabels;
    
    public OrderManager(ArrayList<Orderlist> list, Connector connector, int index){
        this.index = index;
        this.list = list;
        this.connector = connector;
        orderLabels = new ArrayList<>();
    }
    
    public Order getOrder(int index) throws SQLException{
        return this.connector.getOrder(this.list.get(this.index).getOrderIDs().get(index));
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
    
    public void removerIDFromOrder(int i){
        this.list.get(this.index).getOrderIDs().remove(i);
    }
    
    public String getListName(){
        return this.list.get(this.index).getName();
    }
    
    public void addOrderLabel(MovableLabel label){
        this.orderLabels.add(label);
    }
    
    public int getListSize(){
        return this.list.get(this.index).getOrderIDs().size();
    }
    
    public int getOrderLabelSize(){
        return this.orderLabels.size();
    }
    
    public void removeOrder(MovableLabel label){
        
    }
 
    public void listUpdate(int i){

    }
    
}
