package com.my.discordbot.event;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class EventListenerInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JDA jda;

    @Autowired
    private EventListener eventListener;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        jda.addEventListener(eventListener);
    }
}