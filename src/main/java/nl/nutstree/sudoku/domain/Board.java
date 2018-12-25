package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.*;

public class Board {




    public Optional<Integer> getValue(Position position) {
        return Optional.empty();
    }

    public Collection<Integer> getPossibilities(Position position) {
        return new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8 , 9));
    }

    public void setValue(Position position, int value) {


    }
}
