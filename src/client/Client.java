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
            //boolean turno = false;
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
                        try {
                            sendServer(out, dm);
                        } catch (ParserConfigurationException | TransformerException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                        try {
                            sendServerSwitchHand(out, dm);
                        } catch (ParserConfigurationException | TransformerException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }
            };
            dm.addKeyListener(additionalKeyListener);
            dm.addWindowListener(new WindowAdapter() {
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
                } else if (serverResponse.equalsIgnoreCase("play")) {
                    dm.setStatus("play");
                } else if (serverResponse.equalsIgnoreCase("win") || serverResponse.equalsIgnoreCase("lose")) {
                    if (serverResponse.equalsIgnoreCase("win")) {
                        dm.setMex("HAI VINTO");
                    } else {
                        dm.setMex("HAI PERSO");
                    }
                    dm.setStatus("stop");
                    closeProgram(socket, in, out, 5);
                } else if (serverResponse.equalsIgnoreCase("exit")) {
                    closeProgram(socket, in, out, 1);
                } else {
                    String[] fields = serverResponse.split(";");
                    if (fields[0].equals("error")) {
                        //turno = true;
                        dm.setMex(fields);
                        dm.setStatus("play");

                    } else if (fields[0].equals("ok")) {
                        dm.setPoints(Integer.parseInt(fields[1]));
                    }
                }
            }

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

    public static void closeProgram(Socket socket, BufferedReader in, PrintWriter out, int delay)
            throws InterruptedException, IOException {
        socket.close();
        in.close();
        out.close();
        Thread.sleep(delay * 1000);
        System.exit(0);
    }

    public static void exitProgram(Socket socket, BufferedReader in, PrintWriter out) throws IOException {
        out.println("exit");
        out.close();
        in.close();
        socket.close();
        System.exit(0);
    }

}
