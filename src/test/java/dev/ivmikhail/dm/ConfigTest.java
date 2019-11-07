package dev.ivmikhail.dm;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class ConfigTest {

    private Config config;

    @Before
    public void setUp() {
        Properties props = new Properties();
        props.put("userAgent", "Mozilla");
        props.put("downloads.directory", "downloads");
        props.put("downloads.maxRunningCount", "2");
        props.put("url.connection.timeoutMillis", "1500");

        config = new Config(asList("1", "2"), props);
    }

    @Test
    public void getUrls() {
        assertThat(config.getUrls(), is(asList("1", "2")));
    }

    @Test
    public void getUserAgent() {
        assertEquals("Mozilla", config.getUserAgent());
    }

    @Test
    public void getDownloadDirectory() {
        assertEquals("downloads", config.getDownloadDirectory().toString());
    }

    @Test
    public void getMaxRunningDownloads() {
        assertEquals(2, config.getMaxRunningDownloads());
    }

    @Test
    public void getTimeoutMillis() {
        assertEquals(1500, config.getTimeoutMillis());
    }
}