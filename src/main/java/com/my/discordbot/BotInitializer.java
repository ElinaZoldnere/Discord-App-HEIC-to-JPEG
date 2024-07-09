package com.my.discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class BotInitializer {

    @Autowired
    private EventListener eventListener;
    @Autowired
    private ProcessConversionToJpg processConversionToJpg;

    @Value("${DISCORD_BOT_TOKEN}")
    private String discordBotToken;

    public void initializeBot() {
        try {
            JDA jda = JDABuilder.createLight(discordBotToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(eventListener)
                    .build();

            processConversionToJpg.setJda(jda);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
