import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import utility.*;
import xml.*;

public class app {
  public static void main(String[] args) throws Exception {
    ArrayList<letter> tmp = new ArrayList<letter>();
    tmp = xml.fileManager.getLetterValues();
    // for (letter letter : tmp) {
    //     System.out.println(letter.toString());
    // }
    // matrice3x3 matrice = new matrice3x3();
    // matrice.stampaMatrice();
    letter l1 = tmp.get(3);
    letter l2 = tmp.get(6);
    punto p = new punto(2, 4);
    l1.setP(p);
    l2.setP(p);
    ArrayList<letter> test = new ArrayList<letter>();
    test.add(l1);
    test.add(l2);
    //comando c = new comando("add", test);
    //System.out.println(xml.parserStringifier.stringifyCommand(c));
    comando c = new comando();

     BufferedReader br = new BufferedReader(new FileReader("./src/xml/test.xml"));
        String line;
        StringBuilder xmlContent = new StringBuilder();
        while((line = br.readLine())!=null){
            xmlContent.append(line);
        }
        br.close();
      String tmp2 = xmlContent.toString();
      c = parserStringifier.parseCommando(tmp2);
    
  }
}
