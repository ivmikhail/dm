package com.github.ivmikhail.dm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Permission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AppTest {
    private SecurityManager originalSecurityManager;
    private PrintStream originalSystemOut = System.out;

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        originalSecurityManager = System.getSecurityManager();

        SecurityManager nonExitSecurityManager = new SecurityManager() {
            @Override
            public void checkPermission(Permission permission) {
                if (permission.getName().startsWith("exitVM")) {
                    //exitVM.{exitCode}
                    throw new SecurityException("System exit not allowed");
                }
            }
        };
        System.setSecurityManager(nonExitSecurityManager);
        System.setOut(new PrintStream(outContent));//change out
    }

    @After
    public void tearDown() {
        System.setOut(originalSystemOut);//restore out
        System.setSecurityManager(originalSecurityManager);
    }

    @Test(expected = SecurityException.class)
    public void shouldPrintHelpAndExit() throws IOException {
        App.main(new String[]{});

        assertEquals("Usage: java [-Dapp.properties=/path/to/app.properties] -jar downloader.jar /path/to/urls.txt\n", outContent.toString());
    }

    @Test
    public void testMain() throws IOException {
        File f = File.createTempFile("prefix", null);

        App.main(new String[]{f.getAbsolutePath()});//pass empty file

        assertEquals("Start download data from []\n", outContent.toString());
        fail();
        f.delete();
    }
}