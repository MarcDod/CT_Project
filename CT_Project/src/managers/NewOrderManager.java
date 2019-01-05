/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.database.Connector;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author Marc
 */
public class NewOrderManager extends ActivityManager{

    private ArrayList<String> items;
    private Connector database;
    private String userName;
    
    public NewOrderManager(ArrayList<String> items, Connector database, String userName){
        this.items = items;
        this.database = database;
        this.userName = userName;
    }
    
    public ArrayList<String> getItems(){
        return this.items;
    }
    
    public void makeNewOrder(String itemName, String deadLine, int number) throws SQLException, IllegalArgumentException{
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        GregorianCalendar now = new GregorianCalendar(); 
        if(userName.equals("Keine Information")) throw new IllegalArgumentException("Bitte Melden sie sich an");
        this.database.addOrder(new java.sql.Date(now.getTime().getTime()), deadLine, number, false, itemName, false, userName, false);
    }
    
    public void addItemName(String itemName) throws SQLException, NullPointerException{
        this.database.addItem(itemName, 0);
    }
    
    @Override
    public String getTitle() {
        return "";
    }
    
}
