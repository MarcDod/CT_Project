/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.XML;

import DataManagement.Datatemplates.Orderlist;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Julian
 */
public class XMLManager{

    public XMLManager(){
    }

    public ArrayList<Orderlist> loadXMLOrderLists(File file) throws JDOMException,
            IOException{
        Document xmlDoc = new SAXBuilder().build(file);
        ArrayList<Orderlist> list = new ArrayList<>();
        Element rootElement = xmlDoc.getRootElement();
        for(Element element : rootElement.getChildren()){
            list.add(new Orderlist(element));
        }
        return list;
    }

    public void saveXMLOrderLists(ArrayList<Orderlist> list, File file) throws IOException{
        FileWriter fw = new FileWriter(file);
        Element root  = new Element("Orderlists");
        list.forEach((orderlist) -> {
            root.addContent(orderlist.pack());
        });
        new XMLOutputter(Format.getPrettyFormat()).
                output(new Document(root), fw);
    }
}
