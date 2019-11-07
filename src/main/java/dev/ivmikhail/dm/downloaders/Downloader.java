package dev.ivmikhail.dm.downloaders;


import dev.ivmikhail.dm.Config;
import dev.ivmikhail.dm.DownloadException;
import dev.ivmikhail.dm.util.URLUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public abstract class Downloader {
    protected URL url;
    protected Config config;

    public Downloader(URL url, Config config) {
        this.url = url;
        this.config = config;
    }

    public static Downloader create(String url, Config config) throws MalformedURLException {
        URL resource = new URL(url);
        String protocol = resource.getProtocol();

        if (protocol.equals("http") || protocol.equals("https")) {
            return new HttpDownloader(resource, config);
        } else if (protocol.equals("ftp")) {
            return new FtpDownloader(resource, config);
        } else if (protocol.equals("sftp")) {
            return new SftpDownloader(resource, config);
        } else {
            throw new UnsupportedOperationException("URL protocol not supported, sorry");
        }
    }

    public Path start() throws IOException {
        File tempFile = File.createTempFile("prefix", null);
        tempFile.deleteOnExit(); //this file will be deleted by Environment in case of interruption

        long fileSize = downloadTo(tempFile);
        validateFile(fileSize, tempFile);

        String destFileName = URLUtils.generateFileNameBy(url);
        Path destPath = config.getDownloadDirectory().resolve(destFileName);

        return Files.move(tempFile.toPath(), destPath, REPLACE_EXISTING);
    }

    protected abstract long downloadTo(File f) throws IOException;

    protected void validateFile(long fileSize, File f) {
        if (fileSize != f.length()) throw new DownloadException("Downloaded file size doesn't match");
    }
}
