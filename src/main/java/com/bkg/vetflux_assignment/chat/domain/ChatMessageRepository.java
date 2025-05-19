package com.bkg.vetflux_assignment.chat.domain;

import java.util.List;

public interface ChatMessageRepository {

    ChatMessage save(ChatMessage message);
    List<ChatMessage> findByChatRoomIdOrderBySendDatetimeAsc(long chatRoomId);
    
}
