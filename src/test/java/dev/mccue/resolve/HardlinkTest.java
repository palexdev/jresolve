package dev.mccue.resolve;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HardlinkTest {
    @Test
    public void createHardlinks() throws IOException {
        var resolved = new Resolve()
            .addDependencies(List.of(
                Dependency.mavenCentral("dev.mccue:json:0.2.3")
            ))
            .fetch()
            .run();

        var libs = Path.of("libs");
        try {
            Files.walkFileTree(libs, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (NoSuchFileException ignored) {}


        try {
            Files.createDirectories(libs);
            for (var path : resolved.libraries().values()) {
                // If developing on an external drive, this fails
                // Just link to system temp directory
                Files.createLink(Path.of(System.getProperty("java.io.tmpdir"), path.getFileName().toString()), path);
            }

        } finally {
            // Also clean links otherwise subsequent runs will fail
            for (Path path : resolved.libraries().values()) {
                Path link = Path.of(System.getProperty("java.io.tmpdir"), path.getFileName().toString());
                Files.delete(link);
            }
        }


    }
}
