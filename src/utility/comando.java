package utility;

import java.util.ArrayList;

/**
 * La classe 'comando' rappresenta un comando inviato dal client al server o
 * viceversa
 * Contiene informazioni riguardo al comando da eseguire inerente alla lista di
 * lettere annessa
 */
public class comando {
  // ATTRIBUTI

  /**
   * Rappresenta il comando che verra' eseguito
   * 
   */
  private String exec;
  /**
   * Rappresenta la lista di lettere che verra' utilizzata in base al comando
   * richiesto
   * 
   */
  private ArrayList<letter> l;

  // COSTRUTTORI
  /**
   * Costruttore di default
   * 
   */
  public comando() {
    exec = "";
    l = new ArrayList<letter>();
  }

  /**
   * Costruttore parametrico
   * 
   * @param _exec comando da eseguire
   * @param _l    lista di lettere da utilizzare
   */
  public comando(String _exec, ArrayList<letter> _l) {
    exec = _exec;
    l = _l;
  }

  // GETTER E SETTER
  /**
   * Ritorna la lista di lettere
   * 
   * @return la lista di lettere
   */
  public ArrayList<letter> getL() {
    return l;
  }

  /**
   * Ritorna il comando da eseguire
   * 
   * @return il comando da eseguire
   */
  public String getExec() {
    return exec;
  }

  /**
   * Imposta la lista di lettere
   * 
   * @param l la lista di lettere
   */
  public void setL(ArrayList<letter> l) {
    this.l = l;
  }

  /**
   * Imposta il comando da eseguire
   * 
   * @param exec il comando da eseguire
   */
  public void setExec(String exec) {
    this.exec = exec;
  }
}
