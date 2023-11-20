public class draw {
    private char[][] matriceDiGioco;

    public draw(int righe, int colonne) {
        matriceDiGioco = new char[righe][colonne];

        initializeMatrix();// Inizializzo la matrice con dei valori di esempio
    }

    private void initializeMatrix() {
        for (int i = 0; i < matriceDiGioco.length; i++) { // scorro le righe
            for (int j = 0; j < matriceDiGioco[i].length; j++) { // scorro le colonne

                matriceDiGioco[i][j] = (char) ('A' + (int) (Math.random() * 26)); // genero lettere casuali dalla A alla
                                                                                  // Z coi valori ASCII
            }
        }
    }


