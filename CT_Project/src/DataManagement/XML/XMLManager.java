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
import org.jdom2.Document;
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

    public Orderlist loadXMLOrderList(File file) throws JDOMException,
            IOException{
        Document xmlDoc = new SAXBuilder().build(file);
        return new Orderlist(xmlDoc.getRootElement());
    }

    public void saveXMLOrderList(Orderlist o, File file) throws IOException{
        FileWriter fw = new FileWriter(file);
        new XMLOutputter(Format.getPrettyFormat()).
                output(new Document(o.pack()), fw);
    }
}
