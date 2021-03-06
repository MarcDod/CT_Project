package DataManagement.Datatemplates;

import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marc
 */
public class Order {
    private int orderID;
    private Date date;
    private String deadline;
    private int number;
    private boolean canceld;
    private String itemName;
    private String user;
    private boolean watched;
    private boolean bought;

    public Order(int orderID, Date date, String deadline, int number, boolean canceld, String itemName, String user , boolean watched, boolean bought){
        this.orderID = orderID;
        this.date = date;
        this.deadline = deadline;
        this.number = number;
        this.canceld = canceld;
        this.itemName = itemName;
        this.user = user;
        this.watched = watched;
        this.bought = bought;
    }

    public int getOrderID() {
        return orderID;
    }

//    public void setOrderID(int orderID) {
//        this.orderID = orderID;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCanceld() {
        return canceld;
    }

    public void setCanceld(boolean closed) {
        this.canceld = closed;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }

//    public int getAccountID() {
//        return accountID;
//    }

//    public void setAccountID(int accountID) {
//        this.accountID = accountID;
//    }

    public String getUser(){
        return user;
    }

    public boolean isWatched(){
        return watched;
    }

    public void setWatched(boolean watched){
        this.watched = watched;
    }
    
    
}
