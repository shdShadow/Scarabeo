import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        final int PORT_NUMBER = 666;

        try {
            // Creazione del ServerSocket in ascolto sulla porta specificata
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server in attesa di connessioni...");

            // Accettazione della connessione da parte del client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connessione accettata da: " + clientSocket.getInetAddress());

            // Creazione degli stream di input e output
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Loop per gestire la comunicazione continua
            while (true) {
                // Lettura del messaggio inviato dal client
                String clientMessage = in.readLine();

                // Verifica se il client ha chiuso la connessione
                if (clientMessage == null) {
                    System.out.println("Il client ha chiuso la connessione.");
                    break;
                }

                System.out.println("Messaggio ricevuto dal client: " + clientMessage);

                // Risposta al client
                out.println("Messaggio ricevuto correttamente dal server.");
            }

            // Chiusura delle risorse
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
