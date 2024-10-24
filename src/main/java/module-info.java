module dev.mccue.jresolve {
    requires static org.jspecify;

    requires java.xml;
    requires transitive java.net.http;

    exports dev.mccue.jresolve;
    exports dev.mccue.jresolve.maven;
}