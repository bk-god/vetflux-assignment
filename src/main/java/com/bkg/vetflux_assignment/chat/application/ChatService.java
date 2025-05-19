package com.bkg.vetflux_assignment.chat.application;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bkg.vetflux_assignment.chat.application.dto.ChatMessageDTO;
import com.bkg.vetflux_assignment.chat.application.dto.ChatRoomDTO;
import com.bkg.vetflux_assignment.chat.domain.ChatMessage;


@Service
public interface ChatService {

    @Transactional
    long createChatRoom(Set<Long> userIds);

    @Transactional(readOnly=true)
    List<ChatRoomDTO> fetchChatRoomList(long userId);

    @Transactional(readOnly=true)
    List<ChatMessageDTO> fetchChatMessageList(long chatRoomId, long userId);

    @Transactional
    void sendMessage(ChatMessage message);
    
}
