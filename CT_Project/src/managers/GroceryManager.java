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
import ct_project.Gui;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jdom2.JDOMException;

/**
 *
 * @author Marc
 */
public class GroceryManager {

    public final static String XML_FILE_PATH = "XML-OrderLists/orderList.xml";
    public final static String ORDERS_WITHOUT_LIST = "ALLE OHNE LISTE";

    private XMLManager xmlManager;
    
    private ArrayList<Orderlist> groceryList;
    private int activeIndex;
    
    public GroceryManager(ArrayList<Orderlist> grList, ArrayList<Order> allOrders, XMLManager xmlManager, Connector database) throws JDOMException, IOException, SQLException {
        if (database == null) {
            return;
        }
        if (grList == null) {
            grList = new ArrayList<Orderlist>();
        }
        for (Orderlist list : grList) {
            for (int i = list.getOrderIDs().size() - 1; i >= 0; i--) {
                Order order = database.getOrder(list.getOrderIDs().get(i));
                if (order == null || order.isCanceld() || order.isBought()) {
                    list.removeID(i);
                }
            }
        }
        if (grList.size() != 0) {
            if (ORDERS_WITHOUT_LIST.equals(grList.get(0).getName())) {
                grList.remove(0);
            }
        }

        ArrayList<Integer> allOrderIdsWithoutList = new ArrayList<>();
        for (Order order : allOrders) {
            boolean withoutList = true;
            for (Orderlist list : grList) {
                if (order.isCanceld() || order.isBought() || list.getOrderIDs().contains(order.getOrderID())) {
                    withoutList = false;
                }
            }
            if (withoutList) {
                allOrderIdsWithoutList.add(order.getOrderID());
            }
        }

        grList.add(0, new Orderlist(allOrderIdsWithoutList,ORDERS_WITHOUT_LIST, String.format("#%02x%02x%02x", Gui.COLOR.getRed(), Gui.COLOR.getGreen(), Gui.COLOR.getBlue())));
        this.groceryList = grList;
        this.xmlManager = xmlManager;
        xmlManager.saveXMLOrderLists(grList, new File(GroceryManager.XML_FILE_PATH));
    }
    
    public void removeList(int index) throws IOException{
        this.groceryList.remove(index);
        xmlManager.saveXMLOrderLists(this.groceryList, new File(GroceryManager.XML_FILE_PATH));
    }
    
    public ArrayList<Orderlist> getGroceryList() {
        return this.groceryList;
    }

    public void setActivIndex(int index) {
        this.activeIndex = index;
    }

    public Orderlist getActiveList() {
        return this.groceryList.get(activeIndex);
    }

    public String getActiveName() {
        return this.groceryList.get(activeIndex).getName();
    }

    public int getActiveIndex() {
        return this.activeIndex;
    }
}
