import java.util.Random;

public class matrice3x3 {

    private char[][] matrice;

    // Costruttore che genera la matrice 3x3 di lettere casuali
    public matrice3x3() {
        matrice = generaMatrice();
    }

    // Genera una matrice 3x3 di lettere casuali
    private char[][] generaMatrice() {
        char[][] nuovaMatrice = new char[3][3];                //matrice di 3x3 char 
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            
                nuovaMatrice[i][j] = (char) (random.nextInt(26) + 'a');  //genero una lettera casuale dalla a alla z usando codice ascii
            }
        }

        return nuovaMatrice;
    }

    
    public void stampaMatrice() {        //metodo per stampare la matrice sotto forma di griglia
        System.out.println("Matrice 3x3 di lettere random:");
        System.out.println("---------------------");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + matrice[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("---------------------");
        }
    }

    
}
