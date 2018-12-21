/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;
import DataManagement.XML.XMLManager;

/**
 *
 * @author Marc
 */
public class GroceryManager {
    private List[] groceryList;
    private int activeIndex;
    
    private  XMLManager xmlManager;
    
    public GroceryManager(XMLManager xmlManager){
        this.xmlManager = xmlManager;
        this.groceryList = new List[]{new List("TEST LISTE", new int[]{1})};
    }
    
    public List[] getGroceryList(){
        return this.groceryList;
    }
    
    public void setActivIndex(int index){
        this.activeIndex = index;
    }
    
    public List getActiveList(){
        return this.groceryList[activeIndex];
    }
    
}
