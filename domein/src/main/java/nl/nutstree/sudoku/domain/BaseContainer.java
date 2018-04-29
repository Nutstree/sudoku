package nl.nutstree.sudoku.domain;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AutoValue
public abstract class BaseContainer implements Container {
    public abstract List<Cell> getCells();

    public List<Integer> getValues() {
        return getCells().stream()
                .map(Cell::getValue)
                .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.toList());
    }

    public static BaseContainer create(List<Cell> cells) {
        return new AutoValue_BaseContainer(cells);
    }
}

