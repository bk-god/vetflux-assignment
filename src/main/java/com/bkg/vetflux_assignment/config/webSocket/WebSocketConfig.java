package com.bkg.vetflux_assignment.config.webSocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Map<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Long> SESSION_TIME_MAP = new ConcurrentHashMap<>();
    private static final long IDLE_TIMEOUT_MILLIS = 10 * 60 * 1000;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                        SESSION_MAP.put(session.getId(), session);
                        SESSION_TIME_MAP.put(session.getId(), System.currentTimeMillis());
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                        SESSION_MAP.put(session.getId(), session);
                        SESSION_TIME_MAP.put(session.getId(), System.currentTimeMillis());
                        super.handleMessage(session, message);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
                            throws Exception {
                        SESSION_MAP.remove(session.getId());
                        SESSION_TIME_MAP.remove(session.getId());
                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            SESSION_TIME_MAP.forEach((sessionId, lastActiveTime) -> {
                if (now - lastActiveTime > IDLE_TIMEOUT_MILLIS) {
                    WebSocketSession session = SESSION_MAP.get(sessionId);
                    if (session != null && session.isOpen()) {
                        try {
                            session.close(CloseStatus.NORMAL);
                        } catch (IOException e) {
                            throw new RuntimeException("세션 종료 실패");
                        }
                    }
                }
            });
        }, 1, 1, TimeUnit.MINUTES);
    }

}
