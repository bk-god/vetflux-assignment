package com.bkg.vetflux_assignment.chat.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);
    ChatRoom saveAndFlush(ChatRoom chatRoom);

    @Query("""
    SELECT cr
    FROM ChatRoom cr
    JOIN cr.users cru
    GROUP BY cr
    HAVING COUNT(CASE WHEN cru.chatRoomUserId.userId IN :userIds THEN 1 END) = :#{#userIds.size()}
       AND COUNT(cru) = :#{#userIds.size()}
    """)
    Optional<ChatRoom> findByUserIds(@Param("userIds") Set<Long> userIds);

    @Query("""
    SELECT cr
    FROM ChatRoom cr
    JOIN cr.users cru
    GROUP BY cr
    HAVING COUNT(CASE WHEN cru.chatRoomUserId.userId IN :userId THEN 1 END) = 1
    ORDER BY cr.createdDatetime desc
    """)
    List<ChatRoom> findByUserIdIn(@Param("userId") Long userId);

    Optional<ChatRoom> findByChatRoomId(long chatRoomId);
}
