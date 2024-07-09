package com.my.discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
class ProcessConversionToJpg {

    @Value("${CLOUD_FUNCTION_URL}")
    private String cloudFunctionUrl;
    @Value("${SECRET_TOKEN}")
    private String secretToken;
    @Value("${OWNER_ID}")
    private String ownerId;

    private JDA jda;

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public void process(Message.Attachment attachment, TextChannel textChannel) {
        try {
            File tempFile = File.createTempFile("attachment-", ".heic");
            attachment.getProxy().downloadToFile(tempFile).thenAccept(file -> {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.getName(),
                                    RequestBody.create(file, MediaType.parse("image/heic")))
                            .build();
                    Request request = new Request.Builder()
                            .url(cloudFunctionUrl)
                            .post(body)
                            .addHeader("Authorization", "Bearer " + secretToken)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            File convertedFile = File.createTempFile("converted-", ".jpg");
                            Files.write(convertedFile.toPath(), responseBody.bytes());
                            FileUpload fileUpload = FileUpload.fromData(convertedFile, "converted.jpg");
                            textChannel.sendFiles(fileUpload).queue();
                        }
                    } else {
                        String errorBody = response.body() != null ? response.body().string() : "No response body";
                        sendDM(ownerId, "Failed to convert HEIC file: HTTP status " + response.code() + "\nError: " + errorBody);
                    }
                } catch (IOException e) {
                    sendDM(ownerId, "Failed to convert HEIC file: " + e.getMessage());
                } finally {
                    if (!file.delete()) {
                        System.err.println("Failed to delete temporary file: " + file.getAbsolutePath());
                    }
                }
            });
        } catch (IOException e) {
            sendDM(ownerId, "Failed to create temporary file for download: " + e.getMessage());
        }
    }

    private void sendDM(String userId, String content) {
        jda.retrieveUserById(userId).queue(user -> user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        }));
    }

}
