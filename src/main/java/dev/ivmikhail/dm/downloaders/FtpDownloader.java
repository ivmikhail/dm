package dev.ivmikhail.dm.downloaders;

import dev.ivmikhail.dm.Config;

import java.net.URL;

/**
 * {@link HttpDownloader} uses {@link java.net.URLConnection} so it can handle FTP download thru {@link sun.net.ftp.FtpClient}
 */
public class FtpDownloader extends HttpDownloader {
    public FtpDownloader(URL url, Config config) {
        super(url, config);
    }
}
