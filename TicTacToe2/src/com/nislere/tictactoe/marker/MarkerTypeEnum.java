package com.nislere.tictactoe.marker;

public enum MarkerTypeEnum {
    X("X", 0),
    O("O", 1);

    private String stringFormat;
    private int id;

    MarkerTypeEnum(String stringFormat, int id){
        this.stringFormat = stringFormat;
        this.id = id;
    }

    public String getStringFormat() {
        return stringFormat;
    }

    public int getId(){
        return id;
    }
}
