package utility;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe che implementa un'interfaccia grafica per il gioco Scarabeo.
 */
public class interfacciaGrafica extends JFrame {

    /**
     * Costruttore per l'interfaccia grafica del gioco Scarabeo.
     */
    public interfacciaGrafica() {
        // Creazione del frame principale
        JFrame frame = new JFrame("Scarabeo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Creazione di un pannello personalizzato per la visualizzazione dei componenti
        JPanel panel = new JPanel() {
            /**
            * Variabile per il testo che lampeggia.
            */
            String blinkingText = "In attesa che entrambi i giocatori siano pronti...";
            /**
            * Variabile booleana per la visibilità del testo lampeggiante.
            */
            boolean visible = true;

            {
                // Timer per il lampeggio del testo
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        visible = !visible;
                        repaint(); // Richiama il metodo paintComponent per aggiornare il pannello
                    }
                }, 0, 2000); // Esegui il lampeggio ogni 2 secondi
            }

            /**
             * Metodo per disegnare i componenti nel pannello.
             *
             * @param g Oggetto Graphics utilizzato per disegnare
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                // Disegno dello sfondo nero
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());


                // Disegno del testo lampeggiante
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                FontMetrics fontMetrics = g.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(blinkingText);
                /**
                * Variabile intera per le coordinate x della scritta lampeggiante
                */
                int xBlinkingText = (getWidth() - stringWidth) / 2;
                /**
                * Variabile intera per le coordinate y della scritta lampeggiante
                */
                int yBlinkingText = getHeight() - 50;

                if (visible) {
                    g.drawString(blinkingText, xBlinkingText, yBlinkingText);
                }



                // Disegno del titolo "SCARABEO"
                /**
                * Variabile String che contiene la scritta SCARABEO
                */
                String scritta1 = "SCARABEO";
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                /**
                * Variabile FontMetrics per ottenere informazioni sul disegno della scritta SCARABEO
                */
                FontMetrics fontMetricsScritta1 = g.getFontMetrics();
                /**
                * Variabile intera per calcolare larghezza in pixel
                */
                int stringWidthTitolo = fontMetricsScritta1.stringWidth(scritta1);
                /**
                * Variabile intera per le coordinate x della scritta SCARABEO
                */
                int xScritta1 = (getWidth() - stringWidthTitolo) / 2;
                /**
                * Variabile intera per le coordinate Y della scritta SCARABEO
                */
                int yScritta1 = getHeight() / 2 - 120;
                g.drawString(scritta1, xScritta1, yScritta1);



                // Disegno della scritta "L'unico gioco di parole con lo scarabeo"
               
                /**
                * Variabile String che contiene la scritta "L'unico gioco di parole con lo scarabeo"
                */
                String scritta2 = "L'unico gioco di parole con lo scarabeo";
                g.setColor(Color.RED);
                /**
                * Variabile FontMetrics per ottenere informazioni sul disegno della scritta "L'unico gioco di parole con lo scarabeo"
                */
                FontMetrics fontMetricsScritta2 = g.getFontMetrics();
                /**
                * Variabile intera per calcolare larghezza in pixel
                */
                int stringWidthScritta2 = fontMetricsScritta2.stringWidth(scritta2);
                /**
                * Variabile intera per le coordinate x della scritta "L'unico gioco di parole con lo scarabeo"
                */
                int xScritta2 = (getWidth() - stringWidthScritta2) / 2;
                /**
                * Variabile intera per le coordinate y della scritta "L'unico gioco di parole con lo scarabeo"
                */
                int yScritta2 = getHeight() / 2 + 40;
                /**
                * Variabile intera per restituire la larghezza in pixel
                */
                int rectWidth = stringWidthScritta2 + 20;
                /**
                * Variabile intera per restituire la altezza in pixel
                */
                int rectHeight = fontMetricsScritta2.getHeight() + 10;
                g.fillRect(xScritta2 - 10, yScritta2 - 30, rectWidth, rectHeight);
                g.setColor(Color.WHITE);
                g.drawString(scritta2, xScritta2, yScritta2);

                // Disegno del logo dello scarabeo
                ImageIcon immagineScarabeo = new ImageIcon("scarabeoLogo.png");
                /**
                * Variabile Image che riceve l'immagine
                */
                Image image = immagineScarabeo.getImage();
                /**
                * Variabile intera che contiene la larghezza dell'immagine
                */
                int logoWidth = immagineScarabeo.getIconWidth();
                /**
                * Variabile intera per le coordinate x dell'immagine
                */
                int xImmagine = (getWidth() - logoWidth) / 2;
                /**
                * Variabile intera per le coordinate y dell'immagine
                */
                int yImmagine = yScritta2 + rectHeight + 20;
                g.drawImage(image, xImmagine, yImmagine, this);
            }
        };

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
