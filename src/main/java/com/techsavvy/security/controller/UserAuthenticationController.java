package com.techsavvy.security.controller;

import com.techsavvy.security.data.payload.AuthenticationRequest;
import com.techsavvy.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class UserAuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping
    public String authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

}
