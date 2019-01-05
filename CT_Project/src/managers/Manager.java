/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Account;
import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import DataManagement.XML.XMLManager;
import DataManagement.database.Connector;
import GuiElements.activities.ActivityID;
import GuiElements.activities.HomeScreenActivity;
import GuiElements.activities.HomeScreenResourceManagerActivity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;
import managers.OrderManager.Action;
import org.jdom2.JDOMException;

/**
 *
 * @author Marc
 */
public class Manager {

    private Stack<ActivityID> activities;
    private Connector database;
    private XMLManager xmlManager;
    private ActivityManager currentActivityManager;

    private Account user;

    public Manager() {
        this.activities = new Stack<>();
        this.user = null;
        this.xmlManager = new XMLManager();
    }

    public void newConnection() throws SQLException {
        this.database = new Connector();
    }

    public boolean getUserIsReosourceManager(){
        return (user.getGroup() == 0);
    }
    
    public ActivityID getLastActivityID() {
        if (!this.activities.isEmpty()) {
            if (this.activities.peek() == currentActivityManager.getActivityID()) {
                this.activities.pop();
            }
            return this.activities.pop();
        }
        return null;
    }

    public void addActivityID(ActivityID id) {
        this.activities.push(id);
    }

    public boolean isStackEmpty() {
        return this.activities.isEmpty();
    }

    public void resetStack() {
        this.activities.clear();
    }

    public LogInManager getLogInManager() {
        return new LogInManager(this.database);
    }

    public OrderManager getOrderManager() throws JDOMException, IOException, SQLException {
        return new OrderManager(getOrder(), getOrderIndex(), getGroceryList(), getActivityName(),this.xmlManager, this.database, getOrderSwipeAction(), getSwipeAllowed());
    }

    public GroceryManager getGroceryManager() throws JDOMException, IOException, SQLException {
        return new GroceryManager(getGroceryList(), getAllValidOrders(this.database.getAllOrders()), xmlManager);
    }

    public HomeManagerResourceManager getHomeManagerResourceManager() throws JDOMException, IOException, SQLException {
        return new HomeManagerResourceManager(getGroceryList(), getAllValidOrders(this.database.getAllOrders()));
    }

    public HomeManager getHomeManager() throws SQLException{
       
        return new HomeManager(getAllValidOrders(getMyOrders()));
    }
    
    public NewOrderManager getNewOrderManager() throws SQLException{
        return new NewOrderManager(getAllItems(), database, getUserName());
    }
    
    public NewListManager getNewListManager() throws JDOMException, IOException, SQLException {
        return new NewListManager(xmlManager, database, getGroceryList(), getActivityName(), getOrderIndex());
    }

    private ArrayList<Order> getAllValidOrders(ArrayList<Order> orders) throws SQLException {
        ArrayList<Order> temp = orders;

        for (int i = temp.size() - 1; i >= 0; i--) {
            Order o = temp.get(i);
            if (o.isBought() || o.isCanceld()) {
                temp.remove(o);
            }
        }

        return temp;
    }

    private ArrayList<Order> getAllBoughtOrders(ArrayList<Order> orders) throws SQLException {
        ArrayList<Order> temp = orders;

        for (int i = temp.size() - 1; i >= 0; i--) {
            Order o = temp.get(i);
            if (!o.isBought()) {
                temp.remove(o);
            }
        }

        return temp;
    }

    private ArrayList<Order> getAllNewOrders() throws SQLException {
        ArrayList<Order> temp = getAllValidOrders(this.database.getAllOrders());

        for (int i = temp.size() - 1; i >= 0; i--) {
            Order o = temp.get(i);
            if (o.isWatched()) {
                temp.remove(o);
            }
        }

        return temp;
    }

    private ArrayList<Order> getAllCanceldOrders(ArrayList<Order> orders) throws SQLException {
        ArrayList<Order> temp = orders;

        for (int i = temp.size() - 1; i >= 0; i--) {
            Order o = temp.get(i);
            if (!o.isCanceld()) {
                temp.remove(o);
            }
        }

        return temp;
    }

    private ArrayList<Order> getMyOrders() throws SQLException{
        ArrayList<Order> temp;
        if(this.user == null)
            temp = new ArrayList<>();
        else
            temp = this.database.getOrdersFromUser(user.getName());
        return temp;
    }
    
