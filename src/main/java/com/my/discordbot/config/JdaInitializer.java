package com.my.discordbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdaInitializer {

    @Value("${DISCORD_BOT_TOKEN}")
    private String discordBotToken;

    @Bean
    public JDA jda() {
        try {
            return JDABuilder.createLight(discordBotToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .build();
        } catch (Exception e) {
            throw new BeanCreationException("Failed to initialize JDA", e);
        }
    }

}
