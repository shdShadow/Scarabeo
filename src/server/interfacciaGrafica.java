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

            }
        }
    }
}