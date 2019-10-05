package com.github.ivmikhail.dm.downloaders;

import com.github.ivmikhail.dm.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class HttpDownloader extends Downloader {
    private static final int BUF_SIZE = 4096;

    public HttpDownloader(URL url, Config config) {
        super(url, config);
    }

    @Override
    protected long downloadTo(File f) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setReadTimeout(config.getTimeoutMillis());
        conn.setConnectTimeout(config.getTimeoutMillis());
        conn.setRequestProperty("User-Agent", config.getUserAgent());

        long fileSize = conn.getContentLength();
        try (ReadableByteChannel in = Channels.newChannel(conn.getInputStream());
             FileOutputStream fos = new FileOutputStream(f);
             FileChannel output = fos.getChannel()) {

            ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
            while (in.read(buf) != -1) {
                buf.flip();
                output.write(buf);
                buf.rewind();
                buf.limit(buf.capacity());
            }
        }
        return fileSize;
    }
}