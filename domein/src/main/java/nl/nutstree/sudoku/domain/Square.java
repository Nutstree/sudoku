package nl.nutstree.sudoku.domain;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Square implements Container {
    abstract BaseContainer getBaseContainer();

    public List<Integer> getValues() {
        return getBaseContainer().getValues();
    }

    public static Square create(List<Cell> cells) {
        BaseContainer baseContainer = BaseContainer.create(cells);
        return new AutoValue_Square(baseContainer);
    }
}
