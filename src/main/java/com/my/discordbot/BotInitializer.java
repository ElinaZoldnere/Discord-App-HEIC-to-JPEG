package com.my.discordbot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotInitializer {

    @Autowired
    private EventListener eventListener;

    @Value("${DISCORD_BOT_TOKEN}")
    private String discordBotToken;

    public void initializeBot() {
        JDABuilder.createLight(discordBotToken)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(eventListener)
                .build();
    }

}
