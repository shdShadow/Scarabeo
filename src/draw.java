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

    public void mostraMatrice() {

        for (int i = 0; i < matriceDiGioco.length; i++) { // stampo la griglia
            for (int j = 0; j < matriceDiGioco[i].length; j++) {
                System.out.print(matriceDiGioco[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Aggiorna la posizione nella matrice con un valore specifico
    public void updateMatrix(int righe, int colonne, char value) {
        if (righe >= 0 && righe < matriceDiGioco.length && colonne >= 0 && colonne < matriceDiGioco[0].length) { // controllo se la lettera inserita è compresa nella matrice
                                                                                                                 
            matriceDiGioco[righe][colonne] = value; // se è compresa
        } else {
            System.out.println("Posizione non valida"); // se non è compresa
        }
    }

}
