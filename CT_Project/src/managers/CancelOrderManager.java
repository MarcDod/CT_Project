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
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class CancelOrderManager extends OrderManager{
    
    public CancelOrderManager(ArrayList<Order> orders, int index, ArrayList<Orderlist> orderList, Connector database, String name, XMLManager xmlManager) {
        super(orders, index, orderList, database, name, xmlManager);
    }

    @Override
    public void leftSwipe(MovableLabel label) {
        System.out.println("h");
    }
    
    
}
