package dev.mccue.jresolve.maven;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import dev.mccue.jresolve.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DependencyOverrideTest {
    @Test
    public void testTopLevelOverride() throws IOException {
        var tempDir = Files.createTempDirectory("temp");
        var resolution = new Resolve()
            .addDependency(Dependency.mavenCentral("org.clojure:clojure:1.11.0"))
            .addDependencyOverride(Dependency.mavenCentral("org.clojure:clojure:1.10.0"))
            .withCache(Cache.standard(tempDir))
            .run();

        assertEquals(
            resolution.selectedDependencies()
                .stream()
                .filter(dep -> dep
                    .library()
                    .equals(
                        new Library(new Group("org.clojure"), new Artifact("clojure"))
                    ))
                .toList(),
            List.of(Dependency.mavenCentral("org.clojure:clojure:1.10.0"))
        );
    }

    @Test
    public void testSecondLevelOverride() throws IOException {
        var tempDir = Files.createTempDirectory("temp");
        var resolution = new Resolve()
            .addDependency(Dependency.mavenCentral("org.clojure:clojure:1.11.0"))
            .addDependencyOverride(Dependency.mavenCentral("org.clojure:spec.alpha:0.3.214"))
            .withCache(Cache.standard(tempDir))
            .run();

        assertEquals(
            resolution.selectedDependencies()
                .stream()
                .filter(dep -> dep
                    .library()
                    .equals(
                        new Library(new Group("org.clojure"), new Artifact("spec.alpha"))
                    ))
                .toList(),
            List.of(Dependency.mavenCentral("org.clojure:spec.alpha:0.3.214"))
        );
    }
}
