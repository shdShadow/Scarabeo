
package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * La classe drawMatrice contiene un JFrame che mostra a schermo la matrice di
 * lettere presenti sul campo di gioco
 * Implementa l'interfaccia KeyListener per gestire l'input utente
 * Implementa l'interfaccia WindowListener per gestire la chiusura della
 * finestra ed eseguire una serie di operazioni predefinite
 */
public class drawMatrice extends JFrame implements KeyListener {
    // ATTRIBUTI

    /**
     * Immagine disegnata "off-screen" usata per disegnare l'interfaccia a schermo
     * tramite la tecnica del double-buffering
     */
    private Image offScreenImageDrawed = null;
    /**
     * Oggetto Graphics usato per disegnare l'immagine offScreenImageDrawed
     */
    private Graphics offScreenGraphicsDrawed = null;
    /**
     * ArrayList di lettere che contiene le lettere che l'utente ha usato nel turno
     * corrente
     */
    private ArrayList<letter> buffer = new ArrayList<letter>();
    /**
     * ArrayList di lettere che contiene le lettere che l'utente ha in mano
     */
    private ArrayList<letter> mano = new ArrayList<letter>();
    /**
     * Stringa che contiene il nome del giocatore assegnato poi alla lettera
     * generata in fase di input
     */
    private String player_name = "";
    /**
     * Stringa che contiene il punteggio del giocatore
     */
    private String punteggio = "0";
    /**
     * Matrice di lettere che contiene le lettere presenti sul campo di gioco le
     * quali verranno poi disegnate a schermo
     */
    private letter[][] gameMatrix;
    /**
     * Oggetto punto che contiene le coordinate della cella in cui si trova il
     * cursore
     */
    private punto cursorPosition;
    /**
     * Stringa che contiene il messaggio che verra' mostrato a schermo nella sezione
     * "notifiche"
     */
    private String mex = "Niente per ora";
    /**
     * Stringa che contiene lo stato del turno corrente
     * play = turno del giocatore
     * stop = turno dell' altro giocatore
     */
    private String status = "None";

