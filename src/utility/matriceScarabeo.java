package utility;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class matriceScarabeo extends JFrame {
    private JLabel[][] grigliaDiGioco;    //griglia di gioco
    private letter[][] lettereGriglia;     //valori all'interno delle celle(lettere)
    private boolean turnoGiocatore1; // Flag per alternare i giocatori

    public matriceScarabeo() {
        grigliaDiGioco = new JLabel[17][17];
        lettereGriglia = new letter[17][17];
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

    public void aggiungiLettera(int riga, int colonna, char lettera, int valore,player player, punto p) {//metodo per aggiungere lettere nella posizione specificata che viene passata come attributo
        lettereGriglia[riga][colonna] = new letter(lettera, valore,player,p);
        mostraGriglia();
    }

    // Metodo per visualizzare la griglia con le lettere
    private void mostraGriglia() {
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                letter letteraCorrente = lettereGriglia[row][col];
                grigliaDiGioco[row][col].setText(Character.toString(letteraCorrente.getCharacter()));//set della lettera premuta nella cella selezionata grazie a setText

                if (letteraCorrente.getPlayer() != null) {    //controlla se la lettera ha un giocatore associato 
                    if (turnoGiocatore1 && letteraCorrente.getPlayer().getPlayer_name().equals("Player 1")) {
                        grigliaDiGioco[row][col].setForeground(Color.RED);//se la lettera appartiene al giocatore 1 la coloro di rosso
                    } else if (!turnoGiocatore1 && letteraCorrente.getPlayer().getPlayer_name().equals("Player 2")) {
                        grigliaDiGioco[row][col].setForeground(Color.BLUE); //se la lettera appartiene al giocatore 2 la coloro di blu
                    }
                }
            }
        }
        turnoGiocatore1 = !turnoGiocatore1;
    }
   

}