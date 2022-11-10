package com.example.shop.shop.Util;

import java.util.Optional;

public class CodeUtil {

    public static Optional<String> extractTokenFromAuthHeader(String authHeader) {
        final var HEADER_PREFIX = "Bearer ";
        if (!authHeader.startsWith(HEADER_PREFIX)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(HEADER_PREFIX.length()));
    }

}
