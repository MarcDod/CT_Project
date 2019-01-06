/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

/**
 *
 * @author Marc
 */
public abstract class HomeManager extends ActivityManager{
    protected String buttonName;
    
    public HomeManager(){
        this.buttonName = "";
    }
    
    public void setButtonName(String buttonName){
        this.buttonName = buttonName;
    }
    
    @Override
    public String getTitle() {
        return buttonName;
    }
}
