package com.example.shop.shop.common;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {
    private String password;
    private Role role;
    private Boolean isLocked;
}
