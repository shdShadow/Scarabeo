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

public class testMultiServer {
    public static void main(String[] args) throws Exception {
        try {
            // Creazione del ServerSocket in ascolto sulla porta specificata
            ServerSocket serverSocket = new ServerSocket(666);
            System.out.println("Server in attesa di connessioni...");
            Socket[] listSocket = new Socket[2];
            //TEST
            ArrayList<letter> temp = new ArrayList<letter>();
            letter l1 = new letter('A', new punto(3,4));
            temp.add(l1);

            // Add the remaining letters to form the word 'albero'
            temp.add(new letter('L', new punto(4,4)));
            temp.add(new letter('B', new punto(5,6)));
            temp.add(new letter('E', new punto(6,4)));
            temp.add(new letter('R', new punto(7,4)));
            temp.add(new letter('O', new punto(8,4)));
            drawForDebug.addToMatrix(temp);
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
            int turno = 0;
            // Loop per gestire la comunicazione continua
            while (true) {
                // Lettura del messaggio inviato dal client
                PrintWriter out = outs[turno];
                BufferedReader in = ins[turno];
                out.println("Play");
                String clientMessage = in.readLine();

                // Verifica se il client ha chiuso la connessione
                if (clientMessage.equals("exit")) {
                    System.out.println("Il client ha chiuso la connessione.");
                    break;
                }

                // System.out.println("Messaggio ricevuto dal client: " + turno + " "
                // +clientMessage); 
                comando c = parserStringifier.parseCommando(clientMessage);
                String response[] = checkGiocata.checkParola(c.getL());
                // Risposta al client
                if (response[0].equals("error")) {
                    out.println(response[0] + ";" + response[1]);
                } else if (response[0].equals("ok")) {
                    out.println(response[0] + ";");
                    if (turno == 0) {
                        turno = 1;
                    } else {
                        turno = 0;
                    }
                }

            }

            // Chiusura delle risorse
            for (int i = 0; i < listSocket.length; i++) {
                ins[i].close();
                outs[i].close();
                listSocket[i].close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}