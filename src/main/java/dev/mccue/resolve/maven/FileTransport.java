package dev.mccue.resolve.maven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

public final class FileTransport implements Transport {
    private final Path root;

    public FileTransport(Path root) {
        this.root = root;
    }

    @Override
    public GetFileResult getFile(List<String> pathElements) {
        try {
            return new GetFileResult.Success(
                Files.newInputStream(Path.of(
                    root.toString(),
                    pathElements.toArray(String[]::new)
                )),
                OptionalLong.empty()
            );
        } catch (NoSuchFileException e) {
            return new GetFileResult.NotFound();
        } catch (IOException e) {
            return new GetFileResult.Error(e);
        }
    }
}
