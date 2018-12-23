/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import managers.LogInManager;
import managers.HomeManager;
import managers.GroceryManager;
import DataManagement.Datatemplates.Account;
import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import DataManagement.database.Connector;
import GuiElements.activities.Activity;
import GuiElements.activities.ActivityID;
import GuiElements.activities.GroceryList;
import GuiElements.activities.HomeScreen;
import GuiElements.activities.LoginScreen;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;
import org.jdom2.JDOMException;

/**
 *
 * @author Marc
 */
public class Manager {
    
    private Stack<ActivityID> activities;
    private Connector database;
    private XMLManager xmlManager;
    private Activity currentActivity;
    
    private Account user;
    public Manager(){
        this.activities = new Stack<>();
        this.user = null;
        this.xmlManager = new XMLManager(); 
    }
    
    public void newConnection() throws SQLException{
        this.database = new Connector();
    }
    
    public ActivityID getLastActivityID(){
        if(!this.activities.isEmpty()){
            if(this.activities.peek() == currentActivity.getActivityID())
                this.activities.pop();
            return this.activities.pop();
        }
        return null;
    }
    
    public void addActivityID(ActivityID id){
        this.activities.push(id);
    }
    
    public boolean isEmpty(){
        return this.activities.isEmpty();
    }
    
    public void resetStack(){
        this.activities.clear();
    }
    
    public LogInManager getLogInManager(){
        return new LogInManager(this.database);
    }
    public OrderManager getOrderManager() throws JDOMException, IOException, SQLException{
        return new OrderManager(getOrder(), this.database, getOrderName());
    }
    public GroceryManager getGroceryManager() throws JDOMException, IOException, SQLException{
        return new GroceryManager(getGroceryList(), getAllOrder(), xmlManager, database);
    }
    public HomeManager getHomeManager() throws JDOMException, IOException{
        return new HomeManager(getGroceryList());
    }
    public NewListManager getNewListManager() throws JDOMException, IOException{
        return new NewListManager(xmlManager, getGroceryList());
    }
    
    private ArrayList<Order> getOrder() throws JDOMException, IOException, SQLException{
        ArrayList<Order> tempOrders = new ArrayList<>();

        if(currentActivity instanceof GroceryList){
            ArrayList<Orderlist> tempOrderlist = getGroceryList();
            for(Integer id : tempOrderlist.get(getOrderIndex()).getOrderIDs()){
                tempOrders.add(this.database.getOrder(id));
            }
        } if(currentActivity instanceof HomeScreen){
            tempOrders = this.database.getAllOrders();
        }
        
        return tempOrders;
    }
    
    private ArrayList<Orderlist> getGroceryList() throws JDOMException, IOException{
        File temp = new File(GroceryManager.XML_FILE_PATH);
        return (temp.canRead()) ? xmlManager.loadXMLOrderLists(temp) : null;
    }
    
    private ArrayList<Order> getAllOrder() throws SQLException{
        if(database != null)
            return database.getAllOrders();
        return null;
    }
    
    private String getOrderName(){
        if(this.currentActivity instanceof GroceryList){
            return ((GroceryList) currentActivity).getCurrentName();
        }if(this.currentActivity instanceof HomeScreen){
            return "ALLE BESTELLUNGEN";
        }
        return "";
    }

    private int getOrderIndex(){
        if(this.currentActivity instanceof GroceryList){
            return ((GroceryList) currentActivity).getCurrentIndex();
        }
        return Integer.MAX_VALUE;
    }
    
    public boolean loginIsValid(){
        if(this.currentActivity instanceof LoginScreen){
            this.user = ((LoginScreen) currentActivity).getUser();
            return (user != null);
        }    
        return false;
    }
    
    public void setCurrentActivity(Activity tempActivity){
        this.currentActivity = tempActivity;
    }
    
    public boolean ping(int timeout) throws SQLException{
        return database.ping(timeout);
    }

    public void reconnect() throws SQLException{
        this.database.reconnect();
    }
    
    public String getUserName(){
        return (this.user != null)? this.user.getName() : "Keine Information";
    }
}
