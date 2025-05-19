package com.bkg.vetflux_assignment.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("회원")
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 고유번호")
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Comment("아이디")
    @Column(name = "login_id", nullable = false, length = 50)
    private String loginId;

    @Comment("닉네임")
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Comment("비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    @Comment("가입일자")
    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDatetime;

    public static User create(String loginId, String nickname, String password) {
        return User.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .createDatetime(LocalDateTime.now())
                .build();
    }

}
