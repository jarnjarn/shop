package com.example.shop.shop.service;


import com.example.shop.shop.Entity.User;
import com.example.shop.shop.common.Role;
import com.example.shop.shop.dto.SignInDto;
import com.example.shop.shop.dto.SignUpdto;
import com.example.shop.shop.exception.ApiException;
import com.example.shop.shop.exception.ErrorCode;
import com.example.shop.shop.models.TokenModel;
import com.example.shop.shop.models.UserModel;
import com.example.shop.shop.reponsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl  {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    public TokenModel login(SignInDto dto)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (Exception ex) {
            throw new ApiException(ErrorCode.ACCOUNT_LOCKED, ex);
        }
        var accessToken = jwtService.generateToken(dto.getUsername());
        return new TokenModel(accessToken);
    }

    @Transactional
    public UserModel SignUp(SignUpdto dto)
    {
        User user = CreateUserBase(dto);
        user.setRole(Role.ROLE_ADMIN);
        var addedUser = userRepository.save(user);
        var userModel = new UserModel().LoadUserFrom(addedUser);
        userModel.setAccessToken(jwtService.generateToken(addedUser.getUsername()));
        return userModel;
    }

    public User CreateUserBase (SignUpdto dto){

        var user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAddress(dto.getAddress());
        return user;
    }




}

