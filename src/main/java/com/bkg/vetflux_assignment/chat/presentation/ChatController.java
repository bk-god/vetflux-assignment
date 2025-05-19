package com.bkg.vetflux_assignment.chat.presentation;

import java.util.List;
import java.util.Set;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bkg.vetflux_assignment.chat.application.ChatService;
import com.bkg.vetflux_assignment.chat.application.dto.ChatMessageDTO;
import com.bkg.vetflux_assignment.chat.application.dto.ChatRoomDTO;
import com.bkg.vetflux_assignment.chat.domain.ChatMessage;
import com.bkg.vetflux_assignment.chat.presentation.payload.CreateChatRoomRequest;
import com.bkg.vetflux_assignment.chat.presentation.payload.SendMessageRequest;
import com.bkg.vetflux_assignment.config.api.ApiResponse;
import com.bkg.vetflux_assignment.config.exception.UnauthorizedTokenException;
import static com.bkg.vetflux_assignment.config.security.SecurityTokenAuthenticationFilter.TOKEN_PREFIX;
import com.bkg.vetflux_assignment.config.security.SecurityTokenProvider;
import com.bkg.vetflux_assignment.config.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SecurityTokenProvider securityTokenProvider;

    @PostMapping(name = "채팅방 생성", value = "/chat/room")
    public ApiResponse<Long> createChatRoom(@RequestBody CreateChatRoomRequest request) {
        long loginUserId = SecurityUtils.getTokenUserId();
        Set<Long> userIds = request.getUserIds();
        userIds.add(loginUserId);
        long chatRoomId = chatService.createChatRoom(userIds);
        return ApiResponse.success(chatRoomId);
    }

    @GetMapping(name = "채팅방 목록 조회", value = "/chat/room")
    public ApiResponse<List<ChatRoomDTO>> fetchChatRoomList() {
        long loginUserId = SecurityUtils.getTokenUserId();
        return ApiResponse.success(chatService.fetchChatRoomList(loginUserId));
    }

    @GetMapping(name = "채팅 메세지 목록 조회", value = "/chat/room/{chatRoomId}/message")
    public ApiResponse<List<ChatMessageDTO>> fetchChatMessageList(@PathVariable long chatRoomId) {
        long loginUserId = SecurityUtils.getTokenUserId();
        return ApiResponse.success(chatService.fetchChatMessageList(chatRoomId, loginUserId));
    }

    @MessageMapping("/chat/message")
    public void sendMessage(SendMessageRequest request, Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String accessToken = accessor.getFirstNativeHeader("Authorization");
        if (accessToken == null || accessToken.isBlank())
            throw new UnauthorizedTokenException();
        String prefix = accessToken.trim().substring(0, TOKEN_PREFIX.length()).toUpperCase();
        if (!TOKEN_PREFIX.equalsIgnoreCase(prefix))
            throw new UnauthorizedTokenException();
        final String token = accessToken.trim().substring(TOKEN_PREFIX.length());
        if (securityTokenProvider.validateAccessToken(token)) {
            long loginUserId = securityTokenProvider.getAccessTokenUserId(token);
            chatService.sendMessage(ChatMessage.create(request.getChatRoomId(), loginUserId, request.getContent()));
        } else {
            throw new UnauthorizedTokenException();
        }
    }

}
