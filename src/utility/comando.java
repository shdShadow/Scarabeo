package utility;

import java.util.ArrayList;

public class comando {
  private String exec;
  private ArrayList<letter> l;

  public comando() {
    exec = "";
    l = new ArrayList<letter>();
  }

  public comando(String _exec, ArrayList<letter> _l) {
    exec = _exec;
    l = _l;
  }

  public ArrayList<letter> getL() { return l; }
  public String getExec() { return exec; }
  public void setL(ArrayList<letter> l) { this.l = l; }
  public void setExec(String exec) { this.exec = exec; }
}
