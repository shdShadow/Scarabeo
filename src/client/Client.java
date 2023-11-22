package client;

import utility.*;
import xml.parserStringifier;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Client {
    public static void main(String[] args) throws Exception {
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
            Scanner scanner = new Scanner(System.in);
            ArrayList<letter> temp = new ArrayList<letter>();

            // letter l1 = new letter('A', new punto(3,4));
            // temp.add(l1);

            // // Add the remaining letters to form the word 'albero'
            // temp.add(new letter('L', new punto(4,4)));
            // temp.add(new letter('B', new punto(5,4)));
            // temp.add(new letter('E', new punto(6,4)));
            // temp.add(new letter('R', new punto(7,4)));
            // temp.add(new letter('O', new punto(8,4)));

            // // Rest of the code...

            boolean turno = false;
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
            KeyListener additionalKeyListener = new KeyListener() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        // Enter key is pressed
                        try {
                            sendServer(out, dm);
                        } catch (ParserConfigurationException | TransformerException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        // TODO: Handle Enter key press event
                    }

                    if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                        try {
                            sendServerSwitchHand(out, dm);
                        } catch (ParserConfigurationException | TransformerException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO: Handle additional key release event
                    System.out.println("Additional Key Released");
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    // TODO: Handle additional key typed event
                }
            };
            dm.addKeyListener(additionalKeyListener);
            boolean first = true;
            if (mano.get(0).getPlayer().getPlayer_name().equals("Player1")) {
                first = true;
            } else {
                first = false;
            }
            // Loop per gestire la comunicazione continua
            while (true) {
                String serverResponse = in.readLine();
                if (serverResponse.charAt(0) == '<') {
                    comando c = parserStringifier.parseCommando(serverResponse);
                    if (c.getExec().equals("update")) {
                        dm.UpdateMatrix(c.getL());
                    }
                    if (c.getExec().equals("hand")) {
                        dm.addToMano(c.getL());
                    }
                } else if (serverResponse.equalsIgnoreCase("stop")) {
                    System.out.print("\033[H\033[2J");
                    dm.setStatus("stop");
                    turno = false;
                } else if (serverResponse.equalsIgnoreCase("play")) {
                    turno = true;
                    dm.setStatus("play");
                } else if (serverResponse.equalsIgnoreCase("win") || serverResponse.equalsIgnoreCase("lose")) {
                    // call method to close window
                    if (serverResponse.equalsIgnoreCase("win")) {
                        dm.setMex("HAI VINTO");
                    } else {
                        dm.setMex("HAI PERSO");
                    }
                    dm.setStatus("stop");
                    closeProgram();
                } else {
                    String[] fields = serverResponse.split(";");
                    if (fields[0].equals("error")) {
                        turno = true;
                        dm.setMex(fields);
                        dm.setStatus("play");

                    } else if (fields[0].equals("ok")) {
                        dm.setPoints(Integer.parseInt(fields[1]));
                    }
                }
                if (2 == 1) {
                    break;
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

    public static void sendServer(PrintWriter out, drawMatrice dm)
            throws TransformerConfigurationException, ParserConfigurationException, TransformerException {
        ArrayList<letter> temp = dm.getBuffer();
        String s = parserStringifier.stringifyCommand(new comando("add", temp));
        out.println(s);
    }

    public static void sendServerSwitchHand(PrintWriter out, drawMatrice dm)
            throws TransformerConfigurationException, ParserConfigurationException, TransformerException {
        ArrayList<letter> temp = new ArrayList<letter>();
        String s = parserStringifier.stringifyCommand(new comando("switch", temp));
        dm.resetMano();
        out.println(s);
    }

    public static void closeProgram() throws InterruptedException {
        int delayInSeconds = 5; // Replace with your desired delay in seconds
        Thread.sleep(delayInSeconds * 1000);
        System.exit(0);
    }

}
