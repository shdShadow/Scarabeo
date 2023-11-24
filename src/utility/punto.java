package utility;
/**
 * Classe che rappresenta un punto. Sono contenute le coordinate x e y di un punto all'interno della matrice di gioco
 */
public class punto {
  // ATTRIBUTI

  /**
   * attributo contenente le coordinate x del punto
   */
  private Integer x;
  /**
   * attributo contenente le coordinate y del punto
   */
  private Integer y;

  // COSTRUTTORI

  /**
   * Costruttore di default
   *
   */
  public punto() {
    x = 0;
    y = 0;
  }

  /**
   * Costruttore parametrico
   *
   * @param _x coordinata x del punto
   * @param _y coordinata y del punto
   */
  public punto(Integer _x, Integer _y) {
    x = _x;
    y = _y;
  }

  // GETTER E SETTER

  /**
   * Ritorna la coordinata x del punto
   *
   * @return la coordinata x del punto
   */
  public Integer getX() {
    return x;
  }

  /**
   * Ritorna la coordinata y del punto
   *
   * @return la coordinata y del punto
   */
  public Integer getY() {
    return y;
  }

  /**
   * Setta la coordinata x del punto
   *
   * @param x coordinata x del punto
   */
  public void setX(Integer x) {
    this.x = x;
  }

  /**
   * Setta la coordinata y del punto
   *
   * @param y coordinata y del punto
   */
  public void setY(Integer y) {
    this.y = y;
  }
}
