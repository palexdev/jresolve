package dev.mccue.jresolve.maven;

import java.util.Objects;

import dev.mccue.jresolve.doc.Coursier;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Coursier("https://github.com/coursier/coursier/blob/f5f0870/modules/core/shared/src/main/scala/coursier/core/Definitions.scala#L145-L163")
public record Extension(String value) implements Comparable<Extension> {
    public static final Extension JAR = new Extension("jar");
    public static final Extension POM = new Extension("pom");
    public static final Extension EMPTY = new Extension("");

    public Extension {
        Objects.requireNonNull(value);
    }

    @Override
    public int compareTo(Extension extension) {
        return this.value.compareTo(extension.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
