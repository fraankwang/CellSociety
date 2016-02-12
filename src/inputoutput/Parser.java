package inputoutput;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import constants.Parameters;


public class Parser {

	
	public Parameters parse (File file) {
		Map<String, String> params = new HashMap<String, String>();
		int[][] initialStates = null;
		try {				
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList root = doc.getFirstChild().getChildNodes();
			
			for (int i = 0; i < root.getLength(); i++) {
				Node elem = root.item(i);
				if (elem.getNodeType() == Node.ELEMENT_NODE) {
					if (!elem.getNodeName().equalsIgnoreCase("initialStates")) {
							params.put(elem.getNodeName(), elem.getTextContent());						
					}
					else if (elem.getNodeName().equalsIgnoreCase("initialStates")){
						NodeList states = elem.getChildNodes();
						ArrayList<String> stateValues = new ArrayList<String>();
						
						for (int k = 0; k < states.getLength(); k++) {
							if (states.item(k).getNodeType() == Node.ELEMENT_NODE) {
								String row = states.item(k).getTextContent().trim();
								stateValues.add(row);							
							}
							
						}
						initialStates = createInitialStates(stateValues);
					}
				}
			}		
			
			params.remove("#text");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Parameters(params, initialStates);
		
	}
		
	private int[][] createInitialStates(ArrayList<String> states) {
		int numberOfRows = states.size();
		int numberOfColumns = states.get(0).length();
		
		int[][] initialStates = new int[numberOfRows][numberOfColumns];
		
		for (int row = 0; row < numberOfRows; row++) {
			String rowValues = states.get(row);
			
			for (int column = 0; column < numberOfColumns; column++) {
				char cellState = rowValues.charAt(column);
				initialStates[row][column] = Character.getNumericValue(cellState);
				
			}
		}

		return initialStates;
		
	}

}
