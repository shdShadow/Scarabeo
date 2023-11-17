package utility;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class matriceScarabeo extends JFrame {
    private JLabel[][] grigliaDiGioco;    //griglia di gioco
    private char[][] lettereGriglia;     //valori all'interno delle celle(lettere)
    private boolean turnoGiocatore1; // Flag per alternare i giocatori

    public matriceScarabeo() {
        grigliaDiGioco = new JLabel[17][17];
        lettereGriglia = new char[17][17];
        turnoGiocatore1 = true; // Inizia il primo giocatore

        setTitle("Gioco Scarabeo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //la applicazione termina quando la finestra viene chiusa
        setLayout(new GridLayout(17, 17));//permette di visualizzare interamente la griglia nella finestra

     
        for (int riga = 0; riga < 17; riga++) {//Inizizzo la matrice grigliaDiGioco
            for (int colonna = 0; colonna < 17; colonna++) {
                grigliaDiGioco[riga][colonna] = new JLabel();
                grigliaDiGioco[riga][colonna].setHorizontalAlignment(SwingConstants.CENTER);
                add(grigliaDiGioco[riga][colonna]);
            }
        }

        setSize(600, 600);
        setVisible(true);
    }

    
   

}