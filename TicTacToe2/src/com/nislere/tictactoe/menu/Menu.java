package com.nislere.tictactoe.menu;

import com.nislere.tictactoe.board.Board;
import com.nislere.tictactoe.entity.Player;
import com.nislere.tictactoe.marker.MarkerTypeEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame implements ActionListener {

    private static final String GAME_NAME = "TIC TAC TOE 2";

    private int playerIncrement = 1;

    private final JLabel name= new JLabel();
    private final JTextField nameField = new JTextField();
    private final JButton createPlayerButton = new JButton("Create");
    private final JComboBox comboBox = new JComboBox(MarkerTypeEnum.values());
    private final List<Player> players = new ArrayList<>(2);
    private boolean firstPlayer = true;

    public Menu(String appName){
        this.setTitle(appName);
        this.setSize(350,250);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();

        createPlayerButton.addActionListener(this);
    }

    private void initComponents() {
        name.setBounds(50,40,200,40);
        name.setFont(new Font("Arial", Font.BOLD, 20));
        name.setText("Name of " + playerIncrement + ". player:");
        nameField.setBounds(50,90,180,30);
        createPlayerButton.setBounds(50, 130,90, 30);
        comboBox.setBounds(160, 130, 60, 30);
        this.add(name);
        this.add(nameField);
        this.add(createPlayerButton);
        this.add(comboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!nameField.getText().isEmpty()) {
            playerIncrement++;
            name.setText("Name of " + playerIncrement + ". player:");
        } else {
            JOptionPane.showMessageDialog(this, "Name is blank!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        comboBox.setEnabled(false);
        String playerName = nameField.getText();
        MarkerTypeEnum selectedMarker = (MarkerTypeEnum) comboBox.getSelectedItem();
        nameField.setText("");
        if (firstPlayer) {
            players.add(new Player(playerName, selectedMarker, true));
            firstPlayer = false;
        } else {
            players.add(new Player(playerName, getOtherMarker(selectedMarker), false));
            this.dispose();
            new Board(GAME_NAME, players);
        }
    }

    private MarkerTypeEnum getOtherMarker(MarkerTypeEnum marker){
        return marker.equals(MarkerTypeEnum.O) ? MarkerTypeEnum.X : MarkerTypeEnum.O;
    }
}
