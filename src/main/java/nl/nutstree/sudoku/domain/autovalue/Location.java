package nl.nutstree.sudoku.domain.autovalue;

public interface Location {
    int getRow();

    int getColumn();

    int getQuadrant();
}
