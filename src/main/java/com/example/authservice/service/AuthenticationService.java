package com.example.authservice.service;


import com.example.authservice.constant.Authority;
import com.example.authservice.dto.AuthResponseDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;
import com.example.authservice.exceptions.UserAlreadyExistsException;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.securityentity.SecurityUser;
import com.example.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    public AuthResponseDto register(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findUserByUsername(userDto.getUsername());
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User already exists with given username : %s", userDto.getUsername()));
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Set.of(Authority.USER))
                .build();

        userRepository.save(user);

        SecurityUser securityUser = new SecurityUser(user);
        String jwtToken = jwtUtils.generateToken(securityUser);

        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponseDto authenticate(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDto.getUsername());

        String jwtToken = jwtUtils.generateToken(userDetails);

        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }
}
