/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package inputoutput;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import constants.Constants;


public class XMLGenerator {

    private static int fileNameCounter = 0;

    public void writeXML (Map<String, String> params, int[][] currentStates) {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element root = doc.createElement("root");
            doc.appendChild(root);

            addParameters(params, doc, root);
            if (currentStates != null) {
            	addInitialStates(currentStates, doc, root);
            	
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

            String filePath = Constants.RESOURCES.getString("saveXMLFilePath") +
                              params.get("gameType") +
                              Constants.RESOURCES.getString("Underscore") +
                              Integer.toString(fileNameCounter) +
                              Constants.RESOURCES.getString("SavedFileExtension");

            FileWriter fw = new FileWriter(filePath);
            fileNameCounter++;
            fw.write(xmlString);
            ;
            fw.close();

        }
        catch (Exception e) {
            e.getMessage();
        }

    }

    /**
     * Translates 2D array of integer states to multiple row attributes with columns concatenated as
     * a String
     * 
     * @param currentStates
     * @param doc to write the elements to
     * @param root to add the initialStates element to
     */
    private void addInitialStates (int[][] currentStates, Document doc, Element root) {
        Element initialStates = doc.createElement("initialStates");

        for (int[] currentState : currentStates) {
            Element row = doc.createElement("row");

            String rowValues = "";
            for (int c = 0; c < currentStates[0].length; c++) {
                rowValues += Integer.toString(currentState[c]);
            }

            Node values = doc.createTextNode(rowValues);
            row.appendChild(values);
            initialStates.appendChild(row);
        }

        root.appendChild(initialStates);

    }

    /**
     * Takes Map of parameters and adds key as tag and value as node value
     * 
     * @param params
     * @param doc
     * @param root
     */
    private void addParameters (Map<String, String> params, Document doc, Element root) {
        for (String key : params.keySet()) {
            Element paramName = doc.createElement(key);
            Node paramValue = doc.createTextNode(params.get(key));
            paramName.appendChild(paramValue);

            root.appendChild(paramName);
        }
    }

}
