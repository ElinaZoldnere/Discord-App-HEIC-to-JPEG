package com.my.discordbot;

import com.my.discordbot.event.EventListener;
import com.my.discordbot.service.ProcessConversionToJpg;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventListenerTest {

    @Mock
    private ProcessConversionToJpg processConversion;
    @Mock
    private MessageReceivedEvent event;
    @Mock
    private Message message;
    @Mock
    private User user;
    @Mock
    private TextChannel textChannel;
    @Mock
    private Message.Attachment attachment;
    @Mock
    private MessageChannelUnion messageChannelUnion;

    @InjectMocks
    private EventListener eventListener;

    @Test
    void testOnMessageReceived_withHeicAttachment() {
        when(event.getMessage()).thenReturn(message);
        when(event.getChannel()).thenReturn(messageChannelUnion);
        when(messageChannelUnion.getType()).thenReturn(ChannelType.TEXT);
        when(messageChannelUnion.asTextChannel()).thenReturn(textChannel);
        when(message.getAuthor()).thenReturn(user);
        when(user.isBot()).thenReturn(false);
        when(message.getAttachments()).thenReturn(Collections.singletonList(attachment));
        when(attachment.getFileName()).thenReturn("image.heic");

        eventListener.onMessageReceived(event);

        verify(processConversion, times(1)).process(attachment, textChannel);
    }

    @Test
    void testOnMessageReceived_withNonHeicAttachment() {
        when(event.getMessage()).thenReturn(message);
        when(event.getChannel()).thenReturn(messageChannelUnion);
        when(messageChannelUnion.getType()).thenReturn(ChannelType.TEXT);
        when(messageChannelUnion.asTextChannel()).thenReturn(textChannel);
        when(message.getAuthor()).thenReturn(user);
        when(user.isBot()).thenReturn(false);
        when(message.getAttachments()).thenReturn(Collections.singletonList(attachment));
        when(attachment.getFileName()).thenReturn("image.jpg");

        eventListener.onMessageReceived(event);

        verify(processConversion, never()).process(any(), any());
    }
}