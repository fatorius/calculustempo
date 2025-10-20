package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.dto.LoginResponse;
import com.hugosouza.calculustempo.interfaces.ErrorResponse;
import com.hugosouza.calculustempo.interfaces.ResponseData;
import com.hugosouza.calculustempo.interfaces.SuccessResponse;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.UserRepository;
import com.hugosouza.calculustempo.security.JwtUtil;
import com.hugosouza.calculustempo.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtil jwtUtils;

    @PostMapping("/login")
    public ResponseData<LoginResponse> authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

        LoginResponse loginResponse = new LoginResponse(token, refreshToken);

        return new SuccessResponse<>(loginResponse);
    }

    @PostMapping("/register")
    public ResponseData<User> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return new ErrorResponse<>(false, "Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return new ErrorResponse<>(false, "Email already exists");
        }

        User newUser = new User(
                IdGenerator.nextId(),
                user.getUsername(),
                user.getEmail(),
                encoder.encode(user.getPassword())
        );

        userRepository.save(newUser);

        return new SuccessResponse<>(newUser);
    }

    @PostMapping("/refresh")
    public ResponseData<LoginResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || !jwtUtils.validateJwtToken(refreshToken)) {
            return new ErrorResponse<>("Invalid refresh token");
        }

        String username = jwtUtils.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtils.generateToken(username);

        return new SuccessResponse<>(new LoginResponse(newAccessToken, refreshToken));
    }
}