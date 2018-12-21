/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.Datatemplates;

import DataManagement.XML.XMLDatatemplate;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 *
 * @author Julian
 */
public class Orderlist extends XMLDatatemplate{
    ArrayList<Integer> orderIDs;
    String name;

    public Orderlist(ArrayList<Integer> orderIDs, String name){
        this.orderIDs = orderIDs;
        this.name = name;
    }

    public Orderlist(String name){
        this(new ArrayList<Integer>(), name);
    }

    public Orderlist(Element e) throws DataConversionException{
        List<Content> contents = e.getChild("IDs").getContent();
        ArrayList<Integer> ids = new ArrayList<>();
        for(Content content : contents){
            this.orderIDs.add(Integer.parseInt(content.getValue()));
        }
        this.name = e.getAttributeValue("Name");
    }

    @Override
    public Element pack(){
        Element representingElement = new Element("Orderlist");
        representingElement.setAttribute("Name", this.name);
        Element ids = new Element("IDs");
        this.orderIDs.forEach((orderID) -> {
            ids.addContent(String.valueOf(orderID));
        });
        representingElement.addContent(ids);
        return representingElement;
    }
}
