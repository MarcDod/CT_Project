/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;

/**
 *
 * @author Marc
 */
public class GroceryManager {
    public final static String XML_FILE_PATH = "XML-OrderLists/orderList.xml";
    
    private ArrayList<Orderlist> groceryList;
    private int activeIndex;
    private  XMLManager xmlManager;
    
    public GroceryManager(XMLManager xmlManager) throws JDOMException, IOException{
        this.xmlManager = xmlManager;
        this.groceryList = xmlManager.loadXMLOrderLists(new File(XML_FILE_PATH));
    }
    
    public ArrayList<Orderlist> getGroceryList(){
        return this.groceryList;
    }
    
    public void setActivIndex(int index){
        this.activeIndex = index;
    }
    
    public Orderlist getActiveList(){
        return this.groceryList.get(activeIndex);
    }
    
    public int getActiveIndex(){
        return this.activeIndex;
    }
}
