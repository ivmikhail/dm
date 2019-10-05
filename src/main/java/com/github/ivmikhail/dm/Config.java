package com.github.ivmikhail.dm;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class Config {
    private List<String> urls;
    private String userAgent;
    private Path downloadDirectory;
    private int maxRunningDownloads;
    private int connectTimeoutMillis;

    public List<String> getUrls() {
        return urls;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Path getDownloadDirectory() {
        return downloadDirectory;
    }

    public int getMaxRunningDownloads() {
        return maxRunningDownloads;
    }

    public int getTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public Config(List<String> urls, Properties props) {
        this.urls = urls;
        this.downloadDirectory = Paths.get(props.getProperty("downloads.directory"));
        this.maxRunningDownloads = parseInt(props.getProperty("downloads.maxRunningCount"));
        this.connectTimeoutMillis = parseInt(props.getProperty("url.connection.timeoutMillis"));
        this.userAgent = props.getProperty("userAgent");
    }
}
