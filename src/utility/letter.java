package utility;

public class letter {
  private char character;
  private Integer value;
  // Player fields will be set when the card will be randomly drawn
  private player player;

  // GETTERS AND SETTERS

  public char getCharacter() { return character; }

  public player getPlayer() { return player; }

  public Integer getValue() { return value; }

  //   CONSTRUCTORS

  public letter() {
    character = ' ';
    value = 0;
    player = new player();
  }

  public letter(char _character, Integer _value, player _player) {
    character = _character;
    value = _value;
    player = _player;
  }

  public letter(char _character, Integer _value) {
    character = _character;
    value = _value;
    player = new player();
  }

  // METHODS

  public String toString() {
    String s = "";
    s += "Char: " + character + " Value: " + value +
         " Player: " + player.getPlayer_name();
    return s;
  }
}
