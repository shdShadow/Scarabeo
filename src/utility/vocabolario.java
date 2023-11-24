package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Classe contenente tutte le parole del vocabolario italiano
 */
public class vocabolario {
  //Array di stringhe nel quale verrano caricate le parole di un vocabolario preso da Internet
  private ArrayList<String> parole = new ArrayList<String>();

  public vocabolario(){
    this.parole = caricaVocabolario();
  }
  //Ottiene l'elenco delle parole di un vocabolario preso da internet
  private ArrayList<String> caricaVocabolario() {
    ArrayList<String> tmp = new ArrayList<String>();
    try (BufferedReader br =
             new BufferedReader(new FileReader("./src/utility/vocabolario.txt"))) {
      String linea;

      while ((linea = br.readLine()) != null) {
        // Aggiungi la linea alla lista
        tmp.add(linea);
      }

      // Ora tmp contiene tutte le linee del file
      // e possibile accedere alle linee tramite un ciclo

    } catch (IOException e) {
      e.printStackTrace();
    }
    return tmp;
  }
  //Ricerca una parola all'interno del vocabolario
  //La ricerca e' svolta in modo sequenziale in quanto il tempo impiegato non e' troppo elevato
  //E' possibile velocizzarlo implementando una ricerca binaria
  public boolean cercaParola(String parola_da_cercare){
      //variabile Booleana che diventerà true quando la parola cercata verrà trovata
      boolean found = false;
    for(int i =0 ;i < parole.size(); i++){
      if (parole.get(i).equals(parola_da_cercare)){
        found = true;
        break;
      }
    }
    return found;
  }
}
