package dev.mccue.jresolve.maven;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import dev.mccue.jresolve.doc.Coursier;
import dev.mccue.jresolve.doc.Maven;

/**
 * Certain build profiles take into account the current architecture
 * and operating system being used.
 *
 * <p>
 * This can change the set of dependencies being considered.
 * </p>
 */
@Maven("""
        https://maven.apache.org/enforcer/enforcer-rules/requireOS.html
        https://maven.apache.org/guides/introduction/introduction-to-profiles.html#os
    """)
@Coursier(
    "https://github.com/coursier/coursier/blob/6b2c581493011d14423827246574772d8bad663a/modules/core/shared/src/main/scala/coursier/core/Activation.scala#L59"
)
public record Os(
    String name,
    String arch,
    String version
) {
    private static final Set<String> STANDARD_FAMILIES = Set.of(
        "windows",
        "os/2",
        "netware",
        "mac",
        "os/400",
        "openvms"
    );

    private static final Set<String> KNOWN_FAMILIES;

    static {
        var knownFamilies = new HashSet<>(STANDARD_FAMILIES);
        knownFamilies.add("dos");
        knownFamilies.add("tandem");
        knownFamilies.add("win9x");
        knownFamilies.add("z/os");
        KNOWN_FAMILIES = Set.copyOf(knownFamilies);
    }

    public Os(
        String name,
        String arch,
        String version
    ) {
        var archNormalized = arch.toLowerCase(Locale.US);
        archNormalized = archNormalized.equals("x86-64") ? "x86_64" : archNormalized;

        var nameNormalized = name.toLowerCase(Locale.US);
        var versionNormalized = version.toLowerCase(Locale.US);

        this.name = nameNormalized;
        this.arch = archNormalized;
        this.version = versionNormalized;

    }

    public Os() {
        this(
            System.getProperty("os.name").toLowerCase(Locale.US),
            System.getProperty("os.arch").toLowerCase(Locale.US),
            System.getProperty("os.version").toLowerCase(Locale.US)
        );
    }
}
