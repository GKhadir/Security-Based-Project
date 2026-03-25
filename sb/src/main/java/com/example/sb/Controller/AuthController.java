package com.example.sb.Controller;

import com.example.sb.Entity.Users;
import com.example.sb.SecurityCongif.JwtUtil;
import com.example.sb.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth/register")
    public String register(@RequestBody Users user) {
        return service.register(user);
    }

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody Users user) {

        Users validUser = service.login(user.getUsername(), user.getPassword());

        String token = jwtUtil.generateToken(validUser.getUsername());

        return Map.of("token", token);
    }

    // 🔒 Protected API
    @GetMapping("/user/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public String profile(Authentication auth) {
        return "Welcome " + auth.getName().toString();
    }
}
