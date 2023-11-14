import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import utility.*;
import xml.*;

public class app {
  public static void main(String[] args)
      throws SAXException, IOException, ParserConfigurationException {
    ArrayList<letter> tmp = new ArrayList<letter>();
    tmp = xml.fileManager.getLetterValues();
    for (letter letter : tmp) {
        System.out.println(letter.toString());
    }
  }
}
