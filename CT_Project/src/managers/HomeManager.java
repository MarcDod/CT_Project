/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Order;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class HomeManager extends ActivityManager implements ShowsOrders{

    private String buttonName;
    private ArrayList<Order> myOrders;
    
    public HomeManager(ArrayList myOrders){
        this.buttonName = "";
        this.myOrders = myOrders;
    }
    
    public int getSizeOfMyOrder(){
        if(myOrders == null) return 0;
        return myOrders.size();
    }
    
    public void setButtonName(String buttonName){
        this.buttonName = buttonName;
    }
    
    @Override
    public String getTitle() {
        return buttonName;
    }

    @Override
    public boolean[] getSwipeAllowed() {
        return new boolean[]{false, false};
    }

    @Override
    public OrderManager.Action[] getSwipeActions() {
        return new OrderManager.Action[]{OrderManager.Action.NOTHING, OrderManager.Action.NOTHING};
    }
    
}
