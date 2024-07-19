package com.my.discordbot.service;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProcessConversionToJpg {

    @Autowired
    private DiscordService discordService;
    @Autowired
    private HttpService httpService;
    @Autowired
    private FileService fileService;

    @Value("${CLOUD_FUNCTION_URL}")
    private String cloudFunctionUrl;
    @Value("${SECRET_TOKEN}")
    private String secretToken;
    @Value("${OWNER_ID}")
    private String ownerId;

    public void process(Message.Attachment attachment, TextChannel textChannel) {
        try {
            File tempFile = fileService.createTempFile("attachment-", ".heic");
            discordService.downloadAttachment(attachment, tempFile, () -> {
                try (Response response = httpService.uploadFile(tempFile, cloudFunctionUrl,secretToken)){
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            File convertedFile = fileService.createTempFile("converted-", ".jpg");
                            fileService.write(convertedFile, responseBody.bytes());
                            discordService.sendFile(textChannel, convertedFile,"converted.jpg" );
                        }
                    } else {
                        String errorBody = response.body() != null ? response.body().string() : "No response body";
                        discordService.sendDM(ownerId, "Failed to convert HEIC file: HTTP status " + response.code() + "\nError: " + errorBody);
                    }
                } catch (IOException e) {
                    discordService.sendDM(ownerId, "Failed to convert HEIC file: " + e.getMessage());
                } finally {
                    fileService.delete(tempFile);
                }
            });
        } catch (IOException e) {
            discordService.sendDM(ownerId, "Failed to create temporary file for download: " + e.getMessage());
        }
    }

}
