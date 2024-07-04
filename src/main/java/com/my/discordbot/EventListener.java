package com.my.discordbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventListener extends ListenerAdapter {

    @Autowired
    private ProcessConversionToJpg processConversion;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        if (channel instanceof TextChannel) {
            if (!message.getAuthor().isBot() && message.getAttachments().size() > 0) {
                message.getAttachments().forEach(attachment -> {
                    if (attachment.getFileName().endsWith(".heic")) {
                        processConversion.process(attachment, channel);
                    }
                });
            }
        }
    }

}


