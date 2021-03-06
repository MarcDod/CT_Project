/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import ct_project.Gui;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jdom2.JDOMException;

/**
 *
 * @author Marc
 */
public class GroceryManager extends ActivityManager implements ShowsOrders{

    public final static String XML_FILE_PATH = "XML-OrderLists/orderList.xml";
    public final static String ORDERS_WITHOUT_LIST = "ALLE BESTELLUNGEN OHNE LISTE";

    private XMLManager xmlManager;
    
    private ArrayList<Orderlist> groceryList;
    private int activeIndex;
    
    public GroceryManager(ArrayList<Orderlist> grList, ArrayList<Order> allOpenOrders, XMLManager xmlManager) throws JDOMException, IOException, SQLException {
        
        // Filter liste nach Oders welche nicht mehr in die liste gehören
        grList.forEach((list) -> {
            for (int i = list.getOrderIDs().size() - 1; i >= 0; i--) {
                boolean valid = false;
                for(Order o : allOpenOrders){
                    if(o.getOrderID() == list.getOrderIDs().get(i))
                        valid = true;
                }
                if(!valid)
                    list.getOrderIDs().remove(i);
            }
        });
        
        if (!grList.isEmpty()) {
            if (ORDERS_WITHOUT_LIST.equals(grList.get(0).getName())) {
                grList.remove(0);
            }
        }

        // bekomme alle bestellungen ohne liste
        ArrayList<Integer> allOrderIdsWithoutList = new ArrayList<>();
        allOpenOrders.forEach((order) -> {
            boolean withoutList = true;
            for (Orderlist list : grList) {
                if (list.getOrderIDs().contains(order.getOrderID())) {
                    withoutList = false;
                }
            }
            if (withoutList) {
                allOrderIdsWithoutList.add(order.getOrderID());
            }
        });

        grList.add(0, new Orderlist(allOrderIdsWithoutList,ORDERS_WITHOUT_LIST, String.format("#%02x%02x%02x", Gui.COLOR.getRed(), Gui.COLOR.getGreen(), Gui.COLOR.getBlue())));
        this.groceryList = grList;
        this.xmlManager = xmlManager;
        xmlManager.saveXMLOrderLists(grList, new File(GroceryManager.XML_FILE_PATH));
    }
    
    public void removeList(int index) throws IOException{
        this.groceryList.get(index).getOrderIDs().forEach((id) -> {
            this.groceryList.get(0).getOrderIDs().add(id);
        });
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

    public int getActiveIndex() {
        return this.activeIndex;
    }

    @Override
    public String getTitle() {
        return this.groceryList.get(activeIndex).getName();
    }

    @Override
    public boolean[] getSwipeAllowed() {
        return new boolean[]{true, true};
    }

    @Override
    public OrderManager.Action[] getSwipeActions() {
        OrderManager.Action[] temp = new OrderManager.Action[2];
        switch(getTitle()){
            case(ORDERS_WITHOUT_LIST):
                temp[0] = OrderManager.Action.SET_CANCEL_TRUE;
                temp[1] = OrderManager.Action.SET_BOUGHT_TRUE;
                break;
            default:
                temp[0] = OrderManager.Action.NOTHING;
                temp[1] = OrderManager.Action.SET_BOUGHT_TRUE;
                break;
        }
        return temp;
    }
}
