package utility;
/**
 * Classe che rappresenta un giocatore. In essa sono contenuti il nome del giocatore e il suo punteggio
 */
public class player {
    //ATTRIBUTI
    
    /**
     * Rappresenta il nome di un giocatore
     */
    private String player_name;
    /**
     * Rappresenta il punteggio totale di un giocatore
     */
    private Integer total_points;

    // GETTER AND SETTER


    /**
     * Ritorna il punteggio totale di un giocatore
     *
     * @return il punteggio totale del giocatore
     */
    public Integer getTotal_points() {
        return total_points;
    }
  
    /**
     * Ritorna il nome del giocatore
     *
     * @return il nome del giocatore
     */
    public String getPlayer_name() {
        return player_name;
    }


    // COSTRUTTORI

    /**
     * Costruttore di default
     */
    public player() {
        player_name = "";
        total_points = 0;

    }
    /**
     * Costruttore parametrico
     *
     * @param _player_name nome del giocatore
     */
    public player(String _player_name) {
        this.player_name = _player_name;
        this.total_points = 0;

    }

}
