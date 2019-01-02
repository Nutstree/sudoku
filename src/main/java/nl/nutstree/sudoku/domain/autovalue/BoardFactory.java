package nl.nutstree.sudoku.domain.autovalue;

import nl.nutstree.sudoku.domain.Type;

public class BoardFactory {

    private static Type determineType(String puzzle) {
        return Type.valueOf((int) Math.sqrt(puzzle.length()));
    }
}
