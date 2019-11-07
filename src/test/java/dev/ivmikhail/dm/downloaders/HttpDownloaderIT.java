package dev.ivmikhail.dm.downloaders;

import dev.ivmikhail.dm.Config;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class HttpDownloaderIT {
    private File file; //let's download this file and compare hashes
    private Server jetty;

    @Before
    public void setUp() throws Exception {
        file = File.createTempFile("prefix", null);
        file.deleteOnExit();
        Files.write(UUID.randomUUID().toString().getBytes(), file); //write some random data

        jetty = new Server(0);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(file.getParent());

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()});
        jetty.setHandler(handlers);

        jetty.start();
    }

    @After
    public void tearDown() throws Exception {
        jetty.stop();
        file.delete();
    }

    @Test
    public void testDownloadTo() throws IOException {
        String fileUrl = jetty.getURI() + file.getName();

        Properties props = new Properties();
        props.put("userAgent", "Mozilla");
        props.put("downloads.directory", "downloads");
        props.put("downloads.maxRunningCount", "2");
        props.put("url.connection.timeoutMillis", "1500");
        Config config = new Config(emptyList(), props);

        HttpDownloader downloader = new HttpDownloader(new URL(fileUrl), config);

        File downloadedFile = File.createTempFile("target", null);
        downloadedFile.deleteOnExit();

        downloader.downloadTo(downloadedFile);

        HashCode hashCode1 = Files.hash(file, Hashing.sha256());
        HashCode hashCode12= Files.hash(downloadedFile, Hashing.sha256());
        assertEquals(hashCode1, hashCode12);
    }
}