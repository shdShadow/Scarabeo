package utility;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class loadingWindow extends JFrame {
    private Image offScreenImageDrawed = null;
    private Graphics offScreenGraphicsDrawed = null;

    public loadingWindow() {
        setTitle("Scarabeo");
        setSize(settings.loadintWidth, settings.loadingHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void paint(Graphics g) {
        final Dimension d = getSize();
        if (offScreenImageDrawed == null) {
            // Double-buffer: clear the offscreen image.
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

        // Disegna la scritta "SCARABEO"
        String scritta1 = "SCARABEO";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fontMetricsScritta1 = g.getFontMetrics();
        int stringWidthTitolo = fontMetricsScritta1.stringWidth(scritta1);
        int xScritta1 = (getWidth() - stringWidthTitolo) / 2;
        int yScritta1 = getHeight() / 2 - 160;
        g.drawString(scritta1, xScritta1, yScritta1);

        // Disegna la seconda scritta "L'unico gioco di parole con lo scarabeo"
        String scritta2 = "L'unico gioco di parole con lo scarabeo";
        g.setColor(Color.RED);
        FontMetrics fontMetricsScritta2 = g.getFontMetrics();
        int stringWidthScritta2 = fontMetricsScritta2.stringWidth(scritta2);
        int xScritta2 = (getWidth() - stringWidthScritta2) / 2;
        int yScritta2 = getHeight() / 2 - 20;
        int rectWidth = stringWidthScritta2 + 20;
        int rectHeight = fontMetricsScritta2.getHeight() + 10;
        g.fillRect(xScritta2 - 10, yScritta2 - 30, rectWidth, rectHeight);
        g.setColor(Color.WHITE);
        g.drawString(scritta2, xScritta2, yScritta2);

        // Disegna l'immagine dello scarabeo
        ImageIcon immagineScarabeo = new ImageIcon("./src/utility/scarabeoLogo.png");
        Image image = immagineScarabeo.getImage();
        int logoWidth = immagineScarabeo.getIconWidth();
        int logoHeight = immagineScarabeo.getIconHeight();
        int xImmagine = (getWidth() - logoWidth) / 2;
        int yImmagine = yScritta2 + rectHeight + 10;
        g.drawImage(image, xImmagine, yImmagine, this);

    };

}
