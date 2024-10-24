package dev.mccue.jresolve.maven;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.mccue.jresolve.Version;
import dev.mccue.jresolve.VersionRange;
import dev.mccue.jresolve.doc.Coursier;

@Coursier("https://github.com/coursier/coursier/blob/f5f0870/modules/core/shared/src/main/scala/coursier/core/Activation.scala")
record PomActivation(
    List<PomProperty> properties,
    Os os,
    Jdk jdk
) {
    sealed interface Jdk {
        record Unspecified() implements Jdk {}

        record Interval(VersionRange versionInterval) implements Jdk {}

        record SpecificVersions(List<Version> versions) implements Jdk {}
    }

    record Os(
        Optional<String> arch,
        Set<String> families,
        Optional<String> name,
        Optional<String> version
    ) {}
}