    // COSTRUTTORI
    /**
     * Costruttore non parametrico
     * Inizializza la matrice di gioco e la posizione del cursore
     * Inizializza la finestra di gioco impostandone la dimensione, il titolo,
     * l'operazione di chiusura e la possibila di ridimensionarla
     * Aggiunge un KeyListener alla finestra per gestire l'input utente
     */
    public drawMatrice() {
        gameMatrix = new letter[settings.MATRIX_SIZE][settings.MATRIX_SIZE];
        cursorPosition = new punto(0, 0);

        setTitle("Scarabeo");
        setSize(settings.GRID_SIZE, settings.GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        this.addKeyListener(this);

    }

    // METODI

    /**
     * Metodo che aggiunge al buffer di inserimento utente
     * Il metodo controlla se la lettera e' gia' presente nel buffer, se si allora
     * la sovrascrive
     * Se la lettera non e' presente nel buffer, allora la aggiunge in coda
     * 
     * @param l Lettera da aggiungere
     */
    public void addToBuffer(letter l) {
        // Variabile d'appoggio
        boolean trovata = false;
        // Ciclo all'interno degli elementi del buffer
        for (int i = 0; i < buffer.size(); i++) {
            // Ottengo la lettera in posizione i
            letter tmp = buffer.get(i);
            // Se la posizione della lettera da inserire e' uguale alla posizione della
            // lettera ad indice i del buffer significa che devo sovrascrivere
            if (l.getP().getX() == tmp.getP().getX() && l.getP().getY() == tmp.getP().getY()) {
                buffer.get(i).setCharacter(l.getCharacter());
                break;
            }
        }
        // Se non esiste nessuna lattera con la stessa posizione all'interno del buffer
        // la aggiungo in coda
        if (trovata == false) {
            buffer.add(l);
        }
        // Metodo che aggiunge le lettera del buffer alla matrice di gioco in modo da
        // visualizzarle a schermo
        addToMatrix(buffer);
        repaint();
    }

    /**
     * Metodo che aggiunge le lettere del buffer alla matrice di gioco per
     * visualizzarle a schermo
     * 
     * @param list ArrayList di lettere da aggiungere alla matrice di gioco
     */
    private void addToMatrix(ArrayList<letter> list) {
        // Ciclo all'interno degli elementi del buffer
        for (int i = 0; i < list.size(); i++) {
            // Ottengo la lettera ad indice i
            letter tmp = list.get(i);
            // Aggiungo la lettera alla matrice di gioco in base ai valori del suo oggetto
            // punto
            gameMatrix[tmp.getP().getY()][tmp.getP().getX()] = tmp;
        }
    }

    /**
     * Override del metodo paint
     * Disegna l'interfaccia a schermo chiamando un secondo metodo renderOffScreen
     * che disegna l'interfaccia su un'immagine off-screen che verra' poi disegnata
     * in seguito
     * 
     * @param g Oggetto Graphics usato per disegnare l'immagine offScreenImageDrawed
     */
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

    /**
     * Metodo che disegna l'interfaccia grafica a schermo
     * 
     * @param g Oggetto Graphics usato per disegnare l'immagine offScreenImageDrawed
     */
    public void renderOffScreen(Graphics g) {
        // Imposta lo sfondo della finestra
        g.setColor(settings.bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Offset sul lato sinistro
        int xOffset = settings.CELL_SIZE;
        // Offset dal lato superiore
        int yOffset = settings.CELL_SIZE;

        // Disegno la griglia di gioco come una serie di linee di celle
        for (int i = 0; i < settings.MATRIX_SIZE; i++) {
            for (int j = 0; j < settings.MATRIX_SIZE; j++) {
                int x = xOffset + j * settings.CELL_SIZE;
                int y = yOffset + i * settings.CELL_SIZE;
                // Controllo se la posizione della cella che sto per disegnare e' ugale alla
                // posizione del cursore
                // Se e' uguale, disegno la cella in rosso
                if (j == cursorPosition.getX() && i == cursorPosition.getY()) {
                    g.setColor(settings.bgColor);
                    g.fillRect(x, y, settings.CELL_SIZE, settings.CELL_SIZE);
                    g.setColor(Color.RED);
                    g.drawRect(x, y, settings.CELL_SIZE, settings.CELL_SIZE);
                }
                // Altrimenti la disegno bianca come tutte le altre
                else {
                    g.setColor(settings.bgColor);
                    g.fillRect(x, y, settings.CELL_SIZE, settings.CELL_SIZE);
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, settings.CELL_SIZE, settings.CELL_SIZE);
                }
                // Disegno le lettere presenti nella matrice di gioco
                char letter = getLetterAtPosition(i, j);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, settings.CELL_SIZE / 2));
                FontMetrics fm = g.getFontMetrics();
                int letterWidth = fm.stringWidth(String.valueOf(letter));
                int letterHeight = fm.getHeight();
                int letterX = x + (settings.CELL_SIZE - letterWidth) / 2;
                int letterY = y + (settings.CELL_SIZE - letterHeight) / 2 + fm.getAscent();
                g.drawString(String.valueOf(letter), letterX, letterY);
            }
        }

