package nl.nutstree.sudoku;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS) // Make it class retention for incremental compilation
@Value.Style(
        get = {"is*", "get*"}, // Detect 'get' and 'is' prefixes in accessor methods
        typeAbstract = {"Abstract*"}, // 'Abstract' prefix will be detected and trimmed
        builder = "new") // construct builder using 'new' instead of factory method
public @interface ImmutableStyle {}
