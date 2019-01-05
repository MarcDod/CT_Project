/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class NewOrderManager extends ActivityManager{

    private ArrayList<String> items;
    
    public NewOrderManager(ArrayList<String> items){
        this.items = items;
    }
    
    public ArrayList<String> getItems(){
        return this.items;
    }
    
    public void makeNewOrder(String itemName, String deadLine){
        
    }
    
    @Override
    public String getTitle() {
        return "";
    }
    
}
