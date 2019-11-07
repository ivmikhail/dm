package dev.ivmikhail.dm.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {
    private ExceptionUtils() { /* utility class, no need to initialize */ }

    public static String toString(Throwable t) {
        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
