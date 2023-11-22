package utility;

import java.util.ArrayList;

public class letter {
  private char character;
  private Integer value;
  // Player fields will be set when the card will be randomly drawn
  private player player;
  // p will be set only when a player send a word.
  // p specifies the position in the game matrix
  private punto p;
  public boolean borrowed = false;
  // GETTERS AND SETTERS

  /**
   * Returns the character value of this Letter.
   *
   * @return the character value of this Letter
   */
  public char getCharacter() { return character; }

  public player getPlayer() { return player; }

  public Integer getValue() { return value; }

  public punto getP() { return p; }

  public void setP(punto p) {
    this.p = p;
}
  public void setCharacter(char character) { this.character = character; }

  public void setPlayer(player player) { this.player = player; }

  public Boolean getBorrowed() { return borrowed; }

  //   CONSTRUCTORS

  public letter() {
    character = ' ';
    value = 0;
    player = new player();
    p = new punto();
  }

  public letter(char _character, Integer _value, player _player, punto _p) {
    character = _character;
    value = _value;
    player = _player;
    p = _p;
  }

  public letter(char _character, Integer _value) {
    character = _character;
    value = _value;
    player = new player();
    p = new punto();
  }

  public letter(char _character, Integer _value, punto _p){
    character = _character;
    value = _value;
    player = new player();
    p = _p;
  }

  public letter (char _character, Integer _value, player _player, punto _p,Boolean _borrowed){
    character = _character;
    value = _value;
    player = _player;
    borrowed = _borrowed;
    p = _p;
  }

  public letter(char _character, punto _p){
    character = _character;
    value = 0;
    player = new player();
    p = _p;
  }

  public letter(char _character, punto _p, player _player){
    character = _character;
    value = 0;
    player = _player;
    p = _p;
  }

  // METHODS

  public String toString() {
    String s = "";
    s += "Char: " + character + " Value: " + value +
         " Player: " + player.getPlayer_name();
    return s;
  }

  public static String returnWord(ArrayList<letter> l){
    String s = "";
    for (letter letter : l) {
      s += letter.getCharacter();
    }
    return s;
  }


}
