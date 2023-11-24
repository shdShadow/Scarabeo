package server;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import utility.letter;
import utility.vocabolario;
import xml.fileManager;

/**
 * La classe checkGiocata si occupa di controllare la validita' di una parola inserita nel gioco
 */
public class checkGiocata {
    
    /**
     * Il vocabolario utilizzato in questa versione del gioco
     */
    private static vocabolario vocabolario = new vocabolario();
    
    
    /**
     * Controlla la validita' di una parola inserita formata dal set di lettere passato come parametro
     * 
     * @param letters La lista di lettere che formano la parola.
     * @param giocate Il numero di giocate effettuate.
     * @return Un array di stringhe contenente lo stato della risposta e informazioni aggiuntive.
     *         - response[0]: "ok" se la parola e' valida, "error" altrimenti.
     *         - response[1]: "" se la parola e' valida, un messaggio di errore altrimenti.
     * @throws ParserConfigurationException Se il parser non puo' essere configurato.
     * @throws IOException Se si verifica un errore di I/O.
     * @throws SAXException Se si verifica un errore SAX.
     */
    public static String[] checkParola(ArrayList<letter> letters, int giocate) throws ParserConfigurationException, IOException, SAXException {
        String[] response = new String[2];
        //Creo la parola dalle lettere passate
        String parola = letter.returnWord(letters);
        boolean temp = true;
        //Controllo che la parola esista nel vocabolario
        parola = parola.toLowerCase();
        //Controllo che la parola esista nel vocabolario
        if (vocabolario.cercaParola(parola)){
            //Se esiste controllo che sia scritta seguendo una linea orizzontale o verticale
            if(checkIfOrizzOrVert(letters)){
                temp = true;
                response[1] = "";
            }else{
                temp = false;
                response[1] = "La parola non e' orizzontale o verticale";
            }
        }else{
            temp = false;
            response[1] = "La parola non esiste nel vocabolario";
        }
        //Se ha passato i primi controlli, verifico che la parola intersechi una casella di un altra parola gia' presente
        if (temp){
            //La parola deve intersecare una casella solo se non e' la prima parola inserita
            if(giocate > 0 ){
            boolean interseca = false;
            for (letter letter : letters) {
                if (letter.getBorrowed()){
                    interseca = true;
                    break;
                }
            }
            if (interseca){
                temp = true;
                response[1] = "";
            }else{
                temp = false;
                response[1] ="La parola deve intersecare una casella!";
            }
        }
        }
        //Se ha passato tutti i controlli, calcolo il punteggio della parola
        if (temp){
            response[0] = "ok";
            response[1] = calcolaPunteggio(letters);
        }else{
            response[0] = "error";
        }
        return response;
    }
    /**
     * Controlla che la parola sia scritta seguendo una linea orizzontale o verticale
     * 
     * @param letters La lista di lettere che formano la parola.
     * @return true se la parola e' scritta seguendo una linea orizzontale o verticale, false altrimenti.
     */
    private static boolean checkIfOrizzOrVert(ArrayList<letter> letters){
        boolean xModified = false;
        boolean yModified = false;
        for(int i =0; i < letters.size(); i++){
            //Devo controllare che tutte le lettere abbiano la stessa coordinata x
            if(letters.get(i).getP().getX() != letters.get(0).getP().getX()){
                xModified = true;
                break;
            }
            //Devono controllare che tutte le lettere abbiano la stessa coordinata y
            if(letters.get(i).getP().getY() != letters.get(0).getP().getY()){
                yModified = true;
                break;
            }
        }
        boolean tmp = true;
        //Se la x e' stata modificata, e' probabile che la parola sia orizzontale
        if (xModified){
            for(int i =0; i < letters.size(); i++){
                if(letters.get(i).getP().getY() != letters.get(0).getP().getY()){
                    tmp = false;
                    break;
                }
            }
            //Se la y e' stata modificata, e' probabile che la parola sia verticale
        }else if(yModified){
            for(int i =0; i < letters.size(); i++){
                if(letters.get(i).getP().getX() != letters.get(0).getP().getX()){
                    tmp = false;
                    break;
                }
            }
        }
        return tmp;
    }
    /**
     * Calcola il punteggio della parola inserita
     * 
     * @param l La lista di lettere che formano la parola.
     * @return Il punteggio della parola inserita.
     * @throws ParserConfigurationException Se il parser non puo' essere configurato.
     * @throws IOException Se si verifica un errore di I/O.
     * @throws SAXException Se si verifica un errore SAX.
     */
    private static String calcolaPunteggio(ArrayList<letter> l) throws ParserConfigurationException, IOException, SAXException{
        ArrayList<letter> values = fileManager.getLetterValues();
        Integer punteggio = 0;
        //Ciclo all'interno delle lettere passate come parametro e sommo il valore di ogni lettera preso dal file xml
        for (letter letter : l){
            for (letter value : values){
                if (letter.getCharacter() == value.getCharacter()){
                    punteggio += value.getValue();
                }
            }
        }
        return punteggio.toString();
    }
}
