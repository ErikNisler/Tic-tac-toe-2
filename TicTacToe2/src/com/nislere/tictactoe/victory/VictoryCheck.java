package com.nislere.tictactoe.victory;

import com.nislere.tictactoe.board.Field;
import com.nislere.tictactoe.marker.MarkerTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class VictoryCheck implements VictoryType{

    private static final int VICTORY_MATCHING = 5;

    public boolean check(MarkerTypeEnum marker, Field selectedField, Field[][] arrangedFields) {
        boolean result = false;
        for (VictoryTypeEnum type: VictoryTypeEnum.values()) {
            if (VictoryTypeEnum.HORIZONTALLY.equals(type)) {
                result = horizontallyEvaluate(marker, selectedField, arrangedFields);
            }
            if (VictoryTypeEnum.VERTICALLY.equals(type)) {
                result = verticallyEvaluate(marker, selectedField, arrangedFields);
            }
            if (VictoryTypeEnum.UP_TO_DOWN_DIAGONALLY.equals(type)) {
                result = mainDiagonalAndItsChildrenEvaluate(marker, arrangedFields);
            }
            if (VictoryTypeEnum.DOWN_TO_UP_DIAGONALLY.equals(type)) {
                result = minorDiagonalAndItsChildrenEvaluate(marker, arrangedFields);
            }
            if (result) {
                return true;
            }
        }
        return false;
    }

    private boolean validate(MarkerTypeEnum playerMarker, String specificRowMarkers) {
        return specificRowMarkers.contains(getVictoryPattern(playerMarker));
    }

    private String getVictoryPattern(MarkerTypeEnum marker){
        String pattern = "";
        for (int i=0; i < VICTORY_MATCHING; i++) {
            pattern += marker.getStringFormat();
        }
        return pattern;
    }

    @Override
    public boolean horizontallyEvaluate(MarkerTypeEnum playerMarker, Field selectedField, Field[][] arrangedFields) {
        String row = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        int rowNum = getRow(selectedField, arrangedFields);

        for (int i = rowNum; i <= rowNum; i++) {
            for (int j = 0; j < arrangedFields.length; j++) {
                if (arrangedFields[i][j].isMarked()) {
                    String markedField = arrangedFields[i][j].getMarker();
                    row += markedField;
                } else {
                    row += "_";
                }
            }
            specificChildrenMap.put(rowNum, row);
        }
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

    private int getRow(Field selectedField, Field[][] arrangedFields) {
        int row = 0;
        for (int i = 0; i < arrangedFields.length; i++) {
            for (int j = 0; j < arrangedFields.length; j++) {
                if (arrangedFields[i][j] == selectedField) {
                    row = i;
                    break;
                }
            }
        }
        return row;
    }

    @Override
    public boolean verticallyEvaluate(MarkerTypeEnum playerMarker, Field field, Field[][] arrangedFields) {
        Field[][] transposedMatrix = transposeMatrix(arrangedFields);
        return horizontallyEvaluate(playerMarker, field, transposedMatrix);
    }

    private Field[][] transposeMatrix(Field[][] fields) {
        Field[][] matrix = new Field[fields.length][fields.length];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                matrix[j][i] = fields[i][j];
            }
        }
        return matrix;
    }

    @Override
    public boolean mainDiagonalAndItsChildrenEvaluate(MarkerTypeEnum playerMarker, Field[][] arrangedFields) {
        int numOfChildren= arrangedFields.length - 1;

        boolean upper = evaluateUpperChildren(playerMarker, numOfChildren, arrangedFields);
        boolean diagonal = evaluateDiagonal(playerMarker, arrangedFields);
        boolean lower = evaluateLowerChildren(playerMarker, numOfChildren, arrangedFields);

        return upper || diagonal || lower;
    }

    private boolean evaluateDiagonal(MarkerTypeEnum playerMarker, Field[][] arrangedFields) {
        String child = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        for (int i = 0; i < arrangedFields.length; i++) {
            if (arrangedFields[i][i].isMarked()) {
                String markedField = arrangedFields[i][i].getMarker();
                child += markedField;
            } else {
                child += "_";
            }
            specificChildrenMap.put(arrangedFields.length, child);
        }
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluateUpperChildren(MarkerTypeEnum playerMarker, int numOfChildren, Field[][] arrangedFields) {
        String child = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        for (int i = 0; i < numOfChildren; i++) {
            int row = 0;
            int col = 1 + i;
            for (int j = 0; j < numOfChildren; j++) {
                if (arrangedFields[row][col].isMarked()) {
                    String markedField = arrangedFields[row][col].getMarker();
                    child += markedField;
                } else {
                    child += "_";
                }
                row++;
                col++;
            }
            specificChildrenMap.put(numOfChildren, child);
            child="";
            numOfChildren--;
        }
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluateLowerChildren(MarkerTypeEnum playerMarker, int numOfChildren, Field[][] arrangedFields) {
        String child = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        for (int i = 0; i < numOfChildren; i++) {
            int row = 1 + i;
            int col = 0;
            for (int j = 0; j < numOfChildren; j++) {
                if (arrangedFields[row][col].isMarked()) {
                    String markedField = arrangedFields[row][col].getMarker();
                    child += markedField;
                } else {
                    child += "_";
                }
                row++;
                col++;
            }
            specificChildrenMap.put(numOfChildren, child);
            child="";
            numOfChildren--;
        }
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean minorDiagonalAndItsChildrenEvaluate(MarkerTypeEnum playerMarker, Field[][] arrangedFields) {
        int numOfChildren= arrangedFields.length - 1;

        boolean lower = evaluateMinorUpperChildren(playerMarker, numOfChildren, arrangedFields);
        boolean diagonal = evaluateMinorDiagonal(playerMarker, arrangedFields);
        boolean upper = evaluateMinorLowerChildren(playerMarker, numOfChildren, arrangedFields);

        return upper || diagonal || lower;
    }

    private boolean evaluateMinorDiagonal(MarkerTypeEnum playerMarker, Field[][] arrangedFields) {
        String child = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        int col = arrangedFields.length-1;
        int row = 0;
        for (int i = 0; i < arrangedFields.length; i++) {
            if (arrangedFields[row][col].isMarked()) {
                String markedField = arrangedFields[row][col].getMarker();
                child += markedField;
            } else {
                child += "_";
            }
            row++;
            col--;
        }
        specificChildrenMap.put(arrangedFields.length, child);
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluateMinorLowerChildren(MarkerTypeEnum playerMarker, int numOfChildren, Field[][] arrangedFields) {
        String child = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        for (int i = 0; i < numOfChildren; i++) {
            int row = 1 + i;
            int col = arrangedFields.length-1;
            for (int j = 0; j < numOfChildren; j++) {
                if (arrangedFields[row][col].isMarked()) {
                    String markedField = arrangedFields[row][col].getMarker();
                    child += markedField;
                } else {
                    child += "_";
                }
                row++;
                col--;
            }
            specificChildrenMap.put(numOfChildren, child);
            child="";
            numOfChildren--;
        }
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluateMinorUpperChildren(MarkerTypeEnum playerMarker, int numOfChildren, Field[][] arrangedFields) {
        String child = "";
        Map<Integer,String> specificChildrenMap = new HashMap<>();
        for (int i = 0; i < numOfChildren; i++) {
            int row = 0;
            int col = arrangedFields.length-2 - i;
            for (int j = 0; j < numOfChildren; j++) {
                if (arrangedFields[row][col].isMarked()) {
                    String markedField = arrangedFields[row][col].getMarker();
                    child += markedField;
                } else {
                    child += "_";
                }
                row++;
                col--;
            }
            specificChildrenMap.put(numOfChildren, child);
            child="";
            numOfChildren--;
        }
        for (String value: specificChildrenMap.values()) {
            if (validate(playerMarker, value)) {
                return true;
            }
        }
        return false;
    }

}
