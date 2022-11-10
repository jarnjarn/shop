package com.example.shop.shop.exception;

import org.springframework.lang.Nullable;

public record ErrorResponse(ErrorCode code, @Nullable String message) {
    public ErrorResponse(ErrorCode code) {
        this(code, code.getMessage());
    }
}

