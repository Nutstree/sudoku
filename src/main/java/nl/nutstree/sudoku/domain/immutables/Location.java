package nl.nutstree.sudoku.domain.immutables;

public interface Location {
    int getRow();

    int getColumn();

    int getQuadrant();
}
