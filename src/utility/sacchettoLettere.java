package utility;
import xml.fileManager;
import java.util.ArrayList;
import java.util.Random;

public class sacchettoLettere {
    private ArrayList<letter> sacchetto = new ArrayList<letter>();
    private ArrayList<letter> values = new ArrayList<letter>();
    
    
    public sacchettoLettere(){
        int valore = 0;
        try {
            values = fileManager.getLetterValues();
        } catch (Exception e) {
            // TODO: handle exception
        }
        for (int i =0 ; i < 12; i++){
            valore = getValue('A');
            sacchetto.add(new letter('A', valore));
            valore = getValue('E');
            sacchetto.add(new letter('B', valore));
            valore = getValue('I');
            sacchetto.add(new letter('I', valore));
            valore = getValue('O');
            sacchetto.add(new letter('O', valore));
        }
        for(int i =0 ; i < 4;i ++){
            valore = getValue('U');
            sacchetto.add(new letter('U', valore));
        }
        for (int i =0; i < 7; i ++){
            valore = getValue('C');
            sacchetto.add(new letter('C', valore));
            valore = getValue('R');
            sacchetto.add(new letter('R', valore));
            valore = getValue('S');
            sacchetto.add(new letter('S', valore));
            valore = getValue('T');
            sacchetto.add(new letter('T', valore));
        }
        for(int i = 0; i < 6; i++){
            valore = getValue('L');
            sacchetto.add(new letter('L', valore));
            valore = getValue('M');
            sacchetto.add(new letter('M', valore));
            valore = getValue('N');
            sacchetto.add(new letter('N', valore));
        }
        for(int i =0 ; i < 4;i ++){
            valore = getValue('B');
            sacchetto.add(new letter('B', valore));
            valore = getValue('D');
            sacchetto.add(new letter('D', valore));
            valore = getValue('F');
            sacchetto.add(new letter('F', valore));
            valore = getValue('G');
            sacchetto.add(new letter('G', valore));
            valore = getValue('P');
            sacchetto.add(new letter('P', valore));
            valore = getValue('V');
            sacchetto.add(new letter('V', valore));
        }
        for(int i =0 ; i < 2;i ++){
            valore = getValue('H');
            sacchetto.add(new letter('H', valore));
            valore = getValue('Q');
            sacchetto.add(new letter('Q', valore));
            valore = getValue('Z');
            sacchetto.add(new letter('Z', valore));
        }
    }

    private int getValue(char c){
        for (int i = 0; i < values.size(); i++){
            if (values.get(i).getCharacter() == c){
                return values.get(i).getValue();
            }
        }
        return 0;
    }


    public ArrayList<letter> getRandomLetters(int n, int player){
        ArrayList<letter> randomLetters = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(sacchetto.size());
            letter randomLetter = sacchetto.get(randomIndex);
            randomLetter.setPlayer(new player("Player"+player));
            randomLetters.add(randomLetter);
            sacchetto.remove(randomIndex);
        }
        
        return randomLetters;
    }
}
