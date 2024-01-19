package com.example.authservice.controller;

import com.example.authservice.dto.AuthResponseDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.authenticate(userDto));
    }
}
