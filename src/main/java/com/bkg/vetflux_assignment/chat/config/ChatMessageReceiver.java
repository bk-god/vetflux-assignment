package com.bkg.vetflux_assignment.chat.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.chat.domain.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatMessageReceiver implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody());
            ChatMessage chatMessage = objectMapper.readValue(json, ChatMessage.class);

            // WebSocket으로 브로드캐스트
            messagingTemplate.convertAndSend(
                "/sub/chat/room/" + chatMessage.getChatRoomId(),
                chatMessage
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
