package com.example.shop.shop.service;

import com.example.shop.shop.Entity.User;
import com.example.shop.shop.reponsitory.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsSeviceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsSeviceImpl(com.example.shop.shop.reponsitory.UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var optionalCredential = userRepository.selectNewUserCredentialByUsername(username);
        var credential = optionalCredential.orElseThrow(() -> new UsernameNotFoundException(
                "Account not found"));
        List<GrantedAuthority> authorities = Stream.of(credential.getRole())
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        System.out.println(credential.getRole());
        return new org.springframework.security.core.userdetails.User(
                username,
                credential.getPassword(),
                true,
                true,
                true,
                credential.getIsLocked() == Boolean.FALSE,
                authorities);

    }
}

