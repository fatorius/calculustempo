package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.dto.LoginResponse;
import com.hugosouza.calculustempo.interfaces.ErrorResponse;
import com.hugosouza.calculustempo.interfaces.ResponseData;
import com.hugosouza.calculustempo.interfaces.SuccessResponse;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.UserRepository;
import com.hugosouza.calculustempo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

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

        LoginResponse loginResponse = new LoginResponse(token, "");

        return new SuccessResponse<>(loginResponse);
    }

    @PostMapping("/register")
    public ResponseData<User> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return new ErrorResponse<>(false, "Username already exists");
        }

        User newUser = new User(
                null,
                user.getUsername(),
                encoder.encode(user.getPassword())
        );

        userRepository.save(newUser);
        return new SuccessResponse<>(newUser);
    }
}