package utility;

public class player {
    private String player_name;
    private Integer total_points;
    private letter[] current_letters;

    // GETTERS AND SETTERS

    public Integer getTotal_points() {
        return total_points;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public letter[] getCurrent_letters() {
        return current_letters;
    }

    // CONTSTRUCTORS

    public player() {
        player_name = "";
        total_points = 0;
        current_letters = new letter[8];
    }

    public player(String _player_name, Integer _total_points, letter[] _current_letters) {
        this.player_name = _player_name;
        this.total_points = _total_points;
        this.current_letters = _current_letters;
    }

    public player(String _player_name) {
        this.player_name = _player_name;
        this.total_points = 0;
        this.current_letters = new letter[8];
    }

    // METHODS

}
