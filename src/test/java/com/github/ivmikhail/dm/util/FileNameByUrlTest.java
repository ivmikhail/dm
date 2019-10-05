package com.github.ivmikhail.dm.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class FileNameByUrlTest {

    @Parameterized.Parameters
    public static Collection<URL> data() throws MalformedURLException {
        return asList(
                new URL("https://en.wikipedia.org/wiki/URL"),
                new URL("ftp://jamesc@ftp.harlequin.com/foo.html"),
                new URL("http://host/path/to/file.txt?key1=value1&key2=value2&key1=value1;key2=value2#ref"),
                new URL("http://ヒキワリ.ナットウ.ニホン")
        );
    }

    private URL url;

    public FileNameByUrlTest(URL url) {
        this.url = url;
    }

    @Test
    public void test() throws IOException {
        String fileName = URLUtils.generateFileNameBy(url);
        File f = File.createTempFile(fileName, "tmp");//if the file is created, the name is valid
        f.delete();
    }
}
