/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.Datatemplates;

/**
 *
 * @author Julian
 */
public class Account{
    private int id;
    private String name;
    private int group;

    public Account(int id, String name, int group){
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getGroup(){
        return group;
    }
}
