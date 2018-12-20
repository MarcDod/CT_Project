/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiElements.activities;

import DataManagement.Datatemplates.List;
import java.awt.Color;

/**
 *
 * @author Marc
 */
public class ShowOrder extends Activity{
    public ShowOrder(List list) {
        super(ActivityID.SHOW_ORDER_SCREEN,list.getName() ,new Color(240, 240, 240));
    }
    
}
