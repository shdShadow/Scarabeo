package server;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class interfacciaGrafica extends JFrame {
    public interfacciaGrafica() {
        JFrame frame = new JFrame("Scarabeo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            String blinkingText = "In attesa che entrambi i giocatori siano pronti...";
            boolean visible = true;

            {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        visible = !visible;
                        repaint();
                    }
                }, 0, 2000); // Lampeggia ogni 2 secondi (2000 ms)
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Disegna lo sfondo
                g.setColor(Color.BLACK); // Cambia il colore dello sfondo
                g.fillRect(0, 0, getWidth(), getHeight());

                // Disegna la scritta lampeggiante
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                FontMetrics fontMetrics = g.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(blinkingText);
                int xBlinkingText = (getWidth() - stringWidth) / 2;
                int yBlinkingText = getHeight() - 50; // Posiziona la scritta in basso al centro

                if (visible) {
                    g.drawString(blinkingText, xBlinkingText, yBlinkingText);
                }

                // Disegna la scritta "SCARABEO"
                String scritta1 = "SCARABEO";
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics fontMetricsScritta1 = g.getFontMetrics();
                int stringWidthTitolo = fontMetricsScritta1.stringWidth(scritta1);
                int xScritta1 = (getWidth() - stringWidthTitolo) / 2;
                int yScritta1 = getHeight() / 2 - 120;
                g.drawString(scritta1, xScritta1, yScritta1);

                // Disegna la seconda scritta "L'unico gioco di parole con lo scarabeo"
                String scritta2 = "L'unico gioco di parole con lo scarabeo";
                g.setColor(Color.RED);
                FontMetrics fontMetricsScritta2 = g.getFontMetrics();
                int stringWidthScritta2 = fontMetricsScritta2.stringWidth(scritta2);
                int xScritta2 = (getWidth() - stringWidthScritta2) / 2;
                int yScritta2 = getHeight() / 2 + 40;
                int rectWidth = stringWidthScritta2 + 20;
                int rectHeight = fontMetricsScritta2.getHeight() + 10;
                g.fillRect(xScritta2 - 10, yScritta2 - 30, rectWidth, rectHeight);
                g.setColor(Color.WHITE);
                g.drawString(scritta2, xScritta2, yScritta2);

                // Disegna l'immagine dello scarabeo
                ImageIcon immagineScarabeo = new ImageIcon("scarabeoLogo.png");
                Image image = immagineScarabeo.getImage();
                int logoWidth = immagineScarabeo.getIconWidth();
                int logoHeight = immagineScarabeo.getIconHeight();
                int xImmagine = (getWidth() - logoWidth) / 2;
                int yImmagine = yScritta2 + rectHeight + 20;
                g.drawImage(image, xImmagine, yImmagine, this);
            }
        };

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
}



