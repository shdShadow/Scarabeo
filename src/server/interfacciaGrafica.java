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

                //posiziono logo dello scarabeo
                ImageIcon immagineScarabeo=new ImageIcon("scarabeoLogo.png");
                Image image=immagineScarabeo.getImage();
                g.drawImage(image, 0, 0, getWidth(),getHeight(),this);

                //Disegno scritta SCARABEO AL CENTRO IN ALTO NELLA FINESTRA
                String scritta1="SCARABEO";
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD,30));
                FontMetrics fontMetricsTitolo=g.getFontMetrics();//prende le info relative al font utilizzato per scritta1
                int stringWidthTitolo = fontMetricsTitolo.stringWidth(scritta1);//larghezza in pixel 
                int xScritta1 = (getWidth() - stringWidthTitolo) / 2;//per centrare orizzonalmente
                int yScritta1 = getHeight() / 2 - 120; // Posiziona sopra il messaggio principale
                g.drawString(scritta1, xScritta1, yScritta1);//disegna scritta1

            }
        }
    }
}