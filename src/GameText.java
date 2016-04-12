/**
 * Created by Caitlin on 2/4/2016.
 * http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class GameText {

    private HashMap<String, String> hash;
    private File inputFile;

    // TODO 4/11/2016: Make sure the file is valid
    public GameText(String file) {
        inputFile = new File(file);
        hash = new HashMap();
        parse();
    }

    private void parse() {
        try {
            //set up XML reader
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //parse XML file
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //get items with 'item' id
            //in our case this is all of the text
            NodeList nList = doc.getElementsByTagName("item");

            //loop through all items in the list of nodes

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //add items to the hash table
                    hash.put(eElement.getAttribute("id"), eElement
                            .getElementsByTagName("text")
                            .item(0)
                            .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getText(String id){
        // TODO: 4/11/2016 Assert that id is valid. What does hash.get do if it gets an invalid id? 
        return hash.get(id);
    }
}


