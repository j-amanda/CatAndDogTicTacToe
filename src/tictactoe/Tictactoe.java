
package tictactoe;

import javax.swing.JFrame;

/*******************************************************************************
 * 
 * Jordan Davidson
 * 
 * No other group members
 * CSIS 1410
 * Group Project 4 Tic-Tac-Toe
 * November 16, 2015
 * 
 * CREDITS *
 * 
 * Animal Sounds: http://www.wavsource.com/animals/animals.htm
 * 
 * Animal Images by Emi Monserrate: http://emimonserrate.deviantart.com/
 * 
 * Java Help from: https://www.youtube.com/channel/UCN-UBHk43K-0hWrSCLwsjSA
 * 
 * Audio Help from: http://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java
 * 
 ******************************************************************************/


public class Tictactoe {

    public static void main(String[] args) {
        JFrame board = new frame();
        board.setVisible(true);
        board.setTitle("Tic-Tac-Toe");
        board.setSize(300,300);
        board.setResizable(false);
        board.setLocationRelativeTo(null);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }
      
}
