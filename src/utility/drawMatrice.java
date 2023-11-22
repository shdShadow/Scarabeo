
package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.CookieManager;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class drawMatrice extends JFrame implements KeyListener {
    private static final int MATRIX_SIZE = 17;
    private static final int CELL_SIZE = 35;
    private static final int GRID_SIZE = MATRIX_SIZE * CELL_SIZE;
    private Image offScreenImageDrawed = null;
    private Graphics offScreenGraphicsDrawed = null;
    private ArrayList<letter> buffer = new ArrayList<letter>();
    private ArrayList<letter> mano = new ArrayList<letter>();
    private String player_name = "";
    private String punteggio = "0";
    private letter[][] gameMatrix;
    private punto cursorPosition;
    private String mex = "Niente per ora";
    private String status = "None";
    public int counter = 0;

    @Override
    public void paint(final Graphics g) {
        final Dimension d = getSize();
        if (offScreenImageDrawed == null) {
            // Double-buffer: clear the offscreen image.
            offScreenImageDrawed = createImage(d.width, d.height);
        }
        offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics();
        offScreenGraphicsDrawed.setColor(Color.white);
        offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height);
        /////////////////////
        // Paint Offscreen //
        /////////////////////
        renderOffScreen(offScreenImageDrawed.getGraphics());
        g.drawImage(offScreenImageDrawed, 0, 0, null);
    }

    public drawMatrice() {
        gameMatrix = new letter[MATRIX_SIZE][MATRIX_SIZE];
        cursorPosition = new punto(0, 0);

        setTitle("Scarabeo");
        setSize(GRID_SIZE, GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        this.addKeyListener(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                finestraChiusa();
            }
        });
    }

    private void finestraChiusa(){
        System.out.println("Palle");
    }

    private void addToMatrix(ArrayList<letter> list) {
        for (int i = 0; i < list.size(); i++) {
            letter tmp = list.get(i);
            gameMatrix[tmp.getP().getY()][tmp.getP().getX()] = tmp;
        }
    }

    public void addToBuffer(letter l) {
        boolean trovata = false;
        for (int i = 0; i < buffer.size(); i++) {
            letter tmp = buffer.get(i);
            if (l.getP().getX() == tmp.getP().getX() && l.getP().getY() == tmp.getP().getY()) {
                buffer.get(i).setCharacter(l.getCharacter());
                break;
            }
        }
        if (trovata == false) {
            buffer.add(l);
        }
        addToMatrix(buffer);
        repaint();
    }

    public void renderOffScreen(Graphics g) {
        g.setColor(new Color(27, 111, 14));
        g.fillRect(0, 0, getWidth(), getHeight());

        int xOffset = CELL_SIZE; // Offset on the left side
        int yOffset = CELL_SIZE; // Offset on the top

        // Draw the grid
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                int x = xOffset + j * CELL_SIZE;
                int y = yOffset + i * CELL_SIZE;
                if (j == cursorPosition.getX() && i == cursorPosition.getY()) {
                    g.setColor(new Color(27, 111, 14));
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.RED);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                } else {
                    // Draw an empty cell
                    g.setColor(new Color(27, 111, 14));
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
                // Draw the letters in the matrix
                char letter = getLetterAtPosition(i, j);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
                FontMetrics fm = g.getFontMetrics();
                int letterWidth = fm.stringWidth(String.valueOf(letter));
                int letterHeight = fm.getHeight();
                int letterX = x + (CELL_SIZE - letterWidth) / 2;
                int letterY = y + (CELL_SIZE - letterHeight) / 2 + fm.getAscent();
                g.drawString(String.valueOf(letter), letterX, letterY);
            }
        }

        // Draw the label "Mano"
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
        FontMetrics fm = g.getFontMetrics();
        int labelWidth = fm.stringWidth("Mano");
        int labelHeight = fm.getHeight();
        int labelX = CELL_SIZE * 2 + CELL_SIZE * 17;
        int labelY = CELL_SIZE * 2;
        g.drawString("Mano", labelX, labelY);

        // Draw the letters in mano
        int manoX = CELL_SIZE * 2 + CELL_SIZE * 17;
        int manoY = CELL_SIZE * 2 + labelHeight;
        for (int i = 0; i < mano.size(); i++) {
            letter l = mano.get(i);
            g.setColor(Color.WHITE);
            g.drawRect(manoX, manoY, CELL_SIZE, CELL_SIZE);
            g.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
            FontMetrics fmMano = g.getFontMetrics();
            int letterWidthMano = fmMano.stringWidth(String.valueOf(l.getCharacter()));
            int letterHeightMano = fmMano.getHeight();
            int letterXMano = manoX + (CELL_SIZE - letterWidthMano) / 2;
            int letterYMano = manoY + (CELL_SIZE - letterHeightMano) / 2 + fmMano.getAscent();
            g.drawString(String.valueOf(l.getCharacter()), letterXMano, letterYMano);
            manoX += CELL_SIZE;
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
        FontMetrics fmLabel = g.getFontMetrics();
        int labelWidthNotifiche = fmLabel.stringWidth("Notifiche");
        int labelHeightNotifiche = fmLabel.getHeight();
        int labelXNotifiche = CELL_SIZE * 2 + CELL_SIZE * 17;
        int labelYNotifiche = CELL_SIZE * 5 + labelHeightNotifiche;
        g.drawString("Notifiche", labelXNotifiche, labelYNotifiche);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, CELL_SIZE / 2));
        FontMetrics fmMex = g.getFontMetrics();

        int contentHeightMex = fmMex.getHeight();
        int contentXMex = CELL_SIZE * 2 + CELL_SIZE * 17;
        int contentYMex = CELL_SIZE * 5 + labelHeightNotifiche + contentHeightMex;
        if (mex.equals("Niente per ora")) {
            if (status.equals("play")) {
                g.drawString("E' il tuo turno", contentXMex, contentYMex);
            } else if (status.equals("stop")) {
                g.drawString("Non e' il tuo turno", contentXMex, contentYMex);
            }
        } else {
            g.drawString(mex, contentXMex, contentYMex);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
        FontMetrics fmPunteggioLabel = g.getFontMetrics();
        int labelWidthPunteggio = fmPunteggioLabel.stringWidth("Punteggio:");
        int labelHeightPunteggio = fmPunteggioLabel.getHeight();
        int labelXPunteggio = CELL_SIZE * 2 + CELL_SIZE * 17;
        int labelYPunteggio = CELL_SIZE * 7 + labelHeightPunteggio;
        g.drawString("Punteggio: " + punteggio, labelXPunteggio, labelYPunteggio);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
        FontMetrics fmParolaCorrente = g.getFontMetrics();
        int labelWidthParolaCorrente = fmParolaCorrente.stringWidth("Parola corrente:");
        int labelHeightParolaCorrente = fmParolaCorrente.getHeight();
        int labelXParolaCorrente = CELL_SIZE * 2 + CELL_SIZE * 17;
        int labelYParolaCorrente = CELL_SIZE * 9 + labelHeightParolaCorrente;
        String parola = letter.returnWord(buffer);
        g.drawString("Parola corrente: " + parola, labelXParolaCorrente, labelYParolaCorrente);

    }

    private void undoPlayerMove() {
        for (letter letter : buffer) {
            if (letter.borrowed == false) {
                gameMatrix[letter.getP().getY()][letter.getP().getX()] = null;
                mano.add(letter);

            }

        }
        buffer.clear();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            if (cursorPosition.getY() - 1 >= 0) {
                cursorPosition.setY(cursorPosition.getY() - 1);
            }
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            if (cursorPosition.getY() + 1 <= MATRIX_SIZE) {
                cursorPosition.setY(cursorPosition.getY() + 1);
            }
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            if (cursorPosition.getX() - 1 >= 0) {
                cursorPosition.setX(cursorPosition.getX() - 1);
            }
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (cursorPosition.getX() + 1 <= MATRIX_SIZE) {
                cursorPosition.setX(cursorPosition.getX() + 1);
            }
        }

        // Check if the key pressed is the ESC key
        if (keyCode == KeyEvent.VK_ESCAPE) {
            // Perform the desired action when the ESC key is pressed
            // For example, close the application or reset the game
            // Replace the code below with your implementation.
            undoPlayerMove();
        }

        // Check if the key pressed is a letter in the alphabet
        char keyChar = e.getKeyChar();
        if (Character.isLetter(keyChar)) {
            if (status.equals("play")) {
                char uppercaseChar = Character.toUpperCase(keyChar);
                // Do something with the uppercaseChar
                // For example, add it to the buffer
                int x = cursorPosition.getX();
                int y = cursorPosition.getY();
                letter l = new letter(uppercaseChar, new punto(x, y), new player(player_name));
                letter gameL = gameMatrix[y][x];
                if (controllaSeInMano(l)) {
                    if (controllaSeCasellaOccupata(l)) {
                        if (l.getPlayer().getPlayer_name().equals(gameL.getPlayer().getPlayer_name())) {
                            if (l.getCharacter() == gameL.getCharacter()) {
                                l.borrowed = true;
                                addToBuffer(l);
                            } else {
                                mex = "Non puoi sovrascrivere una lettera. \r\n Se hai sbagliato a scrivere, premi 'esc'";
                            }
                        } else {
                            if (l.getCharacter() == gameL.getCharacter()) {
                                l.borrowed = true;
                                addToBuffer(l);
                            } else {
                                mex = "Non puoi sovrascrivere una lettera di un altro giocatore";
                            }
                        }
                    } else {
                        if (checkIfCellWritable(l)) {
                            addToBuffer(l);
                            removeFromCoda(l);
                            mex = "Niente per ora";
                            counter++;
                        } else {
                            mex = "Non puoi scrivere li";
                        }

                    }

                } else {
                    if (controllaSeCasellaOccupata(l)) {
                        if (l.getPlayer().getPlayer_name().equals(gameL.getPlayer().getPlayer_name())) {
                            if (l.getCharacter() == gameL.getCharacter()) {
                                l.borrowed = true;
                                addToBuffer(l);
                            } else {
                                mex = "Non puoi sovrascrivere una lettera. \r\n Se hai sbagliato a scrivere, premi 'esc'";
                            }
                        } else {
                            if (l.getCharacter() == gameL.getCharacter()) {
                                l.borrowed = true;
                                addToBuffer(l);
                            } else {
                                mex = "Non puoi sovrascrivere una lettera di un altro giocatore";
                            }
                        }
                    } else {
                        mex = "Non hai questa lettera in mano";
                    }

                }
            } else {
                mex = "Non e' il tuo turno";
            }

        }

        System.out.println(cursorPosition.getX() + " " + cursorPosition.getY());
        repaint();
    }

    private char getLetterAtPosition(int row, int col) {
        // TODO: Implement the logic to get the letter at the given position
        // You can use the row and col values to access the corresponding punto object
        // and retrieve the letter from it.
        // Replace the return statement below with your implementation.
        if (gameMatrix[row][col] != null) {
            return gameMatrix[row][col].getCharacter();
        } else {
            return ' ';
        }
    }

    private boolean controllaSeCasellaOccupata(letter l) {
        if (gameMatrix[l.getP().getY()][l.getP().getX()] != null) {
            return true;
        }
        return false;
    }

    private boolean controllaSeInMano(letter l) {
        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).getCharacter() == l.getCharacter()) {
                return true;
            }
        }
        return false;
    }

    private void removeFromCoda(letter l) {
        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).getCharacter() == l.getCharacter()) {
                mano.remove(i);
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implement your logic for key released event
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implement your logic for key typed event
    }

    public void setMano(ArrayList<letter> mano) {
        this.mano = mano;
    }

    public void setStatus(String status) {
        this.status = status;
        buffer.clear();
        repaint();
    }

    public void setMex(String[] s) {
        if (s[0].equals("error")) {
            mex = s[1];
            undoPlayerMove();
            repaint();
        }

    }

    public void setMex(String s) {
        mex = s;
        repaint();
    }

    public ArrayList<letter> getBuffer() {
        return buffer;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setPoints(Integer points) {
        this.punteggio = points.toString();
        repaint();
    }

    public void UpdateMatrix(ArrayList<letter> list) {
        for (int i = 0; i < list.size(); i++) {
            letter tmp = list.get(i);
            gameMatrix[tmp.getP().getY()][tmp.getP().getX()] = tmp;
        }
        repaint();
    }

    public void addToMano(ArrayList<letter> list) {
        for (letter letter : list) {
            mano.add(letter);
        }
        repaint();
    }

    public boolean checkIfCellWritable(letter l) {
        boolean viable = true;
        int y = l.getP().getY();
        int x = l.getP().getX();
        //check nord-ovest
        
        if (gameMatrix[y - 1][x] != null && gameMatrix[y][x - 1] != null) {
            viable = false;
        }
        //check nord-est
        if (gameMatrix[y - 1][x] != null && gameMatrix[y][x + 1] != null) {
            viable = false;
        }
        //check sud-ovest
        if (gameMatrix[y + 1][x] != null && gameMatrix[y][x - 1] != null) {
            viable = false;
        }
        //check sud-est
        if (gameMatrix[y + 1][x] != null && gameMatrix[y][x + 1] != null) {
            viable = false;
        }
        return viable;
    }

    public boolean checkIfLetterOfBufferExists(letter l, String direction){
        boolean found = false;
        int y = 0;
        int x = 0;
        switch(direction){
            case "up":
            y = l.getP().getY();
            for (letter letter : buffer) {
                if (letter.getP().getY() == y - 1){
                    found = true;
                    break;
                }
            }
            break;
            case "down":
            y = l.getP().getY();
            for (letter letter : buffer) {
                if (letter.getP().getY() == y + 1){
                    found = true;
                    break;
                }
            }
            break;
            case "left":
            x = l.getP().getX();
            for (letter letter : buffer) {
                if (letter.getP().getX() == x - 1){
                    found = true;
                    break;
                }
            }
            break;
            case "right":
            x = l.getP().getX();
            for (letter letter : buffer) {
                if (letter.getP().getX() == x + 1){
                    found = true;
                    break;
                }
            }
            break;
        }

        return found;
    }

    public void resetMano(){
        mano.clear();
    }

}
