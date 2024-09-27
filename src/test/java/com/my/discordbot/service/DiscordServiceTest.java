package com.my.discordbot.service;

import com.my.discordbot.utils.FileUploadUtil;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.AttachmentProxy;
import net.dv8tion.jda.api.utils.FileUpload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscordServiceTest {

    @Mock
    private JDA jdaMock;
    @Mock
    private User userMock;
    @Mock
    private PrivateChannel privateChannelMock;
    @Mock
    private TextChannel textChannelMock;
    @Mock
    private File fileMock;
    @Mock
    private FileUpload fileUploadMock;
    @Mock
    private FileUploadUtil fileUploadUtilMock;
    @Mock
    private Message.Attachment attachmentMock;

    @InjectMocks
    private DiscordService discordService;

    @Test
    void sendDMSendsDM() {
        String userId = "123456789";
        String content = "Hello, Discord!";

        @SuppressWarnings("unchecked")
        CacheRestAction<User> userRetrieveAction = mock(CacheRestAction.class);
        when(jdaMock.retrieveUserById(userId)).thenReturn(userRetrieveAction);

        doAnswer(invocation -> {
            Consumer<User> callback = invocation.getArgument(0);
            callback.accept(userMock);
            return null;
        }).when(userRetrieveAction).queue(any());

        @SuppressWarnings("unchecked")
        CacheRestAction<PrivateChannel> privateChannelRestAction = mock(CacheRestAction.class);
        when(userMock.openPrivateChannel()).thenReturn(privateChannelRestAction);

        doAnswer(invocation -> {
            Consumer<PrivateChannel> callback = invocation.getArgument(0);
            callback.accept(privateChannelMock);
            return null;
        }).when(privateChannelRestAction).queue(any());

        MessageCreateAction messageCreateActionMock = mock(MessageCreateAction.class);
        when(privateChannelMock.sendMessage(content)).thenReturn(messageCreateActionMock);

        discordService.sendDM(userId, content);

        verify(jdaMock, times(1)).retrieveUserById(userId);
        verify(userMock, times(1)).openPrivateChannel();
        verify(privateChannelMock, times(1)).sendMessage(content);
    }

    @Test
    void sendFileSendsFile() {
        String fileName = "file.heic";

        when(fileUploadUtilMock.createFileUpload(fileMock, fileName)).thenReturn(fileUploadMock);

        MessageCreateAction messageCreateActionMock = mock(MessageCreateAction.class);
        when(textChannelMock.sendFiles(fileUploadMock)).thenReturn(messageCreateActionMock);

        discordService.sendFile(textChannelMock, fileMock, fileName);

        verify(textChannelMock, times(1)).sendFiles(fileUploadMock);
        verify(messageCreateActionMock, times(1)).queue();
    }

    @Test
    void downloadAttachmentDownloadsAttachment() {
        AttachmentProxy attachmentProxyMock = mock(AttachmentProxy.class);
        when(attachmentMock.getProxy()).thenReturn(attachmentProxyMock);

        @SuppressWarnings("unchecked")
        CompletableFuture<File> completableFutureMock = mock(CompletableFuture.class);
        when(attachmentProxyMock.downloadToFile(fileMock)).thenReturn(completableFutureMock);

        Runnable onCompleteMock = mock(Runnable.class);

        doAnswer(invocation -> {
            Runnable onComplete = invocation.getArgument(0);
            onComplete.run();
            return null;
        }).when(completableFutureMock).thenRun(any(Runnable.class));

        discordService.downloadAttachment(attachmentMock, fileMock, onCompleteMock);

        verify(attachmentMock, times(1)).getProxy();
        verify(onCompleteMock, times(1)).run();
    }

}
