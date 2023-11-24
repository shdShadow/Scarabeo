package utility;
import xml.fileManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
/**
 * Classe che rappresenta il sacchetto delle lettere.
 * Viene utilizzata per la generazione casuale della lettere per le mani dei player
 */
public class sacchettoLettere {
    /**
     * Attributo che rappresenta il sacchetto delle lettere e dei loro punteggi
     * In questa versione del gioco, il sacchetto non ha limiti e l'unico modo per terminare una partita e' quello di raggiungere il punteggio massimo
     */
    private ArrayList<letter> sacchetto = new ArrayList<letter>();

    
    /**
     * Costrutture non parametrico
     * Ottiene le lettere e i loro valori dal file xml
     * 
     * @throws ParserConfigurationException se un DocumentBuilder non puo' essere creato
     * @throws IOException se un errore di I/O avviene durante la lettura del file
     * @throws SAXException se avviene un errore durante il parsing del file
     */
    public sacchettoLettere() throws ParserConfigurationException, IOException, SAXException{
       sacchetto = fileManager.getLetterValues();
    }

    
    /**
     * Genera un lista di n lettere random prendendole dal sacchetto
     * 
     * @param n Il numero di lettere da generare.
     * @param player Il player da associare alle lettere
     * @return Un' ArrayList di lettere generate randomicamente
     */
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
