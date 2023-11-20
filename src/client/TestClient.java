package client;
import utility.*;
import xml.parserStringifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

public class TestClient {
    public static void main(String[] args) throws TransformerConfigurationException, ParserConfigurationException, TransformerException {
        final String SERVER_IP = "localhost";
        final int PORT_NUMBER = 666;

        try {
            // Connessione al server
            Socket socket = new Socket(SERVER_IP, PORT_NUMBER);

            // Creazione degli stream di input e output
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Scanner per leggere l'input dell'utente
            Scanner scanner = new Scanner(System.in);
            ArrayList<letter> temp = new ArrayList<letter>();

            letter l1 = new letter('A', new punto(3,4));
            temp.add(l1);

            // Add the remaining letters to form the word 'albero'
            temp.add(new letter('L', new punto(4,4)));
            temp.add(new letter('B', new punto(5,4)));
            temp.add(new letter('E', new punto(6,4)));
            temp.add(new letter('R', new punto(7,4)));
            temp.add(new letter('O', new punto(8,4)));

            // Rest of the code...
            

            boolean turno = false;
            // Loop per gestire la comunicazione continua
            while (true) {
                System.out.print("\033[H\033[2J");
                System.out.println("Aspetta il tuo turno");
                String serverResponse = in.readLine();
                if (serverResponse.equalsIgnoreCase("stop")) {
                     System.out.print("\033[H\033[2J");
                    System.out.println("Non e' il tuo turno");
                    turno = false;
                } else if (serverResponse.equalsIgnoreCase("play")) {
                    turno = true;
                }
                if (turno == true) {
                    // Richiesta di input da parte dell'utente
                    //System.out.print("Inserisci un messaggio (o 'exit' per terminare): ");
                    //String userMessage = scanner.nextLine();
                    System.out.println("provo ad inserire la parola albero");
                    scanner.nextLine();
                    String userMessage = parserStringifier.stringifyCommand(new comando("add", temp));
                    out.println(userMessage);

                    if (userMessage.equalsIgnoreCase("exit")) {
                        out.println(userMessage);
                        break;
                    }
                    turno = false;

                } else {
                   String[] fields = serverResponse.split(";");                   if (fields[0].equals("error")) {
                       System.out.println("Errore: " + fields[1]);
                       turno = true;
                   } else if (fields[0].equals("ok")) {
                       System.out.println("Parola inserita correttamente");
                   }
                }

                // // Invio del messaggio al server
                // out.println(userMessage);

                // // Verifica se l'utente ha inserito 'exit' per chiudere la connessione

                // // Ricezione e stampa della risposta dal server
                // String serverResponse = in.readLine();
                // System.out.println("Risposta dal server: " + serverResponse);

            }

            // Chiusura delle risorse
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
