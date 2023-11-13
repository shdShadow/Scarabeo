package xml; 
import utility.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class fileManager{
    public static ArrayList<letter> getLetterValues() throws ParserConfigurationException, IOException, SAXException{
        ArrayList<letter> letter_values = new ArrayList<letter>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("letters_values.xml");

        return letter_values;
    }

}
