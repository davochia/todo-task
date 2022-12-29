package com.example.integrifytask.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AuthenticateRequest {
    private String email;
    private String password;
}
