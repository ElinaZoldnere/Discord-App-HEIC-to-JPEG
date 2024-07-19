package com.my.discordbot.event;

import com.my.discordbot.service.ProcessConversionToJpg;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener extends ListenerAdapter {

    @Autowired
    private ProcessConversionToJpg processConversion;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        if (channel.getType() == ChannelType.TEXT) {
            TextChannel textChannel = channel.asTextChannel();
            if (!message.getAuthor().isBot() && !message.getAttachments().isEmpty()) {
                message.getAttachments().forEach(attachment -> {
                    if (attachment.getFileName().endsWith(".heic")) {
                        processConversion.process(attachment, textChannel);
                    }
                });
            }
        }
    }

}


