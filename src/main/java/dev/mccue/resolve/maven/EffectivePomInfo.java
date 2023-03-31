package dev.mccue.resolve.maven;

import dev.mccue.resolve.doc.Rife;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents information derived from a POM after all information
 * from Parent poms is merged in and property values have been substituted.
 */
record EffectivePomInfo(
        PomGroupId groupId,
        PomArtifactId artifactId,
        PomVersion version,
        List<PomDependency> dependencies,
        List<PomDependency> dependencyManagement,
        PomPackaging packaging
) {

    static EffectivePomInfo from(final ChildHavingPomInfo childHavingPomInfo) {
        var properties = new LinkedHashMap<String, String>();

        var top = childHavingPomInfo;
        while (top != null) {
            for (PomProperty property : top.properties()) {
                properties.put(property.key(), property.value());
            }
            top = top.child().orElse(null);
        }

        Function<String, String> resolve =
                str -> resolveProperties(properties, str);

        Function<PomDependency, PomDependency> resolveDep = dependency ->
                new PomDependency(
                        dependency.groupId().map(resolve),
                        dependency.artifactId().map(resolve),
                        dependency.version().map(resolve),
                        dependency.exclusions().stream()
                                .map(exclusion -> new PomExclusion(
                                        exclusion.groupId().map(resolve),
                                        exclusion.artifactId().map(resolve)
                                ))
                                .collect(Collectors.toUnmodifiableSet()),
                        dependency.type().map(resolve),
                        dependency.classifier().map(resolve),
                        dependency.optional().map(resolve),
                        dependency.scope().map(resolve)
                );

        PomGroupId groupId = PomGroupId.Undeclared.INSTANCE;
        PomVersion version = PomVersion.Undeclared.INSTANCE;
        PomPackaging packaging = PomPackaging.Undeclared.INSTANCE;
        var dependencies = new LinkedHashMap<PomDependencyKey, PomDependency>();
        var dependencyManagement = new LinkedHashMap<PomDependencyKey, PomDependency>();

        top = childHavingPomInfo;
        while (top != null) {
            if (top.groupId() instanceof PomGroupId.Declared) {
                groupId = top.groupId().map(resolve);
            }
            if (top.version() instanceof PomVersion.Declared) {
                version = top.version().map(resolve);
            }
            if (top.packaging() instanceof PomPackaging.Declared) {
                packaging = top.packaging().map(resolve);
            }

            top = top.child().orElse(null);
        }

        groupId.ifDeclared(value -> properties.put("project.groupId", value));
        version.ifDeclared(value -> properties.put("project.version", value));


        var artifactId = childHavingPomInfo.artifactId().map(resolve);
        artifactId.ifDeclared(value -> properties.put("project.artifactId", value));

        top = childHavingPomInfo;
        while (top != null) {

            top.dependencies()
                    .forEach(dependency -> {
                        var newDep = resolveDep.apply(dependency);
                        dependencies.put(PomDependencyKey.from(newDep), newDep);
                    });

            top.dependencyManagement()
                    .stream()
                    // TODO: Handle BOMs
                    .filter(managedDep -> !managedDep.scope().orElse(Scope.COMPILE).equals(Scope.IMPORT))
                    .forEach(dependency -> {
                        var newDep = resolveDep.apply(dependency);
                        dependencyManagement.put(PomDependencyKey.from(newDep), newDep);
                    });

            top = top.child().orElse(null);
        }

        return new EffectivePomInfo(
                groupId,
                artifactId,
                version,
                dependencies.values().stream().toList(),
                dependencyManagement.values().stream().toList(),
                packaging
        );
    }

    /*
    This is because the minimal set of information for matching a dependency reference against a dependencyManagement section is actually {groupId, artifactId, type, classifier}.
    https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html
     */
    private record PomDependencyKey(
            PomGroupId groupId,
            PomArtifactId artifactId,
            PomVersion version,
            PomClassifier classifier,
            PomType type
    ) {
        static PomDependencyKey from(PomDependency pomDependency) {
            return new PomDependencyKey(
                    pomDependency.groupId(),
                    pomDependency.artifactId(),
                    pomDependency.version(),
                    pomDependency.classifier(),
                    pomDependency.type()
            );
        }
    }

    private static final Pattern MAVEN_PROPERTY = Pattern.compile("\\$\\{([^<>{}]+)}");

    @Rife("")
    private static String resolveProperties(Map<String, String> properties, String data) {
        if (data == null) {
            return null;
        }

        var processed_data = new StringBuilder();
        var matcher = MAVEN_PROPERTY.matcher(data);
        var last_end = 0;
        while (matcher.find()) {
            if (matcher.groupCount() == 1) {
                var property = matcher.group(1);
                if (properties.containsKey(property)) {
                    processed_data.append(data, last_end, matcher.start());
                    processed_data.append(properties.get(property));
                    last_end = matcher.end();
                }
            }
        }
        if (last_end < data.length()) {
            processed_data.append(data.substring(last_end));
        }

        return processed_data.toString();
    }
}