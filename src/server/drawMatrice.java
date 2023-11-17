package server;
import utility.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class drawMatrice {
    public static letter[][] gameMatrix = new letter[17][17];
    public static void drawGrid() {
        JFrame frame = new JFrame("Grid Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setVisible(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(27, 111, 14));
                g.fillRect(0, 0, getWidth(), getHeight());

                int rows = 17; // Il numero di righe e colonne nello scarabeo e' di 17
                int cols = 17; 

                g.setColor(Color.BLACK);
                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);

                //Disegno i quadrati e i caratteri al loro interno
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int x = j * cellSize;
                        int y = i * cellSize;
                        g.drawRect(x, y, cellSize, cellSize);

                        //Ottengo la lettera alla posizione corrente
                        char letter = getLetterAtPosition(i, j);

                        //Disegno la lettera nella cella
                        //TODO Cambiare il colore del carattere in base al giocatore
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, cellSize / 2));
                        FontMetrics fm = g.getFontMetrics();
                        int letterWidth = fm.stringWidth(String.valueOf(letter));
                        int letterHeight = fm.getHeight();
                        int letterX = x + (cellSize - letterWidth) / 2;
                        int letterY = y + (cellSize - letterHeight) / 2 + fm.getAscent();
                        g.drawString(String.valueOf(letter), letterX, letterY);
                    }
                }
            }

            private char getLetterAtPosition(int row, int col) {
                //Setto un placeholder se la cella è vuota
                //Il placeholder puo essere qualsiasi cosa ma e' importante che non sia una lettera
                //Per evitare confusione con le lettere inserite
                if (gameMatrix[row][col] != null) {
                    return gameMatrix[row][col].getCharacter();
                }else{
                    return '.';
                }
            }
        };

        frame.add(panel);
    }

    public static void addToMatrix(ArrayList<letter> l){
        //Aggiungo le lettere nelle posizioni corrette nella matrice di gioco
        for (int i = 0; i < l.size(); i++) {
            gameMatrix[l.get(i).getP().getY()][l.get(i).getP().getX()] = l.get(i);
        }
        drawGrid();
    }
}