    private ArrayList<Order> getOrder() throws JDOMException, IOException, SQLException {
        if(this.database == null) return null;
        ArrayList<Order> tempOrders = new ArrayList<>();

        if (currentActivityManager instanceof GroceryManager) {
            ArrayList<Orderlist> tempOrderlist = getGroceryList();
            int index = (getOrderIndex() == Integer.MAX_VALUE) ? 0 : getOrderIndex();
            for (Integer id : tempOrderlist.get(index).getOrderIDs()) {
                tempOrders.add(this.database.getOrder(id));
            }
        }else if (currentActivityManager instanceof NewListManager) {
            ArrayList<Orderlist> tempOrderlist = getGroceryList();
            for (Integer id : tempOrderlist.get(0).getOrderIDs()) {
                tempOrders.add(this.database.getOrder(id));
            }
        }else if (currentActivityManager instanceof HomeManagerResourceManager || currentActivityManager instanceof HomeManager) {
            String activeName = getActivityName();
            switch (activeName) {
                case HomeScreenResourceManagerActivity.ALL_ORDERS:
                    tempOrders = getAllValidOrders(this.database.getAllOrders());
                    break;
                case HomeScreenResourceManagerActivity.NEW_ORDERS:
                    tempOrders = getAllNewOrders();
                    break;
                case HomeScreenResourceManagerActivity.ORDER_DONE:
                    tempOrders = getAllBoughtOrders(this.database.getAllOrders());
                    break;
                case HomeScreenResourceManagerActivity.CANCLED_ORDERS:
                    tempOrders = getAllCanceldOrders(this.database.getAllOrders());
                    break;
                case HomeScreenActivity.MY_ORDERS:
                    tempOrders = getAllValidOrders(getMyOrders());
                    break;
                case HomeScreenActivity.MY_CANCELED_ORDERS:
                    tempOrders = getAllCanceldOrders(getMyOrders());
                    break;
                case HomeScreenActivity.MY_GROUP_ORDERS:
                    tempOrders = getAllValidOrders(getMyGroupOrders());
                    break;
                case HomeScreenActivity.My_FINISHED_ORDERS:
                    tempOrders = getAllBoughtOrders(getMyOrders());
                    break;
                default:
                    break;
            }
        }

        return tempOrders;
    }

    
    private ArrayList<Order> getMyGroupOrders() throws SQLException{
        ArrayList<Order> temp;
        if(this.user == null)
            temp = new ArrayList<>();
        else
            temp = this.database.getOrdersFromGroup(this.user.getGroup());
        return temp;
    }
    
    private ArrayList<Orderlist> getGroceryList() throws JDOMException, IOException {
        File temp = new File(GroceryManager.XML_FILE_PATH);
        return (temp.canRead()) ? xmlManager.loadXMLOrderLists(temp) : null;
    }

    private ArrayList<String> getAllItems() throws SQLException{
        return database.getAllItems();
    }
    
    private String getActivityName() {
        return currentActivityManager.getTitle();
    }

    private int getOrderIndex() {
        if (this.currentActivityManager instanceof GroceryManager) {
            return ((GroceryManager) currentActivityManager).getActiveIndex();
        }
        return Integer.MAX_VALUE;
    }

    public boolean loginIsValid() {
        if (this.currentActivityManager instanceof LogInManager) {
            this.user = ((LogInManager) currentActivityManager).getUser();
            return (user != null);
        }
        return false;
    }

    public void setCurrentActivity(ActivityManager tempActivityManager) {
        this.currentActivityManager = tempActivityManager;
    }

    public boolean ping(int timeout) throws SQLException {
        return database.ping(timeout);
    }

    public void reconnect() throws SQLException {
        this.database.reconnect();
    }

    public String getUserName() {
        return (this.user != null) ? this.user.getName() : "Keine Information";
    }

    private Action[] getOrderSwipeAction() {
        Action[] temp = null;
        if(this.currentActivityManager instanceof ShowsOrders){
            temp = ((ShowsOrders)this.currentActivityManager).getSwipeActions();
        }
        return temp;
    }

    private boolean[] getSwipeAllowed() {
        boolean[] temp = null;
        if(this.currentActivityManager instanceof ShowsOrders){
            temp = ((ShowsOrders)this.currentActivityManager).getSwipeAllowed();
        }
        return temp;
    }
}
