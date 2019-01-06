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
public class HomeManagerUser extends HomeManager implements ShowsOrders{

    
    private ArrayList<Order> myOrders;
    
    public HomeManagerUser(ArrayList myOrders){
        this.myOrders = myOrders;
    }
    
    public int getSizeOfMyOrder(){
        return myOrders.size();
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
