/**
 * Created by Caitlin on 2/4/2016.
 * http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 */

import java.io.File;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class GameText {

    private Hashtable<String, String> hash;
    private File inputFile;

    public GameText(String file) {
        inputFile = new File(file);
        hash = new Hashtable();
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
        return hash.get(id);
    }
}


