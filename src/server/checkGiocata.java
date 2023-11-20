package server;

import java.util.ArrayList;

import utility.letter;
import utility.vocabolario;

public class checkGiocata {
    private static vocabolario vocabolario = new vocabolario();
    public static String[] checkParola(ArrayList<letter> letters) {
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
            temp = true;
            response[1] = "La parola non esiste nel vocabolario";
        }
        if (temp){
            response[0] = "ok";
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
}
