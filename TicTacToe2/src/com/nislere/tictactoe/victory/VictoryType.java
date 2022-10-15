package com.nislere.tictactoe.victory;

import com.nislere.tictactoe.board.Field;
import com.nislere.tictactoe.marker.MarkerTypeEnum;

public interface VictoryType {

    boolean horizontallyEvaluate(MarkerTypeEnum markerTypeEnum, Field selectedField, Field[][] arrangedFields);

    boolean verticallyEvaluate(MarkerTypeEnum markerTypeEnum, Field selectedField, Field[][] arrangedFields);

    boolean mainDiagonalAndItsChildrenEvaluate(MarkerTypeEnum markerTypeEnum, Field[][] arrangedFields);

    boolean minorDiagonalAndItsChildrenEvaluate(MarkerTypeEnum markerTypeEnum, Field[][] arrangedFields);

}
