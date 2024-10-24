package dev.mccue.jresolve.maven;

import java.util.List;

import dev.mccue.jresolve.Version;
import dev.mccue.jresolve.VersionRange;

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
