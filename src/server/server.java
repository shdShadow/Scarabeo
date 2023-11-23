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

public class Server {
    public static void main(String[] args) throws Exception {
        sacchettoLettere sl = new sacchettoLettere();
        boolean first = true;
        try {
            // Creazione del ServerSocket in ascolto sulla porta specificata

            ServerSocket serverSocket = new ServerSocket(666);
            System.out.println("Server in attesa di connessioni...");
            Socket[] listSocket = new Socket[2];
            int[] points = new int[2];
            // Accettazione della connessione da parte del client
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
            // Loop per gestire la comunicazione continua

            ArrayList<letter> mano1 = sl.getRandomLetters(8, 1);
            ArrayList<letter> mano2 = sl.getRandomLetters(8, 2);
            outs[0].println(parserStringifier.stringifyCommand(new comando("hand", mano1)));
            outs[1].println(parserStringifier.stringifyCommand(new comando("hand", mano2)));
            comando c = new comando();
            boolean end = false;
            while (true) {

                // Lettura del messaggio inviato dal client
                PrintWriter out = outs[turno % 2];
                BufferedReader in = ins[turno % 2];
                out.println("play");
                if (!first) {
                    c.setExec("update");
                    out.println(parserStringifier.stringifyCommand(c));
                    first = false;
                }
                String clientMessage = in.readLine();

                // Verifica se il client ha chiuso la connessione
                if (clientMessage.equals("exit")) {
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

                if (!end){
                    String response[] = new String[2];
                c = parserStringifier.parseCommando(clientMessage);
                if (c.getExec().equals("switch")) {
                    ArrayList<letter> hand = sl.getRandomLetters(8, turno % 2);
                    out.println(parserStringifier.stringifyCommand(new comando("hand", hand)));
                    first = false;
                    out.println("stop");
                    turno++;
                } else {
                    response = checkGiocata.checkParola(c.getL(), giocate);
                    // Risposta al client
                    if (response[0].equals("error")) {
                        out.println(response[0] + ";" + response[1]);
                    } else if (response[0].equals("ok")) {
                        giocate ++ ;
                        points[turno % 2] += Integer.parseInt(response[1]);
                        out.println(response[0] + ";" + points[turno % 2]);
                        int counter = 0;
                        for (letter letter : c.getL()) {
                            if (letter.borrowed) {
                                counter++;
                            }
                        }
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

    public static int checkWinner(int[] points) {
        int index = -1;
        for (int i = 0; i < points.length; i++) {
            if (points[i] >= settings.max_punteggio) {
                index = i;
            }
        }
        return index;
    }

    public static void closeServer(ServerSocket serverSocket, BufferedReader[] ins, PrintWriter[] outs, Socket[] listSocket)
            throws IOException {
        for (int i = 0; i < listSocket.length; i++) {
            ins[i].close();
            outs[i].close();
            listSocket[i].close();
        }
        serverSocket.close();
    }
}