package com.bkg.vetflux_assignment.user.presentation;

import java.util.List;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bkg.vetflux_assignment.config.api.ApiResponse;
import com.bkg.vetflux_assignment.config.security.SecurityTokenDTO;
import com.bkg.vetflux_assignment.config.security.SecurityUtils;
import com.bkg.vetflux_assignment.user.application.UserService;
import com.bkg.vetflux_assignment.user.application.dto.UserDTO;
import com.bkg.vetflux_assignment.user.presentation.payload.JoinUserRequest;
import com.bkg.vetflux_assignment.user.presentation.payload.LoginUserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(name = "회원가입", value = "/user")
    public ApiResponse<?> joinUser(@RequestBody JoinUserRequest request) {
        userService.joinUser(request.getLoginId(), request.getNickname(), request.getPassword(), request.getPasswordCheck());
        return ApiResponse.success();
    }

    @PostMapping(name = "로그인", value = "/user/login")
    public ApiResponse<SecurityTokenDTO> loginUser(@RequestBody LoginUserRequest request) {
        SecurityTokenDTO token = userService.loginUser(request.getLoginId(), request.getPassword());
        return ApiResponse.success(token);
    }

    @GetMapping(name = "회원목록 조회", value = "/user")
    public ApiResponse<List<UserDTO>> fetchUserList() {
        long loginUserId = SecurityUtils.getTokenUserIdOptional().orElse(0L);
        return ApiResponse.success(userService.fetchUserList(loginUserId));
    }

}
