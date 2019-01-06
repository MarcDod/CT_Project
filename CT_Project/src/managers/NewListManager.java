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
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class NewListManager extends ActivityManager{
    private XMLManager xmlManager;
    private ArrayList<Orderlist> orderList;
    private ArrayList<Order> allOrders;
    
    private int index;
    
    private String title;
    
    public NewListManager(XMLManager xmlManager, Connector database ,ArrayList orderList, String title, int index) throws SQLException{
        this.xmlManager = xmlManager;
        this.orderList = orderList;
        this.allOrders = new ArrayList<>();
        this.title = (title.equals(GroceryManager.ORDERS_WITHOUT_LIST))? "NEUE LISTE": title;
        this.index = index;
        
        for(int i = 0; i < this.orderList.get(0).getOrderIDs().size(); i++){
            this.allOrders.add(database.getOrder(this.orderList.get(0).getOrderIDs().get(i)));
        }
        if(index == 0) return;
        
        for(int i = 0; i < this.orderList.get(index).getOrderIDs().size(); i++){
            this.allOrders.add(database.getOrder(this.orderList.get(index).getOrderIDs().get(i)));
        }
        
    }
    
    @Override
    public String getTitle(){
        return this.title;
    }
    
    public ArrayList<Integer> getOrderlist(){
        ArrayList<Integer> ret = new ArrayList<>();
        if(index != 0)
            ret = orderList.get(index).getOrderIDs();
        return ret;
    }
    
    public Color getColor(){
        return Color.decode(orderList.get(index).getColor());
    }
    
    public String getListName(){
        if(index == 0) return "";
        return orderList.get(index).getName();
    }
    
    public ArrayList getAllOrders(){
        return this.allOrders;
    }
    
    public void saveOrderList(Color color, String listName, ArrayList orders) throws IOException{
        String name = listName.toUpperCase();
        if(name.equals(GroceryManager.ORDERS_WITHOUT_LIST)) name += "!";
        Orderlist newList = new Orderlist(orders,name, String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
        if(index == 0)
            this.orderList.add(newList);
        else{
            this.orderList.remove(index);
            this.orderList.add(index, newList);
        }
        this.xmlManager.saveXMLOrderLists(orderList, new File(GroceryManager.XML_FILE_PATH));
    }
    
}
