package com.bkg.vetflux_assignment.chat.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.chat.domain.ChatMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatMessageSender {
    
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private final ChannelTopic topic;

    public void publish(ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic() + ":" + message.getChatRoomId(), message);
    }

}
