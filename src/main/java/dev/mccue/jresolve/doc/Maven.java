package dev.mccue.jresolve.doc;

public @interface Maven {
    String value();

    String details() default "";
}
