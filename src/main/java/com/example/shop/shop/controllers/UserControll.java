package com.example.shop.shop.controllers;

import com.example.shop.shop.dto.SignInDto;
import com.example.shop.shop.dto.SignUpdto;
import com.example.shop.shop.models.TokenModel;
import com.example.shop.shop.models.UserModel;
import com.example.shop.shop.service.UserServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Api(tags = "User")
public class UserControll {

    private final UserServiceImpl userService;

    @PostMapping("/Login")
    public TokenModel Login(SignInDto dto)
    {
        return userService.login(dto);
    }

    @PostMapping("/sign-up")
    public UserModel SignUp(SignUpdto dto){
        return userService.SignUp(dto);
    }


    @PostMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String test(){
        return "test";
    }

}
