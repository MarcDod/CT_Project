/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;

/**
 *
 * @author Marc
 */
public class OrderManager {
    private List list;
    private Manager manager;
    
    public OrderManager(List list, Manager manage){
        this.list = list;
        this.manager = manager;
    }
    
    public String[] getOrder(){
        String[] temp = new String[3];
        
        
        return null;
    }
    
    public int getSize(){
        return this.list.getOrderIDs().length;
    }
    
}
