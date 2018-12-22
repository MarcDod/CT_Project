/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.Orderlist;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class HomeManager {
    private ArrayList<Orderlist> groceryList;
    
    public HomeManager(ArrayList<Orderlist> groceryList){
        this.groceryList = groceryList;
    }
    
    public int getGrocerySize(){
        return (groceryList != null) ? this.groceryList.size() : 0;
    }
}
