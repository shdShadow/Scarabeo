package utility;
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
  //COSTRUTTORI
  public punto() {
    x = 0;
    y = 0;
  }

  public punto(Integer _x, Integer _y) {
    x = _x;
    y = _y;
  }
  //GETTERS E SETTERS
  public Integer getX() { return x; }
  public Integer getY() { return y; }
  public void setX(Integer x) { this.x = x; }
  public void setY(Integer y) { this.y = y; }
}
