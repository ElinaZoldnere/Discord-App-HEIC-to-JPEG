package com.my.discordbot.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access= AccessLevel.PACKAGE)
public class EventListenerInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final JDA jda;
    private final EventListener eventListener;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        jda.addEventListener(eventListener);
    }
}
