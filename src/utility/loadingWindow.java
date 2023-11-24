package utility;

import javax.swing.*;
import java.awt.*;
/**
 * Classe che implementa un'interfaccia grafica per la creazione
 * di una schermata di caricamento contente un titolo, una breve
 * descrizione ed un'immagine del logo del gioco Scarabeo
 */
public class loadingWindow extends JFrame {

     // ATTRIBUTI

    /**
    * Immagine off-screen utilizzata per il rendering 
    */
    private Image offScreenImageDrawed = null;
    /**
    * Oggetto Graphics per l'immagine off-screen
   */
    private Graphics offScreenGraphicsDrawed = null;
    /**
     * Costruttore della finestra di caricamento.
     * Imposta il titolo, le dimensioni, l'operazione di chiusura,
     * la posizione, e la non ridimensionabilità della finestra.
     */
    public loadingWindow() {
        setTitle("Scarabeo"); // Imposta il titolo della finestra
        setSize(settings.loadintWidth, settings.loadingHeight); // Imposta le dimensioni della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Imposta l'operazione di chiusura
        setLocationRelativeTo(null); // Imposta la posizione al centro dello schermo
        setResizable(false); // Impedisce il ridimensionamento della finestra
    }

    /**
     * Metodo paint per il disegno grafico.
     *
     * @param g Oggetto Graphics utilizzato per il disegno
     */
    @Override
    public void paint(Graphics g) {
        // Ottiene le dimensioni della finestra
        final Dimension d = getSize();
        if (offScreenImageDrawed == null) {
            // Double-buffering: pulisce l'immagine off-screen
            offScreenImageDrawed = createImage(d.width, d.height);
        }
        offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics();
        offScreenGraphicsDrawed.setColor(Color.white);
        offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height);
        /////////////////////
        // Paint Offscreen //
        /////////////////////
        renderOffScreen(offScreenImageDrawed.getGraphics());
        g.drawImage(offScreenImageDrawed, 0, 0, null);
    }
    /**
     * Metodo per il rendering sull'immagine off-screen.
     *
     * @param g Oggetto Graphics utilizzato per il disegno sull'immagine off-screen
     */
    public void renderOffScreen(Graphics g) {

        String blinkingText = "In attesa che entrambi i giocatori siano pronti...";
        boolean[] visible = { true }; // Use an array to store the boolean value

        // Timer timer = new Timer();
        // timer.scheduleAtFixedRate(new TimerTask() {
        //     @Override
        //     public void run() {
        //         visible[0] = !visible[0]; // Update the value in the array
        //         repaint();
        //     }
        // }, 2000, 2000); // Lampeggia ogni 2 secondi (2000 ms)

        // Disegna lo sfondo
        g.setColor(new Color(27, 111, 14)); // Cambia il colore dello sfondo
        g.fillRect(0, 0, getWidth(), getHeight());

        // Disegna la scritta lampeggiante
        boolean isVisible = visible[0]; // Assign the value from the array to a local variable
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(blinkingText);
        int xBlinkingText = (getWidth() - stringWidth) / 2;
        int yBlinkingText = getHeight() - 50; // Posiziona la scritta in basso al centro

        if (isVisible) {
            g.drawString(blinkingText, xBlinkingText, yBlinkingText);
        }

        if (visible[0]) {
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
                int yScritta1 = getHeight() / 2 - 160;
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
                int yScritta2 = getHeight() / 2 - 20;
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
                ImageIcon immagineScarabeo = new ImageIcon("./src/utility/scarabeoLogo.png");
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
                int yImmagine = yScritta2 + rectHeight + 10;
                g.drawImage(image, xImmagine, yImmagine, this);

    };

}
