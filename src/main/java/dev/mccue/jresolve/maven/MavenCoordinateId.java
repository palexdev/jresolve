package dev.mccue.jresolve.maven;

import dev.mccue.jresolve.Artifact;
import dev.mccue.jresolve.CoordinateId;
import dev.mccue.jresolve.Group;
import dev.mccue.jresolve.Version;

public record MavenCoordinateId(
    Group group,
    Artifact artifact,
    Version version
) implements CoordinateId {

}
