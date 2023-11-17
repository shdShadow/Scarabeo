package xml;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import utility.*;

public class parserStringifier {
  public static String stringifyCommand(comando c)
      throws ParserConfigurationException, TransformerConfigurationException,
             TransformerException {
    String s = "";
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();
    ArrayList<letter> l = new ArrayList<letter>();
    // root
    Element rootElement = doc.createElement("comando");
    doc.appendChild(rootElement);
    // elemento exec
    Element execElement = doc.createElement("exec");
    execElement.appendChild(doc.createTextNode(c.getExec()));
    rootElement.appendChild(execElement);
    // elemento letter
    l = c.getL();

    for (letter letter : l) {
      //Creo un tag letter
      Element elementLetter = doc.createElement("letter");
      //Creo un altro tag carattere
      //all'interno creo un text node contenente il carattere
      Element characterElement = doc.createElement("character");
      characterElement.appendChild(
          doc.createTextNode(String.valueOf(letter.getCharacter())));
      //aggiungo in coda al tag letter il tag character
      elementLetter.appendChild(characterElement);

      //svolgo lo stesso procedimento per il valore
      //creo un tag value
      //all'interno creo un text node contenente il valore
      Element valueElement = doc.createElement("value");
      valueElement.appendChild(
          doc.createTextNode((letter.getValue().toString())));
      //aggiungo in coda al tag letter il tag value
      elementLetter.appendChild(valueElement);

      //creo un tag punto
      Element puntoElement = doc.createElement("punto");

      //aggiungo al tag punto un secondo tag x
      Element xElement = doc.createElement("x");
      xElement.appendChild(doc.createTextNode(letter.getP().getX().toString()));
      puntoElement.appendChild(xElement);
      //aggiungo al tag punto un terzo tag y
      Element yElement = doc.createElement("y");
      yElement.appendChild(doc.createTextNode(letter.getP().getY().toString()));
      puntoElement.appendChild(yElement);
      //aggiungo in coda al tag letter il tag punto
      elementLetter.appendChild(puntoElement);
      //aggiungo in coda alla root il tag letter
      //ESEMPIO
      //<?xml version="1.0" encoding="UTF-8" standalone="no"?>
      // <comando>
      //     <exec>add</exec>
      //     <letter>
      //         <character>D</character>
      //         <value>2</value>
      //         <punto>
      //             <x>2</x>
      //             <y>4</y>
      //         </punto>
      //     </letter>
      //     <letter>
      //         <character>G</character>
      //         <value>3</value>
      //         <punto>
      //             <x>2</x>
      //             <y>4</y>
      //         </punto>
      //     </letter>
      // </comando>
      rootElement.appendChild(elementLetter);
    }

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StringWriter stringWriter = new StringWriter();
    StreamResult result = new StreamResult(stringWriter); 
    transformer.transform(source, result);
    return stringWriter.toString();
  }

  public static comando parseCommando(String client_command) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(client_command));
    Document doc = builder.parse(is);

    Element root = doc.getDocumentElement();
    String exec = root.getElementsByTagName("exec").item(0).getTextContent();

    NodeList letterNodes = root.getElementsByTagName("letter");
    ArrayList<letter> letters = new ArrayList<letter>();
    for (int i = 0; i < letterNodes.getLength(); i++) {
      Element letterElement = (Element) letterNodes.item(i);
      char character = letterElement.getElementsByTagName("character").item(0).getTextContent().charAt(0);
      int value = Integer.parseInt(letterElement.getElementsByTagName("value").item(0).getTextContent());
      int x = Integer.parseInt(letterElement.getElementsByTagName("x").item(0).getTextContent());
      int y = Integer.parseInt(letterElement.getElementsByTagName("y").item(0).getTextContent());
      punto p = new punto(x, y);
      letter l = new letter(character, value, p);
      letters.add(l);
    }

    comando c = new comando(exec, letters);
    return c;
  }
}
