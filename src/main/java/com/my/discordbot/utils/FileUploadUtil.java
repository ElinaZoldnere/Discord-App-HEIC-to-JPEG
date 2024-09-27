package com.my.discordbot.utils;

import java.io.File;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.stereotype.Service;

@Service
public class FileUploadUtil {
    public FileUpload createFileUpload(File file, String fileName) {
        return FileUpload.fromData(file, fileName);
    }

}
