package com.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ErrorResponseDto {
    private String apiPath;
    private String errorMsg;
    private HttpStatus errorCode;
    private LocalDateTime errorTime;
}
