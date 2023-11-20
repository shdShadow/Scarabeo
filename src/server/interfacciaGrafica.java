package server;

import javax.swing.*;
import java.awt.*;

public class interfacciaGrafica {
    public interfacciaGrafica() {
        JFrame frame = new JFrame("Scarabeo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                

                //Disegno scritta SCARABEO al centro in alto nella finestra
                String scritta1="SCARABEO";
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD,30));
                FontMetrics fontMetricsScritta1=g.getFontMetrics();//prende le info relative al font utilizzato per scritta1
                int stringWidthTitolo = fontMetricsScritta1.stringWidth(scritta1);//larghezza in pixel 
                int xScritta1 = (getWidth() - stringWidthTitolo) / 2;//per centrare orizzonalmente
                int yScritta1 = getHeight() / 2 - 120; // Posiziona sopra il messaggio principale
                g.drawString(scritta1, xScritta1, yScritta1);//disegna scritta1



                //Disegno scritta L'unico gioco di parole con lo scarabeo sotto la prima scritta
                String scritta2 = "L'unico gioco di parole con lo scarabeo";
                g.setColor(Color.RED);
                FontMetrics fontMetricsScritta2 = g.getFontMetrics();//prende le info relative al font utilizzato per scritta2
                int stringWidthScritta2 = fontMetricsScritta2.stringWidth(scritta2);
                int xScritta2 = (getWidth() - stringWidthScritta2) / 2;
                int yScritta2 = getHeight() / 2 + 40; // Posiziona sotto il messaggio principale
                int rectWidth = stringWidthScritta2 + 20; // Aggiungo margine a sinistra e destra
                int rectHeight = fontMetricsScritta2.getHeight() + 10; // Altezza testo con margine sopra e sotto
                g.fillRect(xScritta2 - 10, yScritta2 - 30, rectWidth, rectHeight);

                // Disegna la scritta all'interno del rettangolo rosso
                g.setColor(Color.WHITE);
                g.drawString(scritta2, xScritta2, yScritta2);

                //posiziono logo dello scarabeo
                ImageIcon immagineScarabeo = new ImageIcon("scarabeoLogo.png");
                Image image = immagineScarabeo.getImage();
                int logoWidth = immagineScarabeo.getIconWidth();
                int logoHeight = immagineScarabeo.getIconHeight();
                int xImmagine = (getWidth() - logoWidth) / 2;
                int yImmagine = yScritta2 + rectHeight + 20; // Posiziona sotto la scritta2
                g.drawImage(image, xImmagine, yImmagine, this);
            }
        };

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            }
        }
        
    
