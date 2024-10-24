package dev.mccue.jresolve.maven;

import java.util.Objects;

sealed interface PomParent {
    enum Undeclared implements PomParent {
        INSTANCE;

        @Override
        public String toString() {
            return "Undeclared[]";
        }
    }

    record Declared(
        PomGroupId.Declared groupId,
        PomArtifactId.Declared artifactId,
        PomVersion.Declared version
    ) implements PomParent {
        public Declared {
            Objects.requireNonNull(groupId);
            Objects.requireNonNull(artifactId);
            Objects.requireNonNull(version);
        }
    }
}
