package dev.mccue.jresolve.maven;


import java.util.List;

import dev.mccue.jresolve.doc.Coursier;

@Coursier("https://github.com/coursier/coursier/blob/f5f0870/modules/core/shared/src/main/scala/coursier/core/Definitions.scala#L228-L268")
record PomInfo(
    PomGroupId groupId,
    PomArtifactId artifactId,
    PomVersion version,

    List<PomDependency> dependencies,

    PomParent parent,

    List<PomDependency> dependencyManagement,

    List<PomProperty> properties,

    PomPackaging packaging,
    List<PomProfile> profiles
) {
}
