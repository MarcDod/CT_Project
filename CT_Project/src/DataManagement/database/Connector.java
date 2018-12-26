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
            boolean canceled, String itemName, boolean watched, String user,
            boolean bought) throws SQLException{
        String[] attributes = {"odererID", "date", "deadline", "number",
            "canceled", "itemName", "watched", "User", "bought"};
        int canceledint = (canceled) ? 1 : 0;
        int watchedint = (watched) ? 1 : 0;
        int boughtint = (bought) ? 1 : 0;
        String[] values = {String.valueOf(orderID), "" + date, deadline, String.
            valueOf(number), String.valueOf(canceledint), itemName, String.
            valueOf(watchedint), user, String.valueOf(boughtint)};
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

    public void setOrderCanceled(int orderID, boolean canceled) throws
            SQLException{
        this.update("order", "canceled", String.valueOf((canceled) ? 1 : 0),
                "orderID", String.valueOf(orderID));
    }

    public void setOrderWatched(int orderID, boolean watched) throws
            SQLException{
        this.update("order", "watched", String.valueOf((watched) ? 1 : 0),
                "orderID", String.valueOf(orderID));
    }

    public void setOrderBought(int orderID, boolean bought) throws SQLException{
        this.update("order", "bought", String.valueOf((bought) ? 1 : 0),
                "orderID", String.valueOf(orderID));
    }

    private void update(String table, String cloumn, String value,
            String identifyingColumn, String identifyingValue) throws
            SQLException{//UPDATE `mydb`.`order` SET `canceled` = '1' WHERE (`orderID` = '1');
        String statement = "UPDATE `mydb`.`" + table + "` SET `" + cloumn
                + "` = '" + value + "' WHERE (`" + identifyingColumn + "` = '"
                + identifyingValue + "');";
        this.con.executeUpdate(statement);
    }

}
