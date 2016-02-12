/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package inputoutput;

import java.io.*;
import java.util.Map;

import org.w3c.dom.*;

import constants.Constants;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XMLGenerator {
  
	private static int fileNameCounter = 0;
	
    public void writeXML (Map<String,String> params) {
    	try {
    		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
 
            Element root = doc.createElement("root");
            doc.appendChild(root);

            for (String key : params.keySet()){
            	Element paramName = doc.createElement(key);
            	Node paramValue = doc.createTextNode(params.get(key));
            	paramName.appendChild(paramValue);
            	
            	root.appendChild(paramName);
            }
            
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            String filePath = 	Constants.RESOURCES.getString("saveXMLFilePath") + 
            					params.get("gameType") +
            					Constants.RESOURCES.getString("Underscore") +
            					Integer.toString(fileNameCounter) +
            					Constants.RESOURCES.getString("SavedFileExtension");
 
            FileWriter fw = new FileWriter(filePath);
            fileNameCounter++;
            fw.write(xmlString);;
            fw.close();
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    
    
}
