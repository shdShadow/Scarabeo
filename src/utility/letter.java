package utility;

public class letter {
  private char character;
  private Integer value;
  // Player fields will be set when the card will be randomly drawn
  private player player;
  // p will be set only when a player send a word.
  // p specifies the position in the game matrix
  private punto p;
  // GETTERS AND SETTERS

  public char getCharacter() { return character; }

  public player getPlayer() { return player; }

  public Integer getValue() { return value; }

  public punto getP() { return p; }

  public void setP(punto p) {
    this.p = p;
}

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

  // METHODS

  public String toString() {
    String s = "";
    s += "Char: " + character + " Value: " + value +
         " Player: " + player.getPlayer_name();
    return s;
  }
}
