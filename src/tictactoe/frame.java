package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.sound.sampled.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frame extends JFrame {

    //establishing whose turn it is:
    private char currentPlayer = 'X';

    private String currentPlayerText;

    //Creating a grid 3x3:
    private XOPanel[][] panels = new XOPanel[3][3];

    //Create a status label:
    JLabel status = new JLabel("It's cat's turn.");

    //establish images to draw:
    BufferedImage cat, dog, background;

    /*
     Constructor for frame
     */
    public frame() {

        Holder panel = new Holder();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel.add(panels[i][j] = new XOPanel());
            }
        }

        panel.setBorder(new LineBorder(Color.BLACK, 1));
        status.setBorder(new LineBorder(Color.BLACK, 1));
        add(panel, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
    }

    public boolean catGame() {
        //returns true if board is full, else false

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (panels[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }
        if (currentPlayer == ' ') {
            return false;
        }
        return true;
    }

    public boolean winnerExists(char token) {
        for (int i = 0; i < 3; i++) {
            if ((panels[i][0].getToken() == token)
                    && (panels[i][1].getToken() == token)
                    && (panels[i][2].getToken() == token)) {
                return true;
            }
        }

        // check columns
        for (int j = 0; j < 3; j++) {
            if ((panels[0][j].getToken() == token)
                    && (panels[1][j].getToken() == token)
                    && (panels[2][j].getToken() == token)) {
                return true;
            }
        }

        // check diagonals
        if ((panels[0][0].getToken() == token)
                && (panels[1][1].getToken() == token)
                && (panels[2][2].getToken() == token)) {
            return true;
        }
        if ((panels[0][2].getToken() == token)
                && (panels[1][1].getToken() == token)
                && (panels[2][0].getToken() == token)) {
            return true;
        }

        return false;
    }

    public void setCurrentPlayerText() {
        if (currentPlayer == 'X') {
            currentPlayerText = "Cat";
        } else if (currentPlayer == 'O') {
            currentPlayerText = "Dog";
        }
    }

    public class XOPanel extends JPanel {

        private char token = ' '; //default as empty cell

        //sound stuff: 
        File catSound, dogSound;
        AudioInputStream dogstream, catstream;
        AudioFormat catformat, dogformat;
        DataLine.Info catinfo, doginfo;
        Clip dogclip, catclip;

        /**
         * Constructor for the XOPanels
         */
        public XOPanel() {

            setBorder(new LineBorder(Color.black, 1));
            addMouseListener(new panelMouseListener());
            setBackground(new Color(255, 255, 255));

            /**
             * Establishing sound stuff, will call this if there is a winner:
             */
            try {
                catstream = AudioSystem.getAudioInputStream(getClass().getResource("cat.wav"));
                dogstream = AudioSystem.getAudioInputStream(getClass().getResource("dog.wav"));
                catformat = catstream.getFormat();
                dogformat = dogstream.getFormat();
                catinfo = new DataLine.Info(Clip.class, catformat);
                doginfo = new DataLine.Info(Clip.class, dogformat);
                catclip = (Clip) AudioSystem.getLine(catinfo);
                dogclip = (Clip) AudioSystem.getLine(doginfo);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * get the token of this cell and also set it
         * @return 
         */
        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                cat = ImageIO.read(getClass().getResource("cat.png"));
                dog = ImageIO.read(getClass().getResource("dog.png"));
            } catch (Exception e) {
                System.out.println("Cannot find dog or cat images!");
                System.out.println(e);
            }
            if (token == 'X') {
                g.drawImage(cat, 0, 0, null);
            } else if (token == 'O') {
                g.drawImage(dog, 0, 0, null);
            }
        }

        private class panelMouseListener extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (token == ' ' && currentPlayer != ' ') {
                    setToken(currentPlayer);
                }

                if (winnerExists(currentPlayer)) { //will only execute if there's a winner
                    if (currentPlayer == 'X') { //play the cat sound
                        try {
                            catclip.open(catstream);
                            catclip.start();
                        } catch (LineUnavailableException | IOException ex) {
                            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (currentPlayer == 'O') { //play the dog sound
                        try {
                            dogclip.open(dogstream);
                        } catch (LineUnavailableException | IOException ex) {
                            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        dogclip.start();
                    }
                    setCurrentPlayerText(); //sets text so the status makes more sense than just X or O
                    status.setText(currentPlayerText + " won the game!");
                    currentPlayer = ' '; //makes sure there is no currentPlayer
                } else if (catGame()) { //checks to see if all blocks are filled, if so: tie
                    status.setText("Tie Game! No winner this time.");
                    currentPlayer = ' ';
                } else {
                    if (currentPlayer == ' ') { //if there's no player, display Game Over
                        status.setText("Game Over. Close the window to play a new game!");
                    } else { //if all else, just cycle through the current players
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        setCurrentPlayerText();
                        status.setText("It's now " + currentPlayerText + "'s turn.");
                    }
                }
            } //end mouseClicked
        } //end MouseAdapter
    } //end XOPanel class
}//end of file
