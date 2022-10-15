package com.nislere.tictactoe.board;

import com.nislere.tictactoe.entity.Player;
import com.nislere.tictactoe.game.Game;
import com.nislere.tictactoe.victory.VictoryCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Board extends JFrame implements ActionListener {

    private static int rounds = 0;

    private static final int FIELD_WIDTH_ANCHOR = 50;
    private static final int FIELD_HEIGHT_ANCHOR = 50;
    private static final int BOARD_DIMENSION = 15;
    private static final int NUM_OF_FIELDS = (int) Math.pow(BOARD_DIMENSION, 2);

    private final Player player1;
    private final Player player2;
    private VictoryCheck victoryCheck = new VictoryCheck();

    private static Field[][] FIELDS = new Field[BOARD_DIMENSION][BOARD_DIMENSION];

    private JLabel playerTurnLabel = new JLabel();

    public Board(String appName, List<Player> players){
        JPanel panel = new JPanel();
        panel.setSize(770,790);
        panel.setVisible(true);
        panel.setBackground(Color.WHITE);

        this.setTitle(appName);
        this.setSize(770,790);
        this.setResizable(false);

        initFields(FIELDS, this);

        player1 = players.get(0);
        player2 = players.get(1);

        setListenerToFields(FIELDS);

        playerTurnLabel.setBounds(10,10,500,40);
        playerTurnLabel.setFont((new Font("Arial", Font.BOLD, 20)));
        playerTurnLabel.setText("It's the player " + player1.getName() + " turn!");
        this.add(playerTurnLabel);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setListenerToFields(Field[][] fields) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j].addActionListener(this);
            }
        }
    }

    private void initFields(Field[][] fields, JFrame frame) {
        int xAxis = 0;
        int yAxis = 100;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                Field field = new Field();
                fields[i][j] = field;
                field.setBounds(xAxis, yAxis, FIELD_WIDTH_ANCHOR, FIELD_HEIGHT_ANCHOR);
                xAxis += 50;
                frame.add(field);
            }
            xAxis = 0;
            yAxis += 50;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Field selectedField = (Field) e.getSource();
        if (player1.isPlayerTurn()) {
            playerTurnLabel.setText("It's the " + player2.getName() + "'s turn!");
            markUpField(player1, selectedField);
            player1.setPlayerTurn(false);
            player2.setPlayerTurn(true);
        } else if (player2.isPlayerTurn()) {
            playerTurnLabel.setText("It's the " + player1.getName() + "'s turn!");
            markUpField(player2, selectedField);
            player2.setPlayerTurn(false);
            player1.setPlayerTurn(true);
            }
        rounds++;
        if (rounds == NUM_OF_FIELDS) {
            JOptionPane.showMessageDialog(this, "Draw!", "End of the game!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void markUpField(Player player, Field selectedField) {
        selectedField.setMarker(player.getMarkerType());
        selectedField.setEnabled(false);

        boolean matched = victoryCheck.check(player.getMarkerType(), selectedField, FIELDS);
        if (matched) {
            popUpFinishDialog(player);
        }
    }

    private void popUpFinishDialog(Player player) {
        Object[] options = {"Exit","Play again!"};
        int response = JOptionPane.showOptionDialog(this,
                "Congratulations! Winner is " + player.getName(), "Game over!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (response == 0) {
            System.exit(0);
        }
        if (response == 1) {
            this.dispose();

            Game game = new Game();
            game.startGame();
        }
    }

}
