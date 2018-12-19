/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.Datatemplates;

/**
 *
 * @author Marc
 */
public class List {
    private String name;
    private int[] orderIDs;
    
    public List(String name, int[] orderIDs){
        this.name = name;
        this.orderIDs = orderIDs;
    }
    
    public String getName(){
        return name;
    }

    public int[] getOrderIDs() {
        return orderIDs;
    }
}
