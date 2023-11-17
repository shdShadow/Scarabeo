package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
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
            boolean turno = false;
            // Loop per gestire la comunicazione continua
            while (true) {
                String serverResponse = in.readLine();
                if (serverResponse.equalsIgnoreCase("stop")) {
                    turno = false;
                } else if (serverResponse.equalsIgnoreCase("play")) {
                    turno = true;
                }
                if (turno == true) {
                    // Richiesta di input da parte dell'utente
                    System.out.print("Inserisci un messaggio (o 'exit' per terminare): ");
                    String userMessage = scanner.nextLine();
                    out.println(userMessage);

                    if (userMessage.equalsIgnoreCase("exit")) {
                        out.println(userMessage);
                        break;
                    }

                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.println("Non e' il tuo turno");
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
