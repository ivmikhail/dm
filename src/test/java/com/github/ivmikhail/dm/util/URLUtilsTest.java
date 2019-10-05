package com.github.ivmikhail.dm.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class URLUtilsTest {

    @Test
    public void testGetPortDefault() throws MalformedURLException {
        URL u = new URL("http://google.com");
        assertEquals(999, URLUtils.getPort(u, 999));
    }

    @Test
    public void testGetPort() throws MalformedURLException {
        URL u = new URL("http://google.com:42");
        assertEquals(42, URLUtils.getPort(u, 999));
    }

    @Test
    public void testGetUser() throws MalformedURLException {
        URL u = new URL("ftp://user:password@host:42/path");
        assertEquals("user", URLUtils.getUser(u, ""));
    }

    @Test
    public void testGetUserDefault() throws MalformedURLException {
        URL u = new URL("http://google.com");
        assertEquals("default", URLUtils.getUser(u, "default"));
    }

    @Test
    public void testGetPass() throws MalformedURLException {
        URL u = new URL("ftp://user:password@host:42/path");
        assertEquals("password", URLUtils.getPass(u, ""));
    }

    @Test
    public void testGetPassDefault() throws MalformedURLException {
        URL u = new URL("ftp://host:42/path");
        assertEquals("default", URLUtils.getPass(u, "default"));
    }
}