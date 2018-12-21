/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.database;

import DataManagement.Datatemplates.Account;
import DataManagement.Datatemplates.Order;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            resultingAccount = new Account(result.getString("User"), result.getInt("groupID"));
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
                    "number"), result.getBoolean("closed"), result.getString(
                    "itemName"), result.getString("user"),result.getBoolean(
                    "watched"));
        }
        return resultingOrder;
    }
}
