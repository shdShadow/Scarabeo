package server;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import utility.letter;
import utility.vocabolario;
import xml.fileManager;

public class checkGiocata {
    private static vocabolario vocabolario = new vocabolario();
    
    public static String[] checkParola(ArrayList<letter> letters, int giocate) throws ParserConfigurationException, IOException, SAXException {
        String[] response = new String[2];
        String parola = letter.returnWord(letters);
        boolean temp = true;
        //Controllo che la parola esista nel vocabolario
        parola = parola.toLowerCase();
        if (vocabolario.cercaParola(parola)){
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
        if (temp){
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
        
        if (temp){
            response[0] = "ok";
            response[1] = calcolaPunteggio(letters);
        }else{
            response[0] = "error";
        }
        return response;
    }
    private static boolean checkIfOrizzOrVert(ArrayList<letter> letters){
        boolean xModified = false;
        boolean yModified = false;
        for(int i =0; i < letters.size(); i++){
            if(letters.get(i).getP().getX() != letters.get(0).getP().getX()){
                xModified = true;
                break;
            }
            if(letters.get(i).getP().getY() != letters.get(0).getP().getY()){
                yModified = true;
                break;
            }
        }
        boolean tmp = true;
        if (xModified){
            for(int i =0; i < letters.size(); i++){
                if(letters.get(i).getP().getY() != letters.get(0).getP().getY()){
                    tmp = false;
                    break;
                }
            }
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

    private static String calcolaPunteggio(ArrayList<letter> l) throws ParserConfigurationException, IOException, SAXException{
        ArrayList<letter> values = fileManager.getLetterValues();
        Integer punteggio = 0;
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
