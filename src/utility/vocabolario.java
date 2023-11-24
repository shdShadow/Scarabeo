package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Classe contenente tutte le parole del vocabolario italiano
 */
public class vocabolario {
  
  /**
   * ArrayList che conterra' le parole del vocabolario
   */
  private ArrayList<String> parole = new ArrayList<String>();

  /**
   * Costruttore della classe vocabolario non parametrico
   */
  public vocabolario(){
    this.parole = caricaVocabolario();
  }
  
  /**
   * Carica il vocabolario da un file di testo che rappresenta il vocabolario
   * Il vocabolario e' stato preso da un' altra repository su github pertanto potrebbe non essere aggiornato e non contenere tutte le parole
   * @return ArrayList di stringhe contenente tutte le parole del vocabolario
   */
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

  /**
   * Cerca una parola all'interno del vocabolario
   * La ricerca e' stata implementata in modo sequenziale in quanto il tempo impiegato non e' troppo elevato
   * E' possibile velocizzarlo implementando una ricerca binaria
   * @param parola_da_cercare La parola da cercare nel vocabolario
   * @return true se la parola e' presente nel vocabolario, false altrimenti
   */
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
