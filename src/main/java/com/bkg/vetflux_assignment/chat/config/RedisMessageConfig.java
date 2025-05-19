package com.bkg.vetflux_assignment.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisMessageConfig {

    private final ChatMessageReceiver chatMessageReceiver;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("chat-room");
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(chatMessageReceiver);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener(),  new PatternTopic(topic().getTopic() + ":*"));
        return container;
    }

}
