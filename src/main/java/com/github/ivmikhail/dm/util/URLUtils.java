package com.github.ivmikhail.dm.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public final class URLUtils {
    private URLUtils() { /* utility class, no need to initialize */ }

    public static String generateFileNameBy(URL u) {
        try {
            //TODO think about max file name length
            return URLEncoder.encode(u.toString(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static int getPort(URL u, int defaultPort) {
        return u.getPort() != -1 ? u.getPort() : defaultPort;
    }

    public static String getUser(URL u, String defaultUser) {
        String[] info = getUserInfo(u);
        if (info != null && info.length > 0) return info[0];
        return defaultUser;
    }

    public static String getPass(URL u, String defaultPassword) {
        String[] info = getUserInfo(u);
        if (info != null && info.length > 1) return info[1];
        return defaultPassword;
    }

    private static String[] getUserInfo(URL u) {
        String userInfo = u.getUserInfo();
        if (userInfo == null) return null;
        return userInfo.split(":");
    }
}
