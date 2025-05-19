package com.bkg.vetflux_assignment.user.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bkg.vetflux_assignment.config.security.SecurityTokenDTO;
import com.bkg.vetflux_assignment.user.application.dto.UserDTO;

@Service
public interface UserService {

    @Transactional
    void joinUser(String loginId, String nickname, String password, String passwordCheck);

    @Transactional
    SecurityTokenDTO loginUser(String loginId, String password);

    @Transactional(readOnly = true)
    List<UserDTO> fetchUserList(long loginUserId);
    
    
}
