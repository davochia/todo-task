package com.example.integrifytask.controller;

import com.example.integrifytask.configuration.JwtUtils;
import com.example.integrifytask.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserDao userDao;
    private final JwtUtils jwtUtils;
    private  final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticateRequest authenticateRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));

        final UserDetails userDetails = userDao.findUserByEmail(authenticateRequest.getEmail());
        if (userDetails != null){
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Error occurred");
    }
}
