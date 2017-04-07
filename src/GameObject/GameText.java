package GameObject;
/**
 * Created by Caitlin on 2/4/2016.
 * http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * Parses text from xml files into a hashmap, and provides functionality to get that text
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

    /**
     * Given an xml file, parses that file into a HashMap
     * @param file filename of an xml file
     */
    public GameText(String file) {
        inputFile = new File(file);
        assert inputFile.exists() : "input file does not exist";
        hash = new HashMap();
        parse();
    }

    /**
     * XML parsing function from
     * http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     * @return 0 if successful, -1 if failure
     */
    private int parse() {
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
            return 1;
        }
        return 0;
    }

    /**
     * Gets a text passage given the id from the xml file
     * @param id the name of the passage
     * @return the text passage corresponding to the ID
     */
    public String getText(String id){
        String text = hash.get(id);
        assert text != null : "invalid id passed to hashMap";
        return text;
    }
}


