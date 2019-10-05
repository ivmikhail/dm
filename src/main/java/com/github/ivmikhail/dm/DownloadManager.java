package com.github.ivmikhail.dm;

import com.github.ivmikhail.dm.downloaders.Downloader;
import com.github.ivmikhail.dm.util.ExceptionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;

public class DownloadManager {

    private Config config;

    public DownloadManager(Config config) {
        this.config = config;
    }

    public void start() {
        try {
            Files.createDirectories(config.getDownloadDirectory());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create download directory", e);
        }
        List<String> urls = config.getUrls();
        if (urls.size() == 0) return;

        int nThreads = Math.min(urls.size(), config.getMaxRunningDownloads());
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        urls.forEach(url -> asyncDownload(url, executor));
        executor.shutdown();
    }

    private void asyncDownload(final String url, final ExecutorService executor) {
        CompletableFuture
                .supplyAsync(() -> download(url), executor)
                .exceptionally(e -> String.format("Failed to download data from [%s] due %s", url, ExceptionUtils.toString(e)))
                .thenAccept(System.out::println);

    }

    private String download(String url) {
        try {
            Path filePath = Downloader.create(url, config).start();

            return String.format("Data from [%s] downloaded to [%s]", url, filePath);
        } catch (IOException e) {
            throw new CompletionException(e);
        }
    }
}
