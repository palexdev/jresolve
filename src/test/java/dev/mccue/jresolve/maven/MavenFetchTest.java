package dev.mccue.jresolve.maven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import dev.mccue.jresolve.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MavenFetchTest {
    @Test
    public void fetchIntoStandardCache() throws IOException {
        var temp = Files.createTempDirectory("temp");
        var clojure = Dependency.mavenCentral("org.clojure:clojure:1.11.0");

        var libraries = new Resolve()
            .addDependency(clojure)
            .withCache(Cache.standard(temp))
            .fetch()
            .run()
            .libraries();

        assertEquals(
            Map.of(
                new Library("org.clojure", "clojure"),
                Path.of(
                    temp.toString(),
                    "org",
                    "clojure",
                    "clojure",
                    "1.11.0",
                    "clojure-1.11.0.jar"
                ),
                new Library("org.clojure", "spec.alpha"),
                Path.of(
                    temp.toString(),
                    "org",
                    "clojure",
                    "spec.alpha",
                    "0.3.218",
                    "spec.alpha-0.3.218.jar"
                ),
                new Library("org.clojure", "core.specs.alpha"),
                Path.of(
                    temp.toString(),
                    "org",
                    "clojure",
                    "core.specs.alpha",
                    "0.2.62",
                    "core.specs.alpha-0.2.62.jar"
                )
            ),
            libraries
        );
    }

    @Test
    public void fetchSources() throws IOException {
        var temp = Files.createTempDirectory("temp");
        var json = Dependency.mavenCentral("dev.mccue:json:0.2.3");

        var result = new Resolve()
            .addDependency(json)
            .withCache(Cache.standard(temp))
            .fetch()
            .includeSources()
            .includeDocumentation()
            .run();

        var sources = result.sources();


        assertEquals(
            Map.of(
                new Library("dev.mccue", "json"),
                Path.of(
                    temp.toString(),
                    "dev",
                    "mccue",
                    "json",
                    "0.2.3",
                    "json-0.2.3-sources.jar"
                )
            ),
            sources
        );
    }

    @Test
    public void dontFetchAnything() throws IOException {
        var temp = Files.createTempDirectory("temp");

        var clojure = Dependency.mavenCentral("org.clojure:clojure:1.11.0");

        var result = new Resolve()
            .addDependency(clojure)
            .withCache(Cache.standard(temp))
            .fetch()
            .includeLibraries(false)
            .run();

        assertEquals(new Fetch.Result(Map.of(), Map.of(), Map.of()), result);
    }

    @Test
    public void onlyFetchSources() throws IOException {
        var temp = Files.createTempDirectory("temp");

        var clojure = Dependency.mavenCentral("dev.mccue:json:0.2.3");

        var result = new Resolve()
            .addDependency(clojure)
            .withCache(Cache.standard(temp))
            .fetch()
            .includeLibraries(false)
            .includeSources()
            .run();

        assertEquals(new Fetch.Result(Map.of(), Map.of(
            new Library("dev.mccue", "json"),
            Path.of(
                temp.toString(),
                "dev",
                "mccue",
                "json",
                "0.2.3",
                "json-0.2.3-sources.jar"
            )
        ), Map.of()), result);
    }

    @Test
    public void onlyFetchDocumentation() throws IOException {
        var temp = Files.createTempDirectory("temp");

        var clojure = Dependency.mavenCentral("dev.mccue:json:0.2.3");

        var result = new Resolve()
            .addDependency(clojure)
            .withCache(Cache.standard(temp))
            .fetch()
            .includeLibraries(false)
            .includeDocumentation()
            .run();

        assertEquals(new Fetch.Result(Map.of(), Map.of(), Map.of(
            new Library("dev.mccue", "json"),
            Path.of(
                temp.toString(),
                "dev",
                "mccue",
                "json",
                "0.2.3",
                "json-0.2.3-javadoc.jar"
            )
        )), result);
    }

    @Test
    public void onlyFetchSourcesAndDocumentation() throws IOException {
        var temp = Files.createTempDirectory("temp");

        var clojure = Dependency.mavenCentral("dev.mccue:json:0.2.3");

        var result = new Resolve()
            .addDependency(clojure)
            .withCache(Cache.standard(temp))
            .fetch()
            .includeLibraries(false)
            .includeSources()
            .includeDocumentation()
            .run();

        assertEquals(new Fetch.Result(Map.of(),
            Map.of(
                new Library("dev.mccue", "json"),
                Path.of(
                    temp.toString(),
                    "dev",
                    "mccue",
                    "json",
                    "0.2.3",
                    "json-0.2.3-sources.jar"
                )
            ),
            Map.of(
                new Library("dev.mccue", "json"),
                Path.of(
                    temp.toString(),
                    "dev",
                    "mccue",
                    "json",
                    "0.2.3",
                    "json-0.2.3-javadoc.jar"
                )
            )
        ), result);
    }

    @Disabled
    @Test
    void fetchWithProfiles() throws IOException {
        var temp = Files.createTempDirectory("temp");

        var d1 = Dependency.mavenCentral("io.github.palexdev:materialfx:11.17.0");
        var d2 = Dependency.mavenCentral("io.github.palexdev:virtualizedfx:21.6.0");

        var result = new Resolve()
            .addDependency(d1)
            .addDependency(d2)
            .withCache(Cache.standard(temp))
            .fetch()
            .run();
    }
}
