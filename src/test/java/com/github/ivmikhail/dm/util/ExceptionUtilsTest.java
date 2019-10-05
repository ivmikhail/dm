package com.github.ivmikhail.dm.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ExceptionUtilsTest {

    @Test
    public void testToString() {
        Exception cause = new Exception("root");
        Exception e = new Exception("Oh no", cause);

        String value = ExceptionUtils.toString(e);

        assertThat(value, containsString("java.lang.Exception: Oh no"));
        assertThat(value, containsString("Caused by: java.lang.Exception: root"));
    }
}