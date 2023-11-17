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

                int rows = 17; // Number of rows in the grid
                int cols = 17; // Number of columns in the grid

                g.setColor(Color.BLACK);
                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);

                // Draw squares and characters
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int x = j * cellSize;
                        int y = i * cellSize;
                        g.drawRect(x, y, cellSize, cellSize);

                        // Get the letter at the current position
                        char letter = getLetterAtPosition(i, j);

                        // Draw the letter inside the cell
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
                // TODO: Implement the logic to get the letter at the given position
                // You can use the row and col values to access the corresponding punto object
                // and retrieve the letter from it.
                // Replace the return statement below with your implementation.
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
        for (int i = 0; i < l.size(); i++) {
            gameMatrix[l.get(i).getP().getY()][l.get(i).getP().getX()] = l.get(i);
        }
        drawGrid();
    }
}
