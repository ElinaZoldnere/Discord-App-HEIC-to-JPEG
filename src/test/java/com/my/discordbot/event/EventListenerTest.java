package com.my.discordbot.event;

import com.my.discordbot.service.ProcessConversionToJpeg;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventListenerTest {

    @Mock
    private ProcessConversionToJpeg processConversionMock;
    @Mock
    private MessageReceivedEvent eventMock;
    @Mock
    private Message messageMock;
    @Mock
    private MessageChannelUnion messageChannelMock;
    @Mock
    private Message.Attachment attachmentMock;
    @Mock
    private User userMock;

    @InjectMocks
    private EventListener eventListener;

    @Test
    void testOnMessageReceivedWithHeicAttachment() {
        when(eventMock.getMessage()).thenReturn(messageMock);
        when(eventMock.getChannel()).thenReturn(messageChannelMock);
        when(messageChannelMock.getType()).thenReturn(ChannelType.TEXT);
        when(messageChannelMock.asTextChannel()).thenReturn(mock(TextChannel.class));
        when(messageMock.getAuthor()).thenReturn(userMock);
        when(userMock.isBot()).thenReturn(false);
        when(messageMock.getAttachments()).thenReturn(Collections.singletonList(attachmentMock));
        when(attachmentMock.getFileName()).thenReturn("image.heic");

        eventListener.onMessageReceived(eventMock);

        verify(processConversionMock, times(1)).process(eq(attachmentMock), any(TextChannel.class));
    }

    @Test
    void testOnMessageReceivedWithNonHeicAttachment() {
        when(eventMock.getMessage()).thenReturn(messageMock);
        when(eventMock.getChannel()).thenReturn(messageChannelMock);
        when(messageChannelMock.getType()).thenReturn(ChannelType.TEXT);
        when(messageChannelMock.asTextChannel()).thenReturn(mock(TextChannel.class));
        when(messageMock.getAuthor()).thenReturn(userMock);
        when(userMock.isBot()).thenReturn(false);
        when(messageMock.getAttachments()).thenReturn(Collections.singletonList(attachmentMock));
        when(attachmentMock.getFileName()).thenReturn("image.jpg");

        eventListener.onMessageReceived(eventMock);

        verify(processConversionMock, never()).process(any(), any());
    }

}
