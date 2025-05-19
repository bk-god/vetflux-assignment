package com.bkg.vetflux_assignment.chat.infra;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bkg.vetflux_assignment.chat.domain.ChatMessage;
import com.bkg.vetflux_assignment.chat.domain.ChatMessageRepository;

public interface  ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepository {
    
}
