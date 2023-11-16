package xml;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utility.*;

public class fileManager {
  public static ArrayList<letter> getLetterValues()
      throws ParserConfigurationException, IOException, SAXException {
    ArrayList<letter> letter_values = new ArrayList<letter>();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(".  /src/xml/letters_values.xml");
    // Document root
    Element letter_list = document.getDocumentElement();
    // NodeList containing every letter
    NodeList letters = letter_list.getElementsByTagName("lettera");
    for (int i = 0; i < letters.getLength(); i++) {
      Element letter = (Element) letters.item(i);
      char character = letter.getElementsByTagName("carattere")
                           .item(0)
                           .getTextContent()
                           .charAt(0);
      Integer value = Integer.parseInt(
          letter.getElementsByTagName("valore").item(0).getTextContent());
      letter l = new letter(character, value);
      letter_values.add(l);
    }
    return letter_values;
  }
}
