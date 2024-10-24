package dev.mccue.jresolve.maven;

import java.util.List;

import dev.mccue.jresolve.*;

public class DocsTest {

    public static void main(String[] args) {
        var resolution = new Resolve()
            .addDependency(
                new Dependency(
                    new Library("com.auth0", "auth0"),
                    new MavenCoordinate(
                        new Group("com.auth0"),
                        new Artifact("auth0"),
                        new Version("2.3.0"),
                        List.of(MavenRepository.central()),
                        List.of(Scope.COMPILE, Scope.RUNTIME)
                    ),
                    Exclusions.of(
                        new Exclusion(
                            new Group("com.fasterxml.jackson.core"),
                            Artifact.ALL
                        )
                    )
                ))
            .run();

        resolution.printTree();
    }
}
