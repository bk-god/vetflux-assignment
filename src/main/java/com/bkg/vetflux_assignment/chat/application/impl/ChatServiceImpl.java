package com.bkg.vetflux_assignment.chat.application.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.chat.application.ChatService;
import com.bkg.vetflux_assignment.chat.application.dto.ChatMessageDTO;
import com.bkg.vetflux_assignment.chat.application.dto.ChatRoomDTO;
import com.bkg.vetflux_assignment.chat.config.ChatMessageSender;
import com.bkg.vetflux_assignment.chat.domain.ChatMessage;
import com.bkg.vetflux_assignment.chat.domain.ChatMessageRepository;
import com.bkg.vetflux_assignment.chat.domain.ChatRoom;
import com.bkg.vetflux_assignment.chat.domain.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatMessageSender messageSender;
  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;

  @Override
  public long createChatRoom(Set<Long> userIds) {
    ChatRoom chatRoom = chatRoomRepository.findByUserIds(userIds)
        .orElseGet(() -> chatRoomRepository.save(ChatRoom.create("new 채팅방", userIds)));
    return chatRoom.getChatRoomId();
  }

  @Override
  public List<ChatRoomDTO> fetchChatRoomList(long userId) {
    return chatRoomRepository.findByUserIdIn(userId).stream()
        .map((room) -> new ChatRoomDTO(
            room.getChatRoomId(),
            room.getRoomName(),
            room.getCreatedDatetime()))
        .collect(Collectors.toList());
  }

  @Override
  public List<ChatMessageDTO> fetchChatMessageList(long chatRoomId, long userId) {
    ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomId)
        .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
    if (!chatRoom.isEnteredUser(userId)) {
      throw new RuntimeException("채팅방에 입장하지 않은 회원입니다.");
    }
    return chatMessageRepository.findByChatRoomIdOrderBySendDatetimeAsc(chatRoomId).stream()
        .map((message) -> new ChatMessageDTO(
            message.getChatMessageId(),
            message.getChatRoomId(),
            message.getSenderId(),
            message.getContent(),
            message.getSendDatetime()))
        .collect(Collectors.toList());
  }

  @Override
  public void sendMessage(ChatMessage message) {
    ChatMessage savedChatMessage = chatMessageRepository.save(message);
    messageSender.publish(savedChatMessage);
  }

}
