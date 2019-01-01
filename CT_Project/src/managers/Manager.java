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
import GuiElements.activities.HomeScreen;
import GuiElements.activities.HomeScreenResourceManager;
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

    public boolean isEmpty() {
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
        return new HomeManager(getMyOrders());
    }
    
    public NewOrderManager getNewOrderManager(){
        return new NewOrderManager();
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
                case HomeScreenResourceManager.ALL_ORDERS:
                    tempOrders = getAllValidOrders(this.database.getAllOrders());
                    break;
                case HomeScreenResourceManager.NEW_ORDERS:
                    tempOrders = getAllNewOrders();
                    break;
                case HomeScreenResourceManager.ORDER_DONE:
                    tempOrders = getAllBoughtOrders(this.database.getAllOrders());
                    break;
                case HomeScreenResourceManager.CANCLED_ORDERS:
                    tempOrders = getAllCanceldOrders(this.database.getAllOrders());
                    break;
                case HomeScreen.MY_ORDERS:
                    tempOrders = getMyOrders();
                default:
                    break;
            }
        }

        return tempOrders;
    }

    private ArrayList<Order> getMyOrders() throws SQLException{
        ArrayList<Order> myOrders = new ArrayList<>();
        return getAllValidOrders(myOrders);
    }
    
    private ArrayList<Orderlist> getGroceryList() throws JDOMException, IOException {
        File temp = new File(GroceryManager.XML_FILE_PATH);
        return (temp.canRead()) ? xmlManager.loadXMLOrderLists(temp) : null;
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
        Action[] temp = new Action[2];
        String name = currentActivityManager.getTitle();
        switch (name) {
            case HomeScreenResourceManager.NEW_ORDERS:
                temp[0] = Action.SET_CANCEL_TRUE;
                temp[1] = Action.SET_WATCHED_TRUE;
                break;
            case HomeScreenResourceManager.ALL_ORDERS:
            case GroceryManager.ORDERS_WITHOUT_LIST:    
                temp[0] = Action.SET_CANCEL_TRUE;
                temp[1] = Action.SET_BOUGHT_TRUE;
                break;
            case HomeScreenResourceManager.ORDER_DONE:
                temp[0] = Action.SET_BOUGHT_FALSE;
                temp[1] = Action.NOTHING;
                break;
            case HomeScreenResourceManager.CANCLED_ORDERS:
                temp[0] = Action.NOTHING;
                temp[1] = Action.SET_CANCEL_FALSE;
                break;
            default:
                temp[0] = Action.NOTHING;
                temp[1] = Action.SET_BOUGHT_TRUE;
                break;
        }
        return temp;
    }

    private boolean[] getSwipeAllowed() {
        boolean[] temp = new boolean[2];
        String name = currentActivityManager.getTitle();
        switch (name) {
            case HomeScreenResourceManager.NEW_ORDERS:
                temp[0] = true;
                temp[1] = true;
                break;
            case HomeScreenResourceManager.ALL_ORDERS:
            case GroceryManager.ORDERS_WITHOUT_LIST:    
                temp[0] = true;
                temp[1] = true;
                break;
            case HomeScreenResourceManager.ORDER_DONE:
                temp[0] = true;
                temp[1] = false;
                break;
            case HomeScreenResourceManager.CANCLED_ORDERS:
                temp[0] = false;
                temp[1] = true;
                break;
            default:
                temp[0] = false;
                temp[1] = false;
                break;
        }

        return temp;
    }
}
