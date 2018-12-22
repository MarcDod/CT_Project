/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.Account;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import DataManagement.database.Connector;
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
        if(!this.activities.isEmpty())
            return this.activities.pop();
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
    public OrderManager getOrderManager() throws JDOMException, IOException{
        return new OrderManager(getGroceryList(), this.database, getGroceryIndex());
    }
    public GroceryManager getGroceryManager() throws JDOMException, IOException{
        return new GroceryManager(getGroceryList());
    }
    public HomeManager getHomeManager() throws JDOMException, IOException{
        return new HomeManager(getGroceryList());
    }
    
    private ArrayList<Orderlist> getGroceryList() throws JDOMException, IOException{
        File temp = new File(GroceryManager.XML_FILE_PATH);
        return (temp.canRead()) ? xmlManager.loadXMLOrderLists(temp) : null;
    }
    
    private int getGroceryIndex(){
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
