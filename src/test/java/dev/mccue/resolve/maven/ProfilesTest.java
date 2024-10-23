package dev.mccue.resolve.maven;

public class ProfilesTest {
    /*@Test
    @Disabled("Profiles not yet supported")
    public void testJDKActivation() throws IOException {
        var dir = Files.createTempDirectory("temp");
        var result = new Resolve()
                .withCache(Cache.standard(dir))
                .addDependency(Dependency.mavenCentral("com.zaxxer:HikariCP:5.0.1"))
                .run();

        // Should get 2.0.0 for slf4j
        assertEquals(
                result.selectedDependencies()
                        .stream()
                        .map(DependencyId::new)
                        .collect(Collectors.toSet()),
                Set.of(
                        new DependencyId(
                                new Library("com.zaxxer", "HikariCP"),
                                new MavenCoordinateId(
                                        new Group(""),
                                        new Artifact(""),
                                        new Version("5.0.1"))
                        ),
                        new DependencyId(
                                new Library("org.slf4j", "slf4j-api"),
                                // 1.7.30 without considering the activation part
                                new MavenCoordinateId(
                                        new Group("org.slf4j"),
                                        new Artifact("slf4j-api"),
                                        new Version("2.0.0"))
                        )
                ));
    }

    @Test
    @Disabled("Not sure exactly what to test yet, but know nd4j was a problem for coursier")
    public void testOsActivation() {
        new Resolve()
                .addDependency(Dependency.mavenCentral("org.nd4j:nd4j-native:0.5.0"))
                .withExecutorService(Executors.newSingleThreadExecutor())
                .run()
                .printTree();

        // org.nd4j:nd4j-native:0.5.0

        // somehow requires group=org.nd4j, artifact=oss-parent which does not exist
    }*/
}
