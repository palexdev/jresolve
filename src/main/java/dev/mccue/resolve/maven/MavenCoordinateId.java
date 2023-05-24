package dev.mccue.resolve.maven;

import dev.mccue.resolve.CoordinateId;
import dev.mccue.resolve.Version;

record MavenCoordinateId(
        Version version
)
        implements CoordinateId {

    @Override
    public String toString() {
        return version.toString();
    }
}
