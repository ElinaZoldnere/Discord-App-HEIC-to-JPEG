package com.my.discordbot;

import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotInitializer {

    @Autowired
    private Bot bot;

    @Value("")
    private String discordBotToken;

    public void initializeBot() {
        JDABuilder.createLight(discordBotToken)
                .addEventListeners(bot)
                .build();
    }

}
