package com.my.discordbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessConversionToJpg {

    @Value("")
    private String cloudFunctionUrl;

    @Value("")
    private String secretToken;

    @Value("")
    private String ownerId;

    public void process(Message.Attachment attachment, MessageChannel channel) {}

}
