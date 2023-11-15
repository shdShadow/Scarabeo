package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class vocabolario {
  private ArrayList<String> parole = new ArrayList<String>();

  public vocabolario(){
    this.parole = caricaVocabolario();
  }

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

  public boolean cercaParola(String parola_da_cercare){
    long init = System.currentTimeMillis();
    int index = ricercaBinaria(parola_da_cercare);
      boolean found = false;
    for(int i =0 ;i < parole.size(); i++){
      if (parole.get(i).equals(parola_da_cercare)){
        found = true;
        long end = System.currentTimeMillis();
        long tot = end - init;
        System.out.println("Tempo impiegato per cercare parola: " + tot);
        break;
      }
    }
    return found;
    // if(index != -1){
    //   return true;
    // }else{
    //   return false;
    // }
  }
  private int ricercaBinaria(String p){
    int low = 0;
    int high = parole.size()-1;

    while(low <= high){
      int middle = (low + high)/2;
      String linea_mid = parole.get(middle);
      int confronto = p.compareTo(linea_mid);
      if(confronto == 0){
        return middle;
      }else if (confronto < 0){
        high = middle - 1;
      }else{
        high = middle + 1;
      }
    }
    return -1;
  }
}
