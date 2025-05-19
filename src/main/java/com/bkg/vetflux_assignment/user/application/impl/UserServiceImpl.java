package com.bkg.vetflux_assignment.user.application.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bkg.vetflux_assignment.config.security.SecurityTokenDTO;
import com.bkg.vetflux_assignment.config.security.SecurityTokenProvider;
import com.bkg.vetflux_assignment.user.application.UserService;
import com.bkg.vetflux_assignment.user.application.dto.UserDTO;
import com.bkg.vetflux_assignment.user.domain.User;
import com.bkg.vetflux_assignment.user.domain.UserReporitory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReporitory userReporitory;
    private final PasswordEncoder passwordEncoder;
    private final SecurityTokenProvider securityTokenProvider;

    @Override
    public void joinUser(String loginId, String nickname, String password, String passwordCheck) {
        System.out.println("loginId: " + loginId);
        if (userReporitory.countByLoginId(loginId) > 0)
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        if (!password.equals(passwordCheck))
            throw new RuntimeException("비밀번호가 동일하지 않습니다.");
        userReporitory.save(User.create(loginId, nickname, passwordEncoder.encode(password)));
    }

    @Override
    public SecurityTokenDTO loginUser(String loginId, String password) {
        User user = userReporitory.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        return securityTokenProvider.issueToken(user.getUserId(), user.getNickname(), List.of());
    }

    @Override
    public List<UserDTO> fetchUserList(long loginUserId) {
        return userReporitory.findByUserIdNot(loginUserId).stream()
                .map((user) -> new UserDTO(user.getUserId(), user.getNickname()))
                .collect(Collectors.toList());
    }

}
