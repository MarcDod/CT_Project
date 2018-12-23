/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class NewListManager {
    private XMLManager xmlManager;
    private ArrayList<Orderlist> orderList;
    
    public NewListManager(XMLManager xmlManager, ArrayList orderList){
        this.xmlManager = xmlManager;
        this.orderList = orderList;
    }
    
    public void saveOrderList(Color color, String listName) throws IOException{
        ArrayList<Integer> temp = new ArrayList<>();
        Orderlist newList = new Orderlist(temp, listName.toUpperCase());
        this.orderList.add(newList);
        this.xmlManager.saveXMLOrderLists(orderList, new File(GroceryManager.XML_FILE_PATH));
    }
    
}
