package com.github.ivmikhail.dm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public final class LoadProperties {

    private LoadProperties() { /* helper static class */}

    /**
     * / means root of classpath
     */
    public static Properties fromClasspath(String resourceName) {
        InputStream is = LoadProperties.class.getResourceAsStream(resourceName);
        if (is == null)
            throw new IllegalStateException("Properties file not found in classpath [" + resourceName + "]");

        return loadAndClose(is);
    }

    public static Properties fromFile(String path) {
        InputStream is = getFileAsStream(path);
        return loadAndClose(is);
    }

    private static InputStream getFileAsStream(String path) {
        try {
            return Files.newInputStream(Paths.get(path));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load properties file [" + path + "]", e);
        }
    }

    private static Properties loadAndClose(InputStream is) {
        Properties properties = new Properties();

        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            properties.load(isr);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to close InputStreamReader", e);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to close InputStream", ex);
            }
        }
        return properties;
    }
}