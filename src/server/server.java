package server;

import utility.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import xml.parserStringifier;

/**
 * La classe server si occupa di gestire la comunicazione tra i client e di
 * gestire le varie richieste in base ai comandi ricevuti
 */
public class server {
    /**
     * Il metodo principale del server
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        sacchettoLettere sl = new sacchettoLettere();
        boolean first = true;
        try {
            // Creazione del ServerSocket in ascolto sulla porta specificata

            ServerSocket serverSocket = new ServerSocket(settings.ServerPort);
            System.out.println("Server in attesa di connessioni...");
            Socket[] listSocket = new Socket[2];
            int[] points = new int[2];
            // Accettazione della connessione richiesta dal client
            Socket clientSocket1 = serverSocket.accept();
            Socket clientSocket2 = serverSocket.accept();
            while (listSocket[0] == null || listSocket[1] == null) {
                listSocket[0] = clientSocket1;
                listSocket[1] = clientSocket2;
            }
            System.out.println("Client connessi");
            // Creazione degli stream di input e output
            BufferedReader[] ins = new BufferedReader[2];
            PrintWriter[] outs = new PrintWriter[2];
            for (int i = 0; i < listSocket.length; i++) {
                ins[i] = new BufferedReader(new InputStreamReader(listSocket[i].getInputStream()));
                outs[i] = new PrintWriter(listSocket[i].getOutputStream(), true);
            }
            int turno = 1;
            int giocate = 0;
            // Genero le prime mani da inviare ai client
            ArrayList<letter> mano1 = sl.getRandomLetters(8, 1);
            ArrayList<letter> mano2 = sl.getRandomLetters(8, 2);
            // Invio le mani ai client
            outs[0].println(parserStringifier.stringifyCommand(new comando("hand", mano1)));
            outs[1].println(parserStringifier.stringifyCommand(new comando("hand", mano2)));
            comando c = new comando();
            boolean end = false;
            // loop principale del server
            while (true) {

                // Ricevo e invio i messaggi sullo stream corretto in base al turno
                PrintWriter out = outs[turno % 2];
                BufferedReader in = ins[turno % 2];
                // Invio al client "play" per indicare che e' il suo turno
                out.println("play");
                if (!first) {
                    c.setExec("update");
                    out.println(parserStringifier.stringifyCommand(c));
                    first = false;
                }
                // Ricezione del messaggio inviato dal client
                String clientMessage = in.readLine();

                // Verifica se un client ha chiuso la connessione
                if (clientMessage.equals("exit")) {
                    // Se ha chiuso la connessione, invio a tutti i client "exit" e chiudo il server
                    // e i relativi stream
                    for (int i = 0; i < outs.length; i++) {
                        if (outs[i] != out) {
                            outs[i].println("exit");
                        }
                    }
                    for (int i = 0; i < listSocket.length; i++) {
                        ins[i].close();
                        outs[i].close();
                        listSocket[i].close();
                    }
                    end = true;
                    serverSocket.close();

                }
                // Se la partita non e' ancora finita
                if (!end) {
                    String response[] = new String[2];
                    // parso il comando del client
                    // tutti i messaggi inviati dal client sono codificati in xml
                    c = parserStringifier.parseCommando(clientMessage);
                    // Se il comando e' switch significa che il client vuole cambiare la sua mano di
                    // lettere
                    if (c.getExec().equals("switch")) {
                        ArrayList<letter> hand = sl.getRandomLetters(8, turno % 2);
                        out.println(parserStringifier.stringifyCommand(new comando("hand", hand)));
                        first = false;
                        // Invio stop per indicare che il turno e' finito
                        out.println("stop");
                        turno++;
                    } else {
                        // In tutti gli altri casi il messaggio del client e' una parola
                        // Controllo che la parola sia valida
                        response = checkGiocata.checkParola(c.getL(), giocate);
                        // Risposta al client
                        if (response[0].equals("error")) {
                            out.println(response[0] + ";" + response[1]);
                        } else if (response[0].equals("ok")) {
                            giocate++;
                            points[turno % 2] += Integer.parseInt(response[1]);
                            // Invio al client il punteggio aggiornato
                            out.println(response[0] + ";" + points[turno % 2]);
                            int counter = 0;
                            // Verifico quante lettere il client ha effettivamente usato e quale invece sono
                            // state prese in prestito dal tabellone
                            for (letter letter : c.getL()) {
                                if (letter.borrowed) {
                                    counter++;
                                }
                            }
                            // Genero le lettere da inviare al client
                            ArrayList<letter> pescata = sl.getRandomLetters(c.getL().size() - counter, (turno % 2) + 1);
                            out.println(parserStringifier.stringifyCommand(new comando("hand", pescata)));
                            out.println("stop");
                            turno++;

                        }

                    }

                    if (response[0] != null) {
                        if (response[0].equals("error")) {
                            first = true;
                        } else {
                            first = false;
                        }
                    }
                    // Controllo se la partita e' finita e pertante c'e' un vincitore
                    int winner = checkWinner(points);
                    if (winner > -1) {
                        for (int i = 0; i < outs.length; i++) {
                            if (i == winner) {
                                outs[i].println("win");
                            } else {
                                outs[i].println("lose");
                            }
                        }
                        closeServer(serverSocket, ins, outs, listSocket);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Controlla alla fine di ogni turno se un giocatore ha raggiunto il punteggio
     * massimo
     * 
     * @param points L'array di punteggi dei giocatori.
     * @return L'indice del giocatore che ha raggiunto il punteggio massimo, -1
     *         altrimenti.
     */
    public static int checkWinner(int[] points) {
        int index = -1;
        for (int i = 0; i < points.length; i++) {
            if (points[i] >= settings.max_punteggio) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Chiude il server e i relativi stream
     * 
     * @param serverSocket Il socket del server.
     * @param ins          L'array di stream di input.
     * @param outs         L'array di stream di output.
     * @param listSocket   L'array di socket.
     * @throws IOException Se si verifica un errore di I/O.
     */
    public static void closeServer(ServerSocket serverSocket, BufferedReader[] ins, PrintWriter[] outs,
            Socket[] listSocket)
            throws IOException {
        for (int i = 0; i < listSocket.length; i++) {
            ins[i].close();
            outs[i].close();
            listSocket[i].close();
        }
        serverSocket.close();
    }
}