package com.github.ivmikhail.dm.downloaders;

import com.github.ivmikhail.dm.Config;
import com.github.ivmikhail.dm.DownloadException;
import com.jcraft.jsch.*;

import java.io.File;
import java.net.URL;

import static com.github.ivmikhail.dm.util.URLUtils.*;

public class SftpDownloader extends Downloader {
    private static final int DEFAULT_PORT = 22;
    private static final String DEFAULT_USER_NAME = "anonymous";
    private static final String DEFAULT_USER_PASS = "anonymous";

    public SftpDownloader(URL url, Config config) {
        super(url, config);
    }

    @Override
    protected long downloadTo(File f) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        try {
            session = jsch.getSession(getUser(url, DEFAULT_USER_NAME), url.getHost(), getPort(url, DEFAULT_PORT));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(getPass(url, DEFAULT_USER_PASS));
            session.setTimeout(config.getTimeoutMillis());
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(url.getPath(), f.getAbsolutePath());
        } catch (JSchException | SftpException e) {
            throw new DownloadException("Failed to download file from SFTP", e);
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
        return 0;
    }
}
