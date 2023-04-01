package dev.mccue.resolve.maven;

import dev.mccue.resolve.Version;

import java.util.Optional;

/**
 * Maven allows "version ranges", snapshots, and other evil.
 */
sealed interface MavenVersion {
    record Known(Version version) implements MavenVersion {}
    record Range(
            Optional<Version> start,
            Optional<Version> end,
            boolean startInclusive,
            boolean endInclusive
    ) implements MavenVersion {}
}