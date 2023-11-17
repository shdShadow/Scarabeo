package utility;

import java.util.ArrayList;

public class comando {
  //comando inviato dal client al server
  private String exec;
  //lista di lettere in caso di comando 'add', quindi di parola aggiunta
  private ArrayList<letter> l;

  //COSTRUTTORI
  public comando() {
    exec = "";
    l = new ArrayList<letter>();
  }

  public comando(String _exec, ArrayList<letter> _l) {
    exec = _exec;
    l = _l;
  }
  //GETTERS E SETTERS
  public ArrayList<letter> getL() { return l; }
  public String getExec() { return exec; }
  public void setL(ArrayList<letter> l) { this.l = l; }
  public void setExec(String exec) { this.exec = exec; }
}
