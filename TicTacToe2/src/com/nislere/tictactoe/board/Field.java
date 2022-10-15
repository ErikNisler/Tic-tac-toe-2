package com.nislere.tictactoe.board;

import com.nislere.tictactoe.marker.MarkerTypeEnum;

import javax.swing.*;
import java.awt.*;
public class Field extends JButton{

    public Field() {
        super();
        this.setFont(new Font("Arial", Font.BOLD, 20));
    }

    public String getMarker() {
        return this.getText();
    }

    public void setMarker(MarkerTypeEnum marker) {
        this.setText(marker.getStringFormat());
    }

    public boolean isMarked() {
        return !this.isEnabled();
    }

}