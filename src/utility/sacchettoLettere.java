package utility;
import xml.fileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class sacchettoLettere {
    private ArrayList<letter> sacchetto = new ArrayList<letter>();
    private ArrayList<letter> values = new ArrayList<letter>();

    public sacchettoLettere() throws ParserConfigurationException, IOException, SAXException{
       sacchetto = fileManager.getLetterValues();
    }

    public ArrayList<letter> getRandomLetters(int n, int player){
        ArrayList<letter> randomLetters = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(sacchetto.size());
            letter randomLetter = sacchetto.get(randomIndex);
            randomLetter.setPlayer(new player("Player"+player));
            randomLetters.add(randomLetter);
        }
        
        return randomLetters;
    }
}
