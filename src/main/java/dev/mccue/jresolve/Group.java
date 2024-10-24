package dev.mccue.jresolve;

import java.util.Objects;
import java.util.function.Function;

import dev.mccue.jresolve.doc.Coursier;

@Coursier("https://github.com/coursier/coursier/blob/f5f0870/modules/core/shared/src/main/scala/coursier/core/Definitions.scala#L8-L9")
public record Group(String value) implements Comparable<Group> {
    public static final Group ALL = new Group("*");

    public Group {
        Objects.requireNonNull(value, "value must not be null");
    }

    Group map(Function<String, String> f) {
        return new Group(f.apply(this.value));
    }

    @Override
    public int compareTo(Group o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
