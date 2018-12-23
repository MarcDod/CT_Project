/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.database;

import DataManagement.Datatemplates.Account;
import DataManagement.Datatemplates.Order;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Marc
 */
public class Connector{

    private DB_Connection con;

    public Connector() throws SQLException{
        con = new DB_Connection();
    }

    private ResultSet sendSQLStatement(String statement) throws SQLException{
        return this.con.sendSqlStatement(statement);
    }

    public String getPassword(String username) throws SQLException{
        ResultSet result = this.sendSQLStatement(
                "SELECT password FROM mydb.account WHERE User = \"" + username
                + "\";");
        result.next();
        return result.getString(1);
    }

    /**
     * Aquires an Account representation from the Database
     *
     * @param username username of the Account in search
     * @return Account representation af the account or null if no Account found
     * @throws SQLException
     */
    public Account getAccount(String username) throws
            SQLException{
        Account resultingAccount = null;
        ResultSet result = this.sendSQLStatement(
                "SELECT User,groupID FROM mydb.account WHERE User = \""
                + username + "\";");
        if(result.next()){
            resultingAccount = new Account(result.getString("User"), result.
                    getInt("groupID"));
        }
        return resultingAccount;
    }

    public boolean ping(int timeout) throws SQLException{
        return this.con.ping(timeout);
    }

    public void reconnect() throws SQLException{
        this.con.reconnect();
    }

    public Order getOrder(int orderID) throws SQLException{
        Order resultingOrder = null;
        ResultSet result = this.sendSQLStatement(
                "SELECT * FROM mydb.order WHERE orderID = \"" + String.valueOf(
                        orderID) + "\";");
        if(result.next()){
            resultingOrder = new Order(result.getInt("orderID"), result.getDate(
                    "date"), result.getString("deadline"), result.getInt(
                    "number"), result.getBoolean("canceled"), result.getString(
                    "itemName"), result.getString("user"), result.getBoolean(
                    "watched"), result.getBoolean(
                            "bought"));
        }
        return resultingOrder;
    }

    public ArrayList<Order> getAllOrders() throws SQLException{
        ArrayList<Order> resultingOrder = new ArrayList<>();
        ResultSet result = this.sendSQLStatement(
                "SELECT * FROM mydb.order;");
        while(result.next()){
            resultingOrder.add(new Order(result.getInt("orderID"), result.
                    getDate(
                            "date"), result.getString("deadline"), result.
                    getInt(
                            "number"), result.getBoolean("canceled"), result.
                    getString(
                            "itemName"), result.getString("user"), result.
                    getBoolean(
                            "watched"), result.getBoolean(
                            "bought")));
        }
        return resultingOrder;
    }

    public void addItem(String itemName, float defaultPrice) throws SQLException{
        String[] attributes = {"itemName", "defaultPrice"};
        String[] values = {itemName, String.valueOf(defaultPrice)};
        this.insert("item", attributes, values);
    }

    public void addOrder(int orderID, Date date, String deadline, int number,
            boolean closed, String itemName, boolean watched, String user) throws SQLException{
        String[] attributes = {"odererID", "date", "deadline", "number",
            "closed", "itemName", "watched", "User"};
        int closedint = (closed) ? 1 : 0;
        int watchedint = (watched) ? 1 : 0;
        String[] values = {String.valueOf(orderID), "" + date, deadline, String.
            valueOf(number), String.valueOf(closedint),itemName,String.
            valueOf(watchedint),user};
        this.insert("order", attributes, values);
    }

    private void insert(String table, String[] attributes, String[] values)
            throws SQLException{
        String statementString = "INSERT INTO `mydb`.`" + table + "` (`";
        for(int i = 0; i < attributes.length; i++){
            String attribute = attributes[i];
            if(i == attributes.length - 1){
                statementString += attribute + "`) ";
            }else{
                statementString += attribute + "`, `";
            }
        }
        statementString += "VALUES ('";
        for(int i = 0; i < values.length; i++){
            String value = values[i];
            if(i == values.length - 1){
                statementString += value + "');";
            }else{
                statementString += value + "', '";
            }
        }
        this.con.executeUpdate(statementString);
    }//INSERT INTO `mydb`.`item` (`itemName`, `defaultPrice`) VALUES ('Nutella', '1.50');

}
