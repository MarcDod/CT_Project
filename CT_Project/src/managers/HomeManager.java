/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.database.Connector;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class HomeManager extends ActivityManager{
    private ArrayList<Orderlist> groceryList;
    
    private String buttonName;
    
    private int notWatchedOrder;
    
    public HomeManager(ArrayList<Orderlist> groceryList, ArrayList<Order> allOrder) throws SQLException{
        this.groceryList = groceryList;
        this.notWatchedOrder = 0;
        allOrder.stream().filter((order) -> (!order.isWatched())).forEachOrdered((_item) -> {
            notWatchedOrder++;
        });
    }
   
    public int getGrocerySize(){
        return (groceryList != null) ? this.groceryList.size() : 0;
    }
    
    public void setButtonName(String buttonName){
        this.buttonName = buttonName;
    }

    public int getNotWatchedOrdes() {
        return this.notWatchedOrder;
    }

    @Override
    public String getTitle() {
        return this.buttonName;
    }
}
