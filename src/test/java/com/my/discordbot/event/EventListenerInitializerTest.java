package com.my.discordbot.event;

import net.dv8tion.jda.api.JDA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.event.ContextRefreshedEvent;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventListenerInitializerTest {

    @Mock
    private JDA jdaMock;
    @Mock
    private EventListener eventListenerMock;
    @Mock
    private ContextRefreshedEvent eventMock;

    @InjectMocks
    private EventListenerInitializer eventListenerInitializer;

    @Test
    public void testOnApplicationEventInitializesEventListener() {
        eventListenerInitializer.onApplicationEvent(eventMock);

        verify(jdaMock).addEventListener(eventListenerMock);
    }

}
