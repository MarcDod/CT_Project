/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import DataManagement.Datatemplates.Order;
import DataManagement.Datatemplates.Orderlist;
import GuiElements.activities.HomeScreenResourceManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class HomeManagerResourceManager extends ActivityManager implements ShowsOrders {

    private ArrayList<Orderlist> groceryList;

    private String buttonName;

    private int notWatchedOrder;

    public HomeManagerResourceManager(ArrayList<Orderlist> groceryList, ArrayList<Order> allOrder) throws SQLException {
        this.groceryList = groceryList;
        this.notWatchedOrder = 0;
        allOrder.stream().filter((order) -> (!order.isWatched())).forEachOrdered((_item) -> {
            notWatchedOrder++;
        });
    }

    public int getGrocerySize() {
        return (groceryList != null) ? this.groceryList.size() : 0;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public int getNotWatchedOrdes() {
        return this.notWatchedOrder;
    }

    @Override
    public String getTitle() {
        return this.buttonName;
    }

    @Override
    public boolean[] getSwipeAllowed() {
        boolean[] temp = new boolean[2];
        switch (this.buttonName) {
            case HomeScreenResourceManager.NEW_ORDERS:
                temp[0] = true;
                temp[1] = true;
                break;
            case HomeScreenResourceManager.ALL_ORDERS:
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

    @Override
    public OrderManager.Action[] getSwipeActions() {
        OrderManager.Action[] temp = new OrderManager.Action[2];
        switch (this.buttonName) {
            case HomeScreenResourceManager.NEW_ORDERS:
                temp[0] = OrderManager.Action.SET_CANCEL_TRUE;
                temp[1] = OrderManager.Action.SET_WATCHED_TRUE;
                break;
            case HomeScreenResourceManager.ALL_ORDERS:
            case GroceryManager.ORDERS_WITHOUT_LIST:
                temp[0] = OrderManager.Action.SET_CANCEL_TRUE;
                temp[1] = OrderManager.Action.SET_BOUGHT_TRUE;
                break;
            case HomeScreenResourceManager.ORDER_DONE:
                temp[0] = OrderManager.Action.SET_BOUGHT_FALSE;
                temp[1] = OrderManager.Action.NOTHING;
                break;
            case HomeScreenResourceManager.CANCLED_ORDERS:
                temp[0] = OrderManager.Action.NOTHING;
                temp[1] = OrderManager.Action.SET_CANCEL_FALSE;
                break;
            default:
                temp[0] = OrderManager.Action.NOTHING;
                temp[1] = OrderManager.Action.NOTHING;
                break;

        }
        return temp;
    }
}
