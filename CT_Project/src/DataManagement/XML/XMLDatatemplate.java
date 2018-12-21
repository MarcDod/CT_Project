/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManagement.XML;

import org.jdom2.Element;

/**
 *
 * @author Julian
 */
public abstract class XMLDatatemplate{

    public XMLDatatemplate(){
    }
    public XMLDatatemplate(Element e){
    }
//    abstract XMLDatatemplate create(Element e) throws DataConversionException;
    public abstract Element pack();
}
