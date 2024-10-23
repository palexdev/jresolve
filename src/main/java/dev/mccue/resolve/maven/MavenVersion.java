package dev.mccue.resolve.maven;

import java.util.List;

import dev.mccue.resolve.Version;
import dev.mccue.resolve.VersionRange;

/**
 * Maven allows "version ranges", snapshots, and other evil.
 */
sealed interface MavenVersion {
    record Known(Version version) implements MavenVersion {}

    record Multiple(List<Version> version) implements MavenVersion {}

    record Range(VersionRange versionRange) implements MavenVersion {}

    static MavenVersion parse(String value) {
        value = value.trim();

        if (value.contains(",")) {
            if (value.startsWith("[") || value.startsWith("(")) {

            }
        }


        return new Known(new Version(value));
    }
}
