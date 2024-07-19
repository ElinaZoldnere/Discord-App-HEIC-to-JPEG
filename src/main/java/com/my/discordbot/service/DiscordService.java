package com.my.discordbot.service;

import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class DiscordService {

    @Autowired
    private JDA jda;

    public void sendDM(String userId, String content) {
        jda.retrieveUserById(userId).queue(user -> user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        }));
    }

}
