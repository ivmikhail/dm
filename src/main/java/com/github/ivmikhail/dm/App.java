package com.github.ivmikhail.dm;

import com.github.ivmikhail.dm.util.LoadProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static java.util.stream.Collectors.toList;

public final class App {
    private static final String PROPERTIES_CLASSPATH = "/app.properties";
    private static final String PROPERTIES_KEY = "app.properties";

    private App() { /* class with main method, no need to initialize */}

    public static void main(String[] args) throws IOException {
        Config conf = getConfOrExit(args);
        System.out.println("Start download data from " + conf.getUrls());
        DownloadManager manager = new DownloadManager(conf);
        manager.start();
    }

    private static Config getConfOrExit(String[] args) throws IOException {
        if (args.length != 1) {
            printHelp();
            Runtime.getRuntime().exit(0);
        }
        List<String> urls = Files.readAllLines(Paths.get(args[0]))
                .stream()
                .filter(s -> !s.isEmpty() && !s.startsWith("#")) //remove empty and commented urls
                .collect(toList());

        String propertiesPath = System.getProperty(PROPERTIES_KEY);
        Properties properties;
        if (propertiesPath == null) {
            properties = LoadProperties.fromClasspath(PROPERTIES_CLASSPATH);
        } else {
            properties = LoadProperties.fromFile(propertiesPath);
        }

        return new Config(urls, properties);
    }

    private static void printHelp() {
        System.out.printf("Usage: java [-D%s=/path/to/app.properties] -jar dm.jar /path/to/urls.txt", PROPERTIES_KEY);
    }
}
