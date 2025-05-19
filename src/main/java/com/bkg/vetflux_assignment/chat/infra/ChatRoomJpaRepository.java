package com.bkg.vetflux_assignment.chat.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bkg.vetflux_assignment.chat.domain.ChatRoom;
import com.bkg.vetflux_assignment.chat.domain.ChatRoomRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepository {
    
    
    
}
