package dev.mccue.jresolve.maven;

import dev.mccue.jresolve.Artifact;
import dev.mccue.jresolve.Group;
import dev.mccue.jresolve.Version;

final class ArtifactNotFound extends RuntimeException {
    final Group group;
    final Artifact artifact;
    final Version version;

    ArtifactNotFound(Group group, Artifact artifact) {
        this.group = group;
        this.artifact = artifact;
        this.version = null;
    }

    ArtifactNotFound(Group group, Artifact artifact, Version version) {
        this.group = group;
        this.artifact = artifact;
        this.version = version;
    }

    ArtifactNotFound(Throwable throwable, Group group, Artifact artifact, Version version) {
        super(throwable);
        this.group = group;
        this.artifact = artifact;
        this.version = version;
    }

    @Override
    public String getMessage() {
        return "LibraryNotFound[group=" + group + ", artifact=" + artifact + ", version=" + version + "]";
    }
}
