package tictactoe;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Holder extends JPanel {

    //this is a jpanel that will hold the XOPanels, I did this in case we use a background :) 
    BufferedImage background;

    public Holder() {
        setLayout(new GridLayout(3, 3));
    }

    @Override
    protected void paintComponent(Graphics g
    ) {
        super.paintComponent(g);
        try {
            background = ImageIO.read(getClass().getResource("background.png"));
        } catch (Exception e) {
            System.out.println("Cannot find background image!");
            System.out.println(e);
        }
        g.drawImage(background, 0, 0, this);
    }
}

