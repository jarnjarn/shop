package com.example.shop.shop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Default Spring MVC errors
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "Not Acceptable"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type"),

    // Business errors
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access Denied"),
    REQUEST_TOO_LARGE(HttpStatus.BAD_REQUEST, "Request is too large"),

    // Application errors
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "Wrong password"),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, "Phone is invalid"),
    PHONE_NOT_FOUND(HttpStatus.NOT_FOUND, "Phone not found"),

    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "Account is locked"),
    ACCOUNT_EXPIRED(HttpStatus.FORBIDDEN, "Account is expired"),
    USERNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "Username is already exists"),
    PHONE_ALREADY_EXIST(HttpStatus.CONFLICT, "Phone is already exists"),
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "Manager not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    BANK_NOT_FOUND(HttpStatus.NOT_FOUND, "Bank not found"),
    IMAGE_UPLOAD_FAILED(HttpStatus.CONFLICT, "Image upload failed"),
    CANNOT_UPDATE_IMAGE(HttpStatus.CONFLICT, "Cannot update images"),
    NO_SALE_AVAILABLE(HttpStatus.CONFLICT, "No sale available"),
    CAN_RESET_SALE_PASSWORD_ONLY(HttpStatus.CONFLICT, "Can reset sale password only"),
    CAN_RESET_USER_PASSWORD_ONLY(HttpStatus.CONFLICT, "Can reset user password only"),
    HAVING_UNDONE_PASSWORD_REQUEST(HttpStatus.CONFLICT, "Having undone password request"),
    PASSWORD_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "Password request not found"),
    PASSWORD_REQUEST_NOT_PENDING(HttpStatus.CONFLICT, "Password request is not pending"),
    PASSWORD_REQUEST_NOT_CHANGED(HttpStatus.CONFLICT, "Password request is not changed"),
    CAN_LOCK_UNLOCK_SALE_ONLY(HttpStatus.CONFLICT, "Can lock or unlock sale only"),
    CAN_DELETE_SALE_ONLY(HttpStatus.CONFLICT, "Can delete sale only"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "Task not found"),

    FAILED_TO_SEND_OTP(HttpStatus.NOT_FOUND, "Failed to send OTP"),

    EXPIRED_OTP(HttpStatus.BAD_REQUEST, "Expired OTP"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid OTP"),

    AGENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Agent not found"),
    RATING_NOT_FOUND(HttpStatus.NOT_FOUND, "Rating not found"),
    CANNOT_REVIEW_TASK(HttpStatus.CONFLICT, "Task is not doing"),
    TASK_IS_NOT_IN_REVIEW(HttpStatus.CONFLICT, "Task is not in review"),
    USER_IS_NOT_MANAGER(HttpStatus.CONFLICT, "User is not manager"),
    USER_IS_NOT_SALE(HttpStatus.CONFLICT, "User is not sale"),
    USER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "User info not found"),
    NOT_A_DEPOSITE_TRANSACTION(HttpStatus.BAD_REQUEST, "Not a deposite transaction"),
    NOT_A_WITHDRAW_TRANSACTION(HttpStatus.BAD_REQUEST, "Not a withdraw transaction"),
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Transaction not found"),
    TRANSACTION_NOT_PENDING(HttpStatus.CONFLICT, "Transaction is not pending"),
    OTP_NOT_FOUND(HttpStatus.NOT_FOUND, "OTP not found"),
    NOT_ENOUGH_MONEY(HttpStatus.CONFLICT, "Not enough money"),
    NOT_IN_AGENT_PRODUCT_PRICE_RANGE(HttpStatus.CONFLICT, "Not in agent product price range"),
    OVER_MAX_TASKS_PER_DAY(HttpStatus.CONFLICT, "Over max tasks per day"),
    OVER_WITHDRAW_AMOUNT(HttpStatus.CONFLICT, "Over withdraw amount"),
    NOT_ENOUGH_CREDIT_POINTS_TO_WITHDRAW(HttpStatus.CONFLICT,
            "Not enough credit points to withdraw"),
    OVER_MAX_WITHDRAW_PER_DAY(HttpStatus.CONFLICT, "Over max withdraw per day"),
    THERE_IS_UNDONE_TASK(HttpStatus.CONFLICT, "There is undone task"),
    THERE_IS_NO_TASK(HttpStatus.CONFLICT, "There is no task"),
    CANNOT_DEPOSITE_FOR_NOT_DOING_TASK(HttpStatus.CONFLICT, "Cannot deposite for not doing task"),
    TASK_IS_ALREADY_PAID(HttpStatus.CONFLICT, "Task is already paid"),
    TASK_IS_NOT_PAID(HttpStatus.CONFLICT, "Task is not paid"),
    DEPOSITE_NOT_ENOUGH(HttpStatus.CONFLICT, "Deposite not enough"),
    NO_AGENT_MATCH(HttpStatus.CONFLICT, "No agent match"),

    MONEY_NOT_ENOUGH(HttpStatus.CONFLICT, "Money not enough"),

    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Image not found"),

    NOT_FOUND_BANK_INFO(HttpStatus.NOT_FOUND, "Not found bank info");
    private final HttpStatus status;
    private final String message;
}
