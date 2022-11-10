package com.example.shop.shop.models;

import com.example.shop.shop.Entity.User;
import com.example.shop.shop.common.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private int id;
    private String username;
    private String address;
    private String password;
    private Role role;
    private String accessToken;

    public UserModel LoadUserFrom(User entity)
    {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.address = entity.getAddress();
        this.password = entity.getPassword();
        this.role = entity.getRole();
        return this;
    }

}
