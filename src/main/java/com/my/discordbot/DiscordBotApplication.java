package com.my.discordbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordBotApplication implements CommandLineRunner {

    @Autowired
    private BotInitializer botInitializer;

    public static void main(String[] args) {
        SpringApplication.run(DiscordBotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        botInitializer.initializeBot();
    }

}
