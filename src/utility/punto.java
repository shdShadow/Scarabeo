package utility;
public class punto {
  private Integer x;
  private Integer y;
  public punto() {
    x = 0;
    y = 0;
  }

  public punto(Integer _x, Integer _y) {
    x = _x;
    y = _y;
  }

  public Integer getX() { return x; }
  public Integer getY() { return y; }
  public void setX(Integer x) { this.x = x; }
  public void setY(Integer y) { this.y = y; }
}
