/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;
import GuiElements.MovableLabel;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class OrderManager {
    private List list;
    private Manager manager;
    private ArrayList<MovableLabel> orders;
    
    public OrderManager(List list, Manager manage){
        this.list = list;
        this.manager = manager;
        orders = new ArrayList<>();
    }
    
    public MovableLabel getOrder(int i){
        return this.orders.get(i);
    }
    
    public MovableLabel getOrder(Object o){
        for(MovableLabel m : orders){
            if(m.equals(o)){
                return m;
            }
        }
        return null;
    }
    
    public void addOrder(MovableLabel label){
        this.orders.add(label);
    }
    
    public int getListSize(){
        return this.list.getOrderIDs().length;
    }
    
    public int getOrderSize(){
        return this.orders.size();
    }
    
    public void removeOrder(MovableLabel label){
        this.orders.remove(label);
    }
    
    public void listUpdate(int i){

    }
    
}
