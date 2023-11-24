package client;

import utility.*;
import xml.parserStringifier;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Classe che implementa la logica del client per il gioco Scarabeo.
 */
public class Client {
    /**
     * Metodo principale per l'esecuzione del client.
     *
     * @param args gli argomenti passati da riga di comando
     * @throws Exception eccezione generica che può essere lanciata durante l'esecuzione
     */
    public static void main(String[] args) throws Exception {
        // Definizione delle costanti per l'indirizzo IP del server e il numero di porta
        final String SERVER_IP = "localhost";
        final int PORT_NUMBER = 666;
        loadingWindow lw = new loadingWindow();

        try {
            // Connessione al server
            Socket socket = new Socket(SERVER_IP, PORT_NUMBER);
            lw.setVisible(true);
            // Creazione degli stream di input e output
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Scanner per leggere l'input dell'utente
            //boolean turno = false;

            // Ottieni la mano del giocatore dal server e inizializza la matrice di gioco
            ArrayList<letter> mano = parserStringifier.parseCommando(in.readLine()).getL();
            drawMatrice dm = new drawMatrice();
            lw.setVisible(false);
            lw.dispose();
            dm.setVisible(true);
            dm.setSize(1280, 720);
            dm.setTitle("SCARABEO");
            dm.setMano(mano);
            dm.repaint();
            dm.setPlayer_name(mano.get(0).getPlayer().getPlayer_name());
            // Aggiungi un KeyListener per gestire gli eventi della tastiera
            KeyListener additionalKeyListener = new KeyListener() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            // Invia al server i dati aggiornati della matrice di gioco quando viene premuto il tasto "Invio"
                            sendServer(out, dm);
                        } catch (ParserConfigurationException | TransformerException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                        try {
                            // Invia al server un comando per cambiare la mano del giocatore quando viene premuto il tasto "Shift sinistro"
                            sendServerSwitchHand(out, dm);
                        } catch (ParserConfigurationException | TransformerException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                
                /**
                * Metodo chiamato quando un tasto della tastiera viene rilasciato.
                 *
                * @param e L'evento KeyEvent associato al rilascio di un tasto
                */
                @Override
                public void keyReleased(KeyEvent e) {
                }
                /**
                * Metodo chiamato quando viene inserito un carattere dalla tastiera.
                *
                * @param e L'evento KeyEvent associato all'inserimento di un carattere
                */
                @Override
                public void keyTyped(KeyEvent e) {
                }
            };
            dm.addKeyListener(additionalKeyListener);
            dm.addWindowListener(new WindowAdapter() {
                /**
                * Metodo chiamato quando l'utente chiude la finestra del gioco.
                *
                * @param e L'evento WindowEvent associato alla chiusura della finestra
                */
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        exitProgram(socket, in, out);
                    } catch (IOException e1) {

                        e1.printStackTrace();
                    }
                }
            });
            while (true) {
                /**
                 * Stringa che rappresenta la risposta ricevuta dal server.
                */
                String serverResponse = in.readLine();
                // Controlla se la risposta inizia con '<'
                if (serverResponse.charAt(0) == '<') {
                    // Analizza il comando dalla risposta del server ed esegue azioni specifiche in base al comando
                    comando c = parserStringifier.parseCommando(serverResponse);
                     // Se l'esecuzione del comando è 'update', aggiorna la matrice in DataManager
                    if (c.getExec().equals("update")) {
                        dm.UpdateMatrix(c.getL());
                    }
                    // Se l'esecuzione del comando è 'hand', aggiunge alla mano del giocatore in DataManager
                    if (c.getExec().equals("hand")) {
                        dm.addToMano(c.getL());
                    }
                    // Se la risposta è "stop", imposta lo stato del gioco come "stop"
                } else if (serverResponse.equalsIgnoreCase("stop")) {
                    System.out.print("\033[H\033[2J");
                    dm.setStatus("stop");
                }  
                // Se la risposta è "play", imposta lo stato del gioco come "play"
                else if (serverResponse.equalsIgnoreCase("play")) {
                    dm.setStatus("play");
                } 
                //Se la risposta è "win" o "lose", imposta un messaggio di vittoria o sconfitta in base alla risposta
                else if (serverResponse.equalsIgnoreCase("win") || serverResponse.equalsIgnoreCase("lose")) {
                    if (serverResponse.equalsIgnoreCase("win")) {
                        dm.setMex("HAI VINTO");
                    } else {
                        dm.setMex("HAI PERSO");
                    }
                    // Imposta lo stato del gioco come "stop" e chiude il programma con un codice di uscita specifico
                    dm.setStatus("stop");
                    closeProgram(socket, in, out, 5);
                } else if (serverResponse.equalsIgnoreCase("exit")) {
                    // Se la risposta è "exit", chiude il programma con un codice di uscita specifico
                    closeProgram(socket, in, out, 1);
                } else {
                    // Se la risposta non corrisponde a nessuna delle condizioni precedenti, la elabora ulteriormente
                    String[] fields = serverResponse.split(";");
                    if (fields[0].equals("error")) {
                        //turno = true;
                        // Se il primo campo è "error", imposta un messaggio di errore in DataManager e imposta lo stato del gioco come "play"
                        dm.setMex(fields);
                        dm.setStatus("play");

                    } else if (fields[0].equals("ok")) {
                        // Se il primo campo è "ok", imposta i punti convertendo il secondo campo in un intero
                        dm.setPoints(Integer.parseInt(fields[1]));
                    }
                }
            }

        } 
         // Gestion dell'eccezione, se necessario
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invia al server una richiesta di aggiornamento della matrice di gioco.
     *
     * @param out L'oggetto PrintWriter per l'output verso il server
     * @param dm  L'oggetto drawMatrice che gestisce la matrice di gioco
     * @throws TransformerConfigurationException in caso di configurazione errata del trasformatore XML
     * @throws ParserConfigurationException      in caso di errore nella configurazione del parser XML
     * @throws TransformerException             in caso di errore durante la trasformazione XML
     */
    public static void sendServer(PrintWriter out, drawMatrice dm)
            throws TransformerConfigurationException, ParserConfigurationException, TransformerException {
        // Ottiene una lista temporanea di lettere dalla matrice di gioco
        ArrayList<letter> temp = dm.getBuffer();
        // Crea una stringa XML per rappresentare il comando di aggiunta alla matrice
        String s = parserStringifier.stringifyCommand(new comando("add", temp));
        // Invia la stringa XML contenente il comando al server
        out.println(s);
    }
    /**
     * Invia al server una richiesta di cambio mano durante il gioco.
     *
     * @param out L'oggetto PrintWriter per l'output verso il server
     * @param dm  L'oggetto drawMatrice che gestisce la matrice di gioco
     * @throws TransformerConfigurationException in caso di configurazione errata del trasformatore XML
     * @throws ParserConfigurationException      in caso di errore nella configurazione del parser XML
     * @throws TransformerException             in caso di errore durante la trasformazione XML
     */
    public static void sendServerSwitchHand(PrintWriter out, drawMatrice dm)
            throws TransformerConfigurationException, ParserConfigurationException, TransformerException {
        // Crea una lista temporanea vuota per il cambio mano
        ArrayList<letter> temp = new ArrayList<letter>();
        // Crea una stringa XML per rappresentare il comando di cambio mano
        String s = parserStringifier.stringifyCommand(new comando("switch", temp));
        // Resettare la mano nel pannello di gioco
        dm.resetMano();
        // Invia la stringa XML contenente il comando al server
        out.println(s);
    }
    /**
     * Chiude correttamente il programma, terminando la connessione con il server e i flussi di input/output.
     *
     * @param socket Il socket per la connessione con il server
     * @param in     Il BufferedReader per la lettura dall'input del server
     * @param out    Il PrintWriter per l'output verso il server
     * @param delay  Il ritardo in secondi prima di terminare completamente il programma
     * @throws InterruptedException in caso di interruzione del thread durante la pausa
     * @throws IOException          in caso di errore di input/output durante la chiusura
     */
    public static void closeProgram(Socket socket, BufferedReader in, PrintWriter out, int delay)
            throws InterruptedException, IOException {
        socket.close();
        in.close();
        out.close();
        Thread.sleep(delay * 1000);
        System.exit(0);
    }

    /**
     * Chiude correttamente il programma inviando un comando di uscita al server e terminando la connessione.
     *
     * @param socket Il socket per la connessione con il server
     * @param in     Il BufferedReader per la lettura dall'input del server
     * @param out    Il PrintWriter per l'output verso il server
     * @throws IOException in caso di errore di input/output durante la chiusura
     */
    public static void exitProgram(Socket socket, BufferedReader in, PrintWriter out) throws IOException {
        out.println("exit");
        out.close();
        in.close();
        socket.close();
        System.exit(0);
    }

}
