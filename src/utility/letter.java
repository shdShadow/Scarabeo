package utility;

import java.util.ArrayList;

/**
 * La classe 'lettera' rappresenta una lettera in una partita. Contiene
 * informazioni riguardo al carattere, al suo valore, al giocatore, alla sua
 * posizione
 */
public class letter {
  // ATTRIBUTI
  /**
   * Rappresenta il carattere che verra' poi rappresentato in gioco
   */
  private char character;
  /**
   * Rappresenta il punteggio della lettera
   * Ha valore solo quando viene caricato tramite il file xml "letters_value.xml".
   * I client non hanno bisogno di sapere il valore della lettera in quanto il
   * punteggio viene poi calcolato dal server
   */
  private Integer value;
  /**
   * Rappresenta il giocatore che ha utilizzato la lettera
   */
  private player player;
  /**
   * Rappresenta la posizione della lettera all'interno della matrice di gioco
   */
  private punto p;
  /**
   * Rappresenta se la lettera e' stata presa in prestito.
   * Se e' true, la lettera proviene da un' altra lettera gia' presente sul campo
   * di gioco
   */
  public boolean borrowed = false;

  // GETTER E SETTER
  /**
   * Ritorna il carattere della lettera
   *
   * @return il carattere dell' oggetto lettera corrente
   * 
   */
  public char getCharacter() {
    return character;
  }

  /**
   * Restituisce il giocatore associato a questa lettera.
   *
   * @return l'oggetto player associato a questa lettera
   */
  public player getPlayer() {
    return player;
  }

  /**
   * Restituisce il punteggio della lettera.
   *
   * @return il punteggio della lettera
   */
  public Integer getValue() {
    return value;
  }

  /**
   * Restituisce la posizione della lettera nella matrice di gioco.
   *
   * @return l'oggetto punto che rappresenta la posizione della lettera nella
   *         matrice di gioco
   */
  public punto getP() {
    return p;
  }

  /**
   * Imposta il punteggio della lettera.
   *
   * @param p il punteggio della lettera
   */
  public void setP(punto p) {
    this.p = p;
  }

  /**
   * Imposta il carattere che verra' visualizzato della lettera.
   *
   * @param character il carattere della lettera
   */
  public void setCharacter(char character) {
    this.character = character;
  }

  /**
   * Imposta il player associato alla lettera.
   *
   * @param player l'oggetto player che verra' associato alla lettera
   */
  public void setPlayer(player player) {
    this.player = player;
  }

  /**
   * Ritorna il valore dell'atributo borrowed.
   *
   * @return il valore dell'atributo borrowed
   */
  public Boolean getBorrowed() {
    return borrowed;
  }

  // CONSTRUTTORI

  /**
   * Costruttore di default.
   */
  public letter() {
    character = ' ';
    value = 0;
    player = new player();
    p = new punto();
  }

  /**
   * Costruttore parametrico utilizzato dalla classe fileManager per caricare il
   * valore delle lettere leggendo dal file "letters_value.xml"
   *
   * @param _character il carattere della lettera
   * @param _value     il punteggio della lettera
   */
  public letter(char _character, Integer _value) {
    character = _character;
    value = _value;
    player = new player();
    p = new punto();
  }

  /**
   * Costruttore parametrico utilizzato dalla classe parserStringifier per parsare
   * un comando ricevuto
   *
   * @param _character il carattere della lettera
   * @param _player    il giocatore che ha utilizzato la lettera
   * @param _p         la posizione della lettere nella matrice di gioco
   * @param _value     il punteggio della lettera
   * @param _borrowed  se la lettera e' stata presa in prestito da un altra parola
   *                   gia' presente sul campo di gioco
   */
  public letter(char _character, Integer _value, player _player, punto _p, Boolean _borrowed) {
    character = _character;
    value = _value;
    player = _player;
    borrowed = _borrowed;
    p = _p;
  }

  /**
   * Costruttore parametrico utilizzato dalla classe drawMatrice in fase di
   * inserimento di una lettera
   *
   * @param _character il carattere della lettera
   * @param _p         la posizione della lettera nella matrice di gioco
   * @param _player    il giocatore che ha utilizzato la lettera
   */
  public letter(char _character, punto _p, player _player) {
    character = _character;
    value = 0;
    player = _player;
    p = _p;
  }

  /**
   * Ritorna una stringa formata dalla concatenazione dei caratteri della lista di
   * oggetti lettera fornita
   *
   * @param l la lista di oggetti lettera
   * @return una stringa rappresentante una parola generata dalla concatenazione
   *         dei caratteri della lista di oggetti lettera fornita
   */
  public static String returnWord(ArrayList<letter> l) {
    String s = "";
    for (letter letter : l) {
      s += letter.getCharacter();
    }
    return s;
  }
}