        // Disegno la label per la sezione della mano del giocatore
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, settings.CELL_SIZE / 2));
        FontMetrics fm = g.getFontMetrics();
        int labelHeight = fm.getHeight();
        int labelX = settings.CELL_SIZE * 2 + settings.CELL_SIZE * 17;
        int labelY = settings.CELL_SIZE * 2;
        g.drawString("Mano", labelX, labelY);

        // Draw the letters in mano
        int manoX = settings.CELL_SIZE * 2 + settings.CELL_SIZE * 17;
        int manoY = settings.CELL_SIZE * 2 + labelHeight;
        for (int i = 0; i < mano.size(); i++) {
            // rappresento ogni lettera della mano come un quadrato
            letter l = mano.get(i);
            g.setColor(Color.WHITE);
            g.drawRect(manoX, manoY, settings.CELL_SIZE, settings.CELL_SIZE);
            g.setFont(new Font("Arial", Font.BOLD, settings.CELL_SIZE / 2));
            FontMetrics fmMano = g.getFontMetrics();
            int letterWidthMano = fmMano.stringWidth(String.valueOf(l.getCharacter()));
            int letterHeightMano = fmMano.getHeight();
            int letterXMano = manoX + (settings.CELL_SIZE - letterWidthMano) / 2;
            int letterYMano = manoY + (settings.CELL_SIZE - letterHeightMano) / 2 + fmMano.getAscent();
            g.drawString(String.valueOf(l.getCharacter()), letterXMano, letterYMano);
            manoX += settings.CELL_SIZE;
        }

        // Disegno la label per la sezione delle notifiche
        // Presenta informazioni sul turno corrente e sui messaggi di errore
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, settings.CELL_SIZE / 2));
        FontMetrics fmLabel = g.getFontMetrics();
        int labelHeightNotifiche = fmLabel.getHeight();
        int labelXNotifiche = settings.CELL_SIZE * 2 + settings.CELL_SIZE * 17;
        int labelYNotifiche = settings.CELL_SIZE * 5 + labelHeightNotifiche;
        g.drawString("Notifiche", labelXNotifiche, labelYNotifiche);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, settings.CELL_SIZE / 2));
        FontMetrics fmMex = g.getFontMetrics();

        int contentHeightMex = fmMex.getHeight();
        int contentXMex = settings.CELL_SIZE * 2 + settings.CELL_SIZE * 17;
        int contentYMex = settings.CELL_SIZE * 5 + labelHeightNotifiche + contentHeightMex;
        if (mex.equals("Niente per ora")) {
            if (status.equals("play")) {
                g.drawString("E' il tuo turno", contentXMex, contentYMex);
            } else if (status.equals("stop")) {
                g.drawString("Non e' il tuo turno", contentXMex, contentYMex);
            }
        } else {
            g.drawString(mex, contentXMex, contentYMex);
        }

        // Disegno la label per la sezione del punteggio
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, settings.CELL_SIZE / 2));
        FontMetrics fmPunteggioLabel = g.getFontMetrics();
        int labelHeightPunteggio = fmPunteggioLabel.getHeight();
        int labelXPunteggio = settings.CELL_SIZE * 2 + settings.CELL_SIZE * 17;
        int labelYPunteggio = settings.CELL_SIZE * 7 + labelHeightPunteggio;
        g.drawString("Punteggio: " + punteggio, labelXPunteggio, labelYPunteggio);
        // Disegno la label per la sezione della parola corrente, cioe' la parola che
        // l'utente sta ancora scrivendo nel turno corrente
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, settings.CELL_SIZE / 2));
        FontMetrics fmParolaCorrente = g.getFontMetrics();
        int labelHeightParolaCorrente = fmParolaCorrente.getHeight();
        int labelXParolaCorrente = settings.CELL_SIZE * 2 + settings.CELL_SIZE * 17;
        int labelYParolaCorrente = settings.CELL_SIZE * 9 + labelHeightParolaCorrente;
        String parola = letter.returnWord(buffer);
        g.drawString("Parola corrente: " + parola, labelXParolaCorrente, labelYParolaCorrente);

    }

    /**
     * Metodo che annulla l'ultima mossa dell'utente
     * Rimuove le lettere presenti nel buffer dalla matrice di gioco
     * e le aggiunge alla mano del giocatore
     * Se la lettera ha l'attributo borrowed a true, significa che la lettera e'
     * stata presa da un parola gia presente sul campo prima che l'utente iniziasse
     * a scrivere
     * pertanto non va ne riaggiunta la mano, ne rimossa dalla matrice di gioco
     */
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

    // IMPLEMENTAZIONE DEI METODI DELL'INTERFACCIA KEYLISTENER
    /**
     * Override del metodo keyPressed
     * Gestisce l'input utente a seguito della pressione di un pulsante da parte
     * dell'utente
     * 
     * @param e Oggetto KeyEvent che contiene l'evento generato dall'utente
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Muovi la posizione del cursore in base al pulsante premuto
        // Se l'utente preme la freccia su, il cursore si muove verso l'alto nel caso
        // non sia gia ai bordi del campo di gioco, altrimenti rimane fermo
        if (keyCode == KeyEvent.VK_UP) {
            if (cursorPosition.getY() - 1 >= 0) {
                cursorPosition.setY(cursorPosition.getY() - 1);
            }
        }
        // Se l'utente preme la freccia giu, il cursore si muove verso il basso nel caso
        // non sia gia ai bordi del campo di gioco, altrimenti rimane fermo
        if (keyCode == KeyEvent.VK_DOWN) {
            if (cursorPosition.getY() + 1 < settings.MATRIX_SIZE) {
                cursorPosition.setY(cursorPosition.getY() + 1);
            }
        }
        // Se l'utente preme la freccia sinistra, il cursore si muove verso sinistra nel
        // caso non sia gia ai bordi del campo di gioco, altrimenti rimane fermo
        if (keyCode == KeyEvent.VK_LEFT) {
            if (cursorPosition.getX() - 1 >= 0) {
                cursorPosition.setX(cursorPosition.getX() - 1);
            }
        }
        // Se l'utente preme la freccia destra, il cursore si muove verso destra nel
        // caso non sia gia ai bordi del campo di gioco, altrimenti rimane fermo
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (cursorPosition.getX() + 1 < settings.MATRIX_SIZE) {
                cursorPosition.setX(cursorPosition.getX() + 1);
            }
        }

        // Se l'utente preme il tasto esc, viene chiamato il metodo undoPlayerMove che
        // annulla l'ultima mossa
        if (keyCode == KeyEvent.VK_ESCAPE) {
            undoPlayerMove();
        }

        char keyChar = e.getKeyChar();
        // Controllo che il tasto premuto dall'utente sia effettivamente una lettera
        if (Character.isLetter(keyChar)) {
            // Controllo che il turno sia effettivamente del giocatore corrente
            // Se non lo e', ogni forma di inserimento e' bloccata
            if (status.equals("play")) {
                // Ottengo il carattere scritto dall'utente e lo converto in maiuscolo
                char uppercaseChar = Character.toUpperCase(keyChar);
                int x = cursorPosition.getX();
                int y = cursorPosition.getY();
                // Creo un oggetto lettera con i valori di x e y del cursore, il carattere
                // inserito dall'utente e il nome del giocatore impostati
                letter l = new letter(uppercaseChar, new punto(x, y), new player(player_name));
                // Metto in una variabile d'appoggio la lettera presente nella matrice di gioco
                // alla posizione del cursore
                letter gameL = gameMatrix[y][x];
                // Tramite metodo esterno controllo se la lettera e' presente nella mano del
                // giocatore
                // Se non lo e', non puo' essere inserita
                if (controllaSeInMano(l)) {
                    // Se la casella alla posizione di inserimento e' gia occupata...
                    if (controllaSeCasellaOccupata(l)) {
                        // controllo che la lettera alla posizione del cursore abbia lo stesso player di
                        // quella che sto per andare ad inserire
                        // Se e' cosi, controllo che la lettera alla posizione del cursore abbia lo
                        // stesso carattere di quella che sto per andare ad inserire
                        // Altrimenti, se la lettera alla posizione del cursore ha un player diverso da
                        // quella che sto per andare ad inserire, controllo che abbiano lo stesso
                        // carattere
                        // Se hanno lo stesso carattere vuol dire che la sto prendendo "prestito" per
                        // scrivere una mia parola, altrimenti vuol dire che sto cercando di
                        // sovrascrivere una lettera di un altro giocatore
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
                    }
                    // Se la casella alla posizione di inserimento non e' occupata...
                    else {
                        // Controllo che la casella sia scrivibile
                        // Se non lo e', blocco l'utente
                        if (checkIfCellWritable(l)) {
                            addToBuffer(l);
                            removeFromCoda(l);
                            mex = "Niente per ora";
                        } else {
                            mex = "Non puoi scrivere li";
                        }

                    }

                }
                // Se la lettera che voglio inserire non e' presente nella mia mano, controllo
                // che la casella alla posizione di inserimento sia occupata
                else {
                    // Se la casella alla posizione di inserimento e' occupata...
                    if (controllaSeCasellaOccupata(l)) {
                        // controllo che la lettera alla posizione del cursore abbia lo stesso player di
                        // quella che sto per andare ad inserire
                        if (l.getPlayer().getPlayer_name().equals(gameL.getPlayer().getPlayer_name())) {
                            // Se hanno lo stesso carattere vuol dire che la sto prendendo "prestito" per
                            // scrivere una mia parola, altrimenti vuol dire che sto cercando di
                            // sovrascrivere una lettera di un altro giocatore
                            if (l.getCharacter() == gameL.getCharacter()) {
                                l.borrowed = true;
                                addToBuffer(l);
                            } else {
                                mex = "Non puoi sovrascrivere una lettera. \r\n Se hai sbagliato a scrivere, premi 'esc'";
                            }
                        }
                        // Se la lettera alla posizione del cursore ha un player diverso da quella che
                        // sto per andare ad inserire, controllo che abbiano lo stesso carattere
                        else {
                            // Se hanno lo stesso carattere la prendo in prestito per scrivere una mia
                            // parola, altrimenti vuol dire che sto cercando di sovrascrivere una lettera di
                            // un altro giocatore
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

    /**
     * Metodo che restituisce il carattere presente nella matrice di gioco alla
     * posizione indicata
     * 
     * @param row Riga della matrice di gioco
     * @param col Colonna della matrice di gioco
     * @return Carattere presente nella matrice di gioco alla posizione indicata. Se
     *         la casella e' vuota, restituisce uno spazio vuoto
     */
    private char getLetterAtPosition(int row, int col) {
        if (gameMatrix[row][col] != null) {
            return gameMatrix[row][col].getCharacter();
        } else {
            return ' ';
        }
    }

    /**
     * Controlla che la casella in cui l'utente vuole scrivere sia occupata o meno
     * 
     * @param l la lettera che l'utente sta cercando di inserire
     * @return true se la casella e' occupata, altrimenti false
     */
    private boolean controllaSeCasellaOccupata(letter l) {
        if (gameMatrix[l.getP().getY()][l.getP().getX()] != null) {
            return true;
        }
        return false;
    }

    /**
     * Controlla se la lettera che l'utente sta cercando di inserire e' presente
     * nella sua mano
     * 
     * @param l la lettera che l'utente sta cercando di inserire
     * @return true se la lettera e' presente nella mano del giocatore, altrimenti
     *         false
     */
    private boolean controllaSeInMano(letter l) {
        for (int i = 0; i < mano.size(); i++) {
            if (mano.get(i).getCharacter() == l.getCharacter()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rimuove la lettera dalla mano del giocatore
     * 
     * @param l la lettera che l'utente sta cercando di inserire
     */
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
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    // GETTER E SETTER

    /**
     * Imposta la mano di lettere del giocatore
     * 
     * @param mano l'ArrayList di lettere da impostare come mano del giocatore
     */
    public void setMano(ArrayList<letter> mano) {
        this.mano = mano;
    }

    /**
     * Imposta lo stato del turno corrente
     * 
     * @param status il nuovo status da impostare
     */
    public void setStatus(String status) {
        this.status = status;
        buffer.clear();
        repaint();
    }

    /**
     * Imposta il messaggio da mostrare nella sezione notifiche
     * 
     * @param s array di stringhe che contiene una risposta inviata dal server
     *          la risposta e' del tipo [error; messaggio di errore] o [ok;]
     */
    public void setMex(String[] s) {
        if (s[0].equals("error")) {
            mex = s[1];
            undoPlayerMove();
            repaint();
        }

    }

    /**
     * Overload del metodo setMex
     * Imposta il messaggio da mostrare nella sezione notifiche
     * 
     * @param s stringa che contiene il messaggio da visualizzare
     */
    public void setMex(String s) {
        mex = s;
        repaint();
    }

    /**
     * Ritorna il buffer contenente le lettere della giocata corrente
     *
     * @return il buffer contenente le lettere della giocata corrente
     */
    public ArrayList<letter> getBuffer() {
        return buffer;
    }

    /**
     * Imposta il nome del giocatore
     * 
     * @param player_name il nome del giocatore
     */
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    /**
     * Imposta il punteggio del giocatore a seguito del calcolo eseguito dal server
     * 
     * @param points il punteggio da impostare
     */
    public void setPoints(Integer points) {
        this.punteggio = points.toString();
        repaint();
    }

    /**
     * Aggiorna la matrice di gioco con la lista di lettere fornita. (buffer)
     * Ogni lettera viene inserita alla sua posizione corrispondente nella matrice
     * di gioco.
     * In seguito all'aggiornamento della matrice, viene triggerato un repaint per
     * riflettere i cambiamenti visivamente.
     *
     * @param list The list of letters to update the matrix with.
     */
    public void UpdateMatrix(ArrayList<letter> list) {
        for (int i = 0; i < list.size(); i++) {
            letter tmp = list.get(i);
            gameMatrix[tmp.getP().getY()][tmp.getP().getX()] = tmp;
        }
        repaint();
    }

    /**
     * Aggiunge le lettere fornite alla mano del giocatore.
     * In seguito all'aggiunta delle lettere, viene triggerato un repaint per
     * riflettere i cambiamenti visivamente.
     *
     * @param list The list of letters to add to the player's hand.
     */
    public void addToMano(ArrayList<letter> list) {
        for (letter letter : list) {
            mano.add(letter);
        }
        repaint();
    }

    /**
     * Metodo che controlla se e' possibile scrivere nella cella in cui l'utente
     * vuole inserire la propria lettera
     * Questa e' una misura di sicurezza in quanto non esiste ancora una funzione in
     * grado di determinare piu' di una parola nella stessa posizione
     * 
     * @param l Lettera che l'utente sta cercando di inserire
     * @return true se la cella non e' circondata da altre lettere, false altrimenti
     */
    public boolean checkIfCellWritable(letter l) {
        boolean viable = true;
        int y = l.getP().getY();
        int x = l.getP().getX();
        // check nord-ovest

        if (gameMatrix[y - 1][x] != null && gameMatrix[y][x - 1] != null) {
            viable = false;
        }
        // check nord-est
        if (gameMatrix[y - 1][x] != null && gameMatrix[y][x + 1] != null) {
            viable = false;
        }
        // check sud-ovest
        if (gameMatrix[y + 1][x] != null && gameMatrix[y][x - 1] != null) {
            viable = false;
        }
        // check sud-est
        if (gameMatrix[y + 1][x] != null && gameMatrix[y][x + 1] != null) {
            viable = false;
        }
        return viable;
    }

    /**
     * Metodo che resetta la mano del giocatore
     */
    public void resetMano() {
        mano.clear();
    }

}
