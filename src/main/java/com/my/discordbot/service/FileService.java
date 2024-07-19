package com.my.discordbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
class FileService {

    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public File createTempFile(String prefix, String suffix) throws IOException {
        return File.createTempFile(prefix, suffix);
    }

    public void write(File file, byte[] data) throws IOException {
        Files.write(file.toPath(), data);
    }

    public void delete(File file) {
        if (!file.delete()) {
            logger.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
        }
    }

}
