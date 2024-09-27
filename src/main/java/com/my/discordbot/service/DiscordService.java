package com.my.discordbot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DiscordService {

    private final JDA jda;

    public void sendDM(String userId, String content) {
        jda.retrieveUserById(userId).queue(user -> user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        }));
    }

    public void sendFile(TextChannel textChannel, File file, String fileName) {
        FileUpload fileUpload = FileUpload.fromData(file, fileName);
        textChannel.sendFiles(fileUpload).queue();
    }

    public void downloadAttachment(Message.Attachment attachment, File destination, Runnable onComplete){
        attachment.getProxy().downloadToFile(destination).thenRun(onComplete);
    }

}
