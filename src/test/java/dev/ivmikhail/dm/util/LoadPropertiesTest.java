package dev.ivmikhail.dm.util;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class LoadPropertiesTest {

    @Test
    public void testLoadFromClasspath() {
        Properties properties = LoadProperties.fromClasspath("/test.properties");
        assertEquals(1, properties.size());
        assertEquals("hello", properties.getProperty("property1"));
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadFromClasspathFail() {
        LoadProperties.fromClasspath("/fake.properties");
    }

    @Test
    public void testLoadFromFile() throws IOException {
        //load from resource and save to temp file
        Properties properties = LoadProperties.fromClasspath("/test.properties");
        File temp = File.createTempFile("junit.test.app.properties.", ".tmp");
        temp.deleteOnExit();
        properties.store(new FileWriter(temp), "");

        //test loading from test file
        Properties p = LoadProperties.fromFile(temp.getAbsolutePath());
        assertEquals(1, p.size());
        assertEquals("hello", p.getProperty("property1"));
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadFromNonFile() {
        LoadProperties.fromFile(".");
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadFromNonExistentFile() throws IOException {
        File temp = File.createTempFile("junit.test.app.properties.", ".tmp");
        temp.delete();

        LoadProperties.fromFile(temp.getAbsolutePath());
    }
